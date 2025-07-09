# Implementación HATEOAS en Microservicios

Este proyecto ahora incluye soporte completo para HATEOAS (Hypermedia as the Engine of Application State) en ambos microservicios: **Producto** y **Venta**.

## ¿Qué es HATEOAS?

HATEOAS es un principio de REST que permite que las respuestas de la API incluyan enlaces a otros recursos relacionados, facilitando la navegación y descubrimiento de la API.

## Características Implementadas

### 1. **Respuestas con Enlaces HATEOAS**
- Todas las respuestas ahora incluyen enlaces a recursos relacionados
- Formato HAL (Hypertext Application Language) estándar
- Enlaces de navegación automáticos

### 2. **Microservicio Producto**

#### Endpoints disponibles:
- `POST /api/v1/producto/create` - Crear producto
- `GET /api/v1/producto/all` - Obtener todos los productos
- `GET /api/v1/producto/search/{id}` - Buscar producto por ID
- `PUT /api/v1/producto/update/{id}` - Actualizar producto
- `DELETE /api/v1/producto/delete/{id}` - Eliminar producto
- `GET /api/v1/producto/search-venta/{idProducto}` - Buscar ventas por producto

#### Ejemplo de respuesta HATEOAS para un producto:
```json
{
  "id": 1,
  "name": "Laptop",
  "modelo": "ThinkPad X1",
  "_links": {
    "self": {
      "href": "http://localhost:8081/api/v1/producto/search/1"
    },
    "productos": {
      "href": "http://localhost:8081/api/v1/producto/all"
    },
    "ventas": {
      "href": "http://localhost:8081/api/v1/producto/search-venta/1"
    },
    "update": {
      "href": "http://localhost:8081/api/v1/producto/update/1"
    },
    "delete": {
      "href": "http://localhost:8081/api/v1/producto/delete/1"
    }
  }
}
```

### 3. **Microservicio Venta**

#### Endpoints disponibles:
- `POST /api/v1/venta/create` - Crear venta
- `GET /api/v1/venta/all` - Obtener todas las ventas
- `GET /api/v1/venta/search/{id}` - Buscar venta por ID
- `GET /api/v1/venta/search-by-producto/{productoId}` - Buscar ventas por producto

#### Ejemplo de respuesta HATEOAS para una venta:
```json
{
  "id": 1,
  "name": "Venta Laptop",
  "idventa": "V001",
  "ventaId": 1,
  "productoId": 1,
  "_links": {
    "self": {
      "href": "http://localhost:8082/api/v1/venta/search/1"
    },
    "ventas": {
      "href": "http://localhost:8082/api/v1/venta/all"
    },
    "ventas-por-producto": {
      "href": "http://localhost:8082/api/v1/venta/search-by-producto/1"
    },
    "create": {
      "href": "http://localhost:8082/api/v1/venta/create"
    }
  }
}
```

## Cómo Probar

### 1. **Crear un Producto**
```bash
curl -X POST http://localhost:8081/api/v1/producto/create \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop Gaming",
    "modelo": "ROG Strix"
  }'
```

### 2. **Obtener Todos los Productos**
```bash
curl http://localhost:8081/api/v1/producto/all
```

### 3. **Buscar Producto por ID**
```bash
curl http://localhost:8081/api/v1/producto/search/1
```

### 4. **Crear una Venta**
```bash
curl -X POST http://localhost:8082/api/v1/venta/create \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Venta Laptop Gaming",
    "idventa": "V001",
    "ventaId": 1,
    "productoId": 1
  }'
```

### 5. **Obtener Todas las Ventas**
```bash
curl http://localhost:8082/api/v1/venta/all
```

## Beneficios de HATEOAS

1. **Descubrimiento de API**: Los clientes pueden navegar por la API siguiendo los enlaces
2. **Desacoplamiento**: Los clientes no necesitan conocer las URLs específicas
3. **Evolución**: Los enlaces pueden cambiar sin afectar a los clientes
4. **Documentación**: Los enlaces sirven como documentación automática
5. **Navegación**: Facilita la navegación entre recursos relacionados

## Estructura de Enlaces

### Producto:
- `self`: Enlace al propio producto
- `productos`: Enlace a la lista de todos los productos
- `ventas`: Enlace a las ventas asociadas al producto
- `update`: Enlace para actualizar el producto
- `delete`: Enlace para eliminar el producto

### Venta:
- `self`: Enlace a la propia venta
- `ventas`: Enlace a la lista de todas las ventas
- `ventas-por-producto`: Enlace a las ventas del producto asociado
- `create`: Enlace para crear una nueva venta

## Configuración Técnica

### Dependencias Agregadas:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>
```

### Configuración:
- `@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)`
- DTOs que extienden `RepresentationModel`
- Servicios `HateoasService` para agregar enlaces automáticamente

## Notas Importantes

1. **Formato HAL**: Las respuestas usan el formato HAL estándar
2. **Enlaces Relativos**: Los enlaces son generados automáticamente
3. **Compatibilidad**: Mantiene compatibilidad con clientes existentes
4. **Performance**: Los enlaces se generan dinámicamente sin impacto significativo en el rendimiento

¡Tu API ahora es completamente HATEOAS-compliant! 🚀 