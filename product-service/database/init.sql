-- ============================================
-- init.sql - Script de inicialización completo
-- Base de datos: productdb
-- ============================================

-- Función para auto-actualizar updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Tabla de productos
CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    price NUMERIC(10, 2) NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    category VARCHAR(50),
    created_by BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_name_not_empty CHECK (LENGTH(TRIM(name)) > 0),
    CONSTRAINT chk_price_positive CHECK (price >= 0),
    CONSTRAINT chk_stock_non_negative CHECK (stock >= 0)
);

-- Trigger para actualizar updated_at
DROP TRIGGER IF EXISTS update_products_updated_at ON products;
CREATE TRIGGER update_products_updated_at
    BEFORE UPDATE ON products
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Índices
CREATE INDEX IF NOT EXISTS idx_products_category ON products(category);
CREATE INDEX IF NOT EXISTS idx_products_created_by ON products(created_by);
CREATE INDEX IF NOT EXISTS idx_products_price ON products(price);
CREATE INDEX IF NOT EXISTS idx_products_stock ON products(stock);
CREATE INDEX IF NOT EXISTS idx_products_created_at ON products(created_at DESC);

-- Comentarios
COMMENT ON TABLE products IS 'Productos del sistema - DB en Docker';
COMMENT ON COLUMN products.created_by IS 'Usuario creador (ref. lógica a userdb.users.id en otro contenedor)';

-- Datos iniciales
INSERT INTO products (name, description, price, stock, category, created_by) VALUES
('Laptop Dell XPS 15', 'Laptop empresarial de alta gama', 1299.99, 15, 'Electronics', 1),
('Mouse Logitech MX Master 3', 'Mouse inalámbrico ergonómico', 99.99, 50, 'Electronics', 1),
('Teclado Mecánico Keychron K8', 'Teclado mecánico RGB', 89.99, 30, 'Electronics', 2),
('Monitor LG UltraWide 34"', 'Monitor curvo UltraWide', 449.99, 8, 'Electronics', 2),
('Auriculares Sony WH-1000XM5', 'Auriculares con cancelación de ruido', 349.99, 20, 'Electronics', 3)
ON CONFLICT DO NOTHING;
