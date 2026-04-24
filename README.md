# Conflict Tracker – Desplegament Fullstack

App de gestió de conflictes bèl·lics desplegada al núvol amb Vue 3 + Spring Boot + PostgreSQL.

🔗 **Frontend:** https://conflict-tracker-vue-azure.vercel.app

---

## Arquitectura

```
[Vue 3 – Vercel] → HTTP/REST → [Spring Boot – Railway] → JDBC → [PostgreSQL – Neon.tech]
```

| Capa | Tecnologia | Servei |
|------|-----------|--------|
| Frontend | Vue 3 + Vite | Vercel |
| Backend | Spring Boot (Java 21) | Railway |
| Base de dades | PostgreSQL | Neon.tech |

---

## Variables d'entorn

### Backend (Railway)

| Variable | Descripció |
|----------|-----------|
| `DB_URL` | URL JDBC de Neon.tech |
| `DB_USERNAME` | Usuari de la BD |
| `DB_PASSWORD` | Contrasenya de la BD |
| `FRONTEND_URL` | URL del frontend a Vercel |

### Frontend (Vercel)

| Variable | Descripció |
|----------|-----------|
| `VITE_API_URL` | URL del backend a Railway |

---

## Com configurar un nou desplegament

1. Crear una BD PostgreSQL a [neon.tech](https://neon.tech) i copiar les credencials
2. Pujar el backend a GitHub i importar-lo a [railway.app](https://railway.app), afegint les variables d'entorn
3. Pujar el frontend a GitHub i importar-lo a [vercel.com](https://vercel.com), afegint la variable `VITE_API_URL`
4. Un cop desplegat el frontend, actualitzar `FRONTEND_URL` a Railway amb la URL de Vercel

---

## Modificacions realitzades i errors trobats

### Backend

**Error 1 – Base de dades H2 en memòria**

El projecte usava H2, una base de dades en memòria que no persisteix dades i no funciona en entorns cloud. Cada cop que el servidor reiniciava, les dades desapareixien.

Canvis al `application.yaml`:
```yaml
# Abans
datasource:
  driver-class-name: org.h2.Driver
  url: jdbc:h2:mem:testdb
  username: sa
  password:

# Després
datasource:
  driver-class-name: org.postgresql.Driver
  url: ${DB_URL}
  username: ${DB_USERNAME}
  password: ${DB_PASSWORD}
```

Canvi al `pom.xml` – substituir la dependència H2 per PostgreSQL:
```xml
<!-- Abans -->
<dependency>
  <groupId>com.h2database</groupId>
  <artifactId>h2</artifactId>
  <scope>runtime</scope>
</dependency>

<!-- Després -->
<dependency>
  <groupId>org.postgresql</groupId>
  <artifactId>postgresql</artifactId>
  <scope>runtime</scope>
</dependency>
```

---

**Error 2 – CORS bloquejat pel navegador**

En intentar carregar dades des del frontend, el navegador bloquejava les peticions amb aquest error:
```
Access to XMLHttpRequest at 'https://...railway.app/api/v1/conflicts'
from origin 'https://...vercel.app' has been blocked by CORS policy
```

El backend no tenia cap configuració CORS. Es va crear el fitxer `CorsConfig.java`:
```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Value("${FRONTEND_URL:http://localhost:5173}")
    private String frontendUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(frontendUrl)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false);
    }
}
```

---

### Frontend

**Error 3 – URL de l'API apuntava a localhost**

Totes les crides axios usaven rutes relatives com `/api/v1/conflicts`, que funcionaven en local gràcies al proxy de Vite. En producció, aquest proxy no existeix i les crides fallaven amb 404.

Solució al `src/stores/conflicts.js`:
```javascript
// Afegir al principi del fitxer
const BASE = import.meta.env.VITE_API_URL || ''

// Canviar totes les crides, per exemple:
// Abans:  axios.get('/api/v1/conflicts')
// Després: axios.get(`${BASE}/api/v1/conflicts`)
```

---

**Error 4 – Error 404 en refrescar rutes**

En accedir directament a `/conflicts/5` o refrescar la pàgina, Vercel retornava 404 perquè buscava un fitxer estàtic que no existia. Vue Router gestiona les rutes al client, però Vercel no ho sap.

Solució – crear `vercel.json` a l'arrel del projecte:
```json
{
  "rewrites": [
    { "source": "/(.*)", "destination": "/index.html" }
  ]
}
```

---

**Error 5 – Errors de l'API contaminaven l'estat de Pinia**

Quan el backend retornava un error, el valor que s'assignava a `conflicts.value` no era un array sinó un objecte d'error de Spring. Això trencava tots els `computed` que esperaven un array.

Solució – validar la resposta abans d'assignar-la:
```javascript
const { data } = await axios.get(`${BASE}/api/v1/conflicts`)
if (Array.isArray(data)) {
  conflicts.value = data
} else {
  conflicts.value = []
  error.value = 'Resposta inesperada del servidor.'
}
```
