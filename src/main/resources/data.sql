INSERT INTO users (name, last_name, email, password, role, is_active)
VALUES ('admin', 'admin', 'admin@correo.com', 'admin123', 'ADMIN', true);

INSERT INTO members (name, last_name, dni, email, birthdate, phone, address, type, is_active) VALUES
('Ana', 'Ramírez', '34567890', 'ana.ramirez@example.com', '1992-04-15', '3511234567', 'Av. Siempre Viva 742', 'ACTIVE', true),
('Luis', 'Fernández', '29876543', 'luis.fernandez@example.com', '1980-09-23', '3417654321', 'Calle Falsa 123', 'ACTIVE', true),
('Clara', 'Martínez', '40789012', 'clara.martinez@example.com', '2001-12-02', null, null, 'ACTIVE', true),
('Sofía', 'Pérez', '33445566', 'sofia.perez@example.com', '1995-07-08', '3812233445', 'San Martín 456', 'ACTIVE', false),
('Diego', 'López', '37890123', 'diego.lopez@example.com', '1988-03-30', '3879876543', 'Belgrano 789', 'ACTIVE', true);


