-- ============================================
-- payment-service - Tabla payments (TRABAJO_FINAL.md)
-- Database: paymentdb
-- ============================================

CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    enrollment_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(30) DEFAULT 'APPROVED',
    paid_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
