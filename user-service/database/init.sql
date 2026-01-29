-- ============================================
-- Script de inicialización de base de datos
-- Base de datos: userdb
-- ============================================

-- Crear la base de datos (ejecutar como superusuario)
-- CREATE DATABASE userdb;
-- \c userdb;

-- Función para actualizar updated_at automáticamente
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Tabla users (será creada por JPA si usas ddl-auto=update)
-- Este script es opcional si prefieres crear la tabla manualmente
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT chk_name_not_empty CHECK (LENGTH(TRIM(name)) > 0),
    CONSTRAINT chk_email_format CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);

-- Índices
CREATE UNIQUE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_name ON users(name);
CREATE INDEX IF NOT EXISTS idx_users_created_at ON users(created_at DESC);

-- Trigger para updated_at
DROP TRIGGER IF EXISTS update_users_updated_at ON users;
CREATE TRIGGER update_users_updated_at
    BEFORE UPDATE ON users
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Comentarios
COMMENT ON TABLE users IS 'Usuarios del sistema - Clean Architecture';
COMMENT ON COLUMN users.id IS 'Identificador único del usuario';
COMMENT ON COLUMN users.name IS 'Nombre completo del usuario';
COMMENT ON COLUMN users.email IS 'Email único del usuario';
COMMENT ON COLUMN users.phone IS 'Teléfono de contacto';
COMMENT ON COLUMN users.address IS 'Dirección del usuario';
COMMENT ON COLUMN users.created_at IS 'Fecha de creación';
COMMENT ON COLUMN users.updated_at IS 'Fecha de última actualización';
