INSERT INTO members (name, last_name, dni, email, birthdate, phone, address, type, status, is_active) VALUES
('Ana', 'Ramírez', '34567890', 'ejemplo@gmail.com', '1992-04-15', '3511234567', 'Av. Siempre Viva 742', 'ACTIVE', 'APPROVED', true),
('Luis', 'Fernández', '29876543', '1999francogarcia@gmail.com', '1980-09-23', '3417654321', 'Calle Falsa 123', 'ACTIVE', 'APPROVED', true),
('Clara', 'Martínez', '40789012', 'clara.martinez@example.com', '2001-12-02', null, null, 'ACTIVE', 'APPROVED', true),
('Sofía', 'Pérez', '33445566', 'sofia.perez@example.com', '1995-07-08', '3812233445', 'San Martín 456', 'ACTIVE', 'PENDING', false),
('Diego', 'López', '37890123', 'diego.lopez@example.com', '1988-03-30', '3879876543', 'Belgrano 789', 'ACTIVE', 'PENDING', true);


INSERT INTO fees (month_period, year_period, amount) VALUES
(1, 2025, 5000),
(2, 2025, 5000),
(3, 2025, 5000),
(4, 2025, 5000),
(5, 2025, 5000),
(6, 2025, 5000);

INSERT INTO payments (member_id, fee_id, issued_date, payment_date, status, method, mercado_pago_id, recorded_by) VALUES
(1, 1, '2025-05-01', '2025-05-01', 'PENDING', 'TRANSFER', NULL, NULL),
(2, 1, '2025-05-02', '2025-05-02', 'PENDING', 'TRANSFER', NULL, NULL),
(3, 1, '2025-05-03', '2025-05-03', 'APPROVED', 'TRANSFER', NULL, NULL),
(4, 1, '2025-05-04', '2025-05-04', 'APPROVED', 'TRANSFER', NULL, NULL),
(1, 2, '2025-05-05', '2025-05-05', 'PENDING', 'MERCADO_PAGO', NULL, NULL);


INSERT INTO users (name, last_name, email, password, role, is_active) VALUES
('Admin', 'Admin', 'admin@correo.com', 'admin123', 'ADMIN', true),
('President', 'President', 'president@correo.com', 'president123', 'PRESIDENT', true);


INSERT INTO teams (name, description, sport)
VALUES ('Basquet Masculino', 'Equipo de basquet', 'BASKETBALL')