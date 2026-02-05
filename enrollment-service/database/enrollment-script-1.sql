-- ============================================
-- enrollment-service - Tabla enrollments (TRABAJO_FINAL.md)
-- Database: enrollmentdb
-- ============================================

CREATE TABLE enrollments (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    status VARCHAR(40) DEFAULT 'PENDING_PAYMENT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
