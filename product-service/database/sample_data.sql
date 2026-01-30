-- ============================================
-- sample_data.sql - Datos de muestra adicionales
-- ============================================

-- Más productos de ejemplo
INSERT INTO products (name, description, price, stock, category, created_by) VALUES
('iPhone 15 Pro', 'Smartphone Apple de última generación', 1199.99, 25, 'Electronics', 1),
('Samsung Galaxy S24', 'Smartphone Android premium', 999.99, 30, 'Electronics', 2),
('iPad Pro 12.9"', 'Tablet profesional Apple', 1099.99, 12, 'Electronics', 1),
('AirPods Pro 2', 'Auriculares inalámbricos Apple', 249.99, 45, 'Electronics', 3),
('MacBook Air M3', 'Laptop ultraligera Apple', 1299.99, 18, 'Electronics', 2),
('Webcam Logitech C920', 'Cámara web HD para streaming', 79.99, 60, 'Electronics', 1),
('Disco SSD Samsung 1TB', 'Almacenamiento NVMe rápido', 129.99, 40, 'Electronics', 3),
('Cable USB-C 2m', 'Cable de carga rápida', 19.99, 100, 'Accessories', 1),
('Soporte Monitor Ajustable', 'Soporte ergonómico de escritorio', 89.99, 25, 'Accessories', 2),
('Hub USB-C 7 en 1', 'Adaptador multipuerto', 49.99, 35, 'Accessories', 3)
ON CONFLICT DO NOTHING;
