# Documentación de Arquitectura - Product Service

## Clean Architecture

Este microservicio implementa Clean Architecture (también conocida como Arquitectura Hexagonal o Ports & Adapters).

## Capas

### 1. Domain Layer (Capa de Dominio)
- **Ubicación**: `domain/`
- **Responsabilidad**: Contiene la lógica de negocio pura
- **Componentes**:
  - `model/Product.java`: Entidad de dominio con validaciones de negocio
  - `repository/ProductRepository.java`: Interface que define el contrato de persistencia
  - `exception/`: Excepciones de dominio

### 2. Application Layer (Capa de Aplicación)
- **Ubicación**: `application/`
- **Responsabilidad**: Orquesta los casos de uso
- **Componentes**:
  - `usecase/`: Casos de uso individuales (SRP)
  - `service/ProductApplicationService.java`: Servicio que orquesta los casos de uso

### 3. Infrastructure Layer (Capa de Infraestructura)
- **Ubicación**: `infrastructure/`
- **Responsabilidad**: Implementa los puertos definidos en el dominio
- **Componentes**:
  - `persistence/entity/ProductEntity.java`: Entidad JPA
  - `persistence/repository/JpaProductRepository.java`: Interface Spring Data
  - `persistence/repository/ProductRepositoryImpl.java`: Adaptador que implementa el puerto

### 4. Presentation Layer (Capa de Presentación)
- **Ubicación**: `presentation/`
- **Responsabilidad**: Maneja las peticiones HTTP
- **Componentes**:
  - `controller/ProductController.java`: Controlador REST
  - `controller/GlobalExceptionHandler.java`: Manejo de excepciones
  - `dto/`: Data Transfer Objects
  - `mapper/ProductDtoMapper.java`: Mapeo entre DTOs y dominio

## Flujo de Dependencias

```
Presentation → Application → Domain ← Infrastructure
```

- La capa de Dominio no depende de ninguna otra capa
- Las capas externas dependen de las internas
- La Infraestructura implementa interfaces definidas en el Dominio

## Patrón de Casos de Uso

Cada caso de uso es una clase independiente con un único método `execute()`:

```java
@Component
public class CreateProductUseCase {
    public Product execute(Product product) { ... }
}
```

Esto permite:
- **Single Responsibility**: Cada clase tiene una única responsabilidad
- **Open/Closed**: Fácil agregar nuevos casos de uso
- **Testabilidad**: Cada caso de uso puede probarse de forma aislada

## Mapeo de Datos con MapStruct

El proyecto usa MapStruct para el mapeo automático entre objetos:

- `ProductDtoMapper`: Convierte entre DTOs de presentación y modelo de dominio
- `ProductPersistenceMapper`: Convierte entre entidad JPA y modelo de dominio

### Ejemplo de Mapper MapStruct

```java
@Mapper(componentModel = "spring")
public interface ProductDtoMapper {
    
    Product toDomain(CreateProductRequest request);
    
    @Mapping(target = "available", expression = "java(product.isAvailable())")
    ProductResponse toResponse(Product product);
    
    List<ProductResponse> toResponseList(List<Product> products);
}
```

MapStruct genera automáticamente la implementación en tiempo de compilación, proporcionando:
- **Type Safety**: Errores detectados en compilación
- **Performance**: No usa reflection en runtime
- **Mantenibilidad**: Código generado fácil de debuggear

## Ventajas de esta Arquitectura

1. **Desacoplamiento**: Las capas están claramente separadas
2. **Testabilidad**: Cada componente puede probarse de forma aislada
3. **Mantenibilidad**: Cambios en una capa no afectan a las demás
4. **Flexibilidad**: Fácil cambiar implementaciones (BD, UI, etc.)
