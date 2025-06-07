INSERT INTO members (name, last_name, dni, email, birthdate, phone, address, type, status) VALUES
('Ana', 'Ramírez', '34567890', 'ejemplo@gmail.com', '1992-04-15', '3511234567', 'Av. Siempre Viva 742', 'ACTIVE', 'ACTIVE'),
('Luis', 'Fernández', '29876543', '1999francogarcia@gmail.com', '1980-09-23', '3417654321', 'Calle Falsa 123', 'ACTIVE', 'ACTIVE'),
('Clara', 'Martínez', '40789012', 'clara.martinez@example.com', '2001-12-02', null, null, 'ACTIVE', 'INACTIVE'),
('Sofía', 'Pérez', '33445566', 'sofia.perez@example.com', '1995-07-08', '3812233445', 'San Martín 456', 'ACTIVE', 'PENDING'),
('Diego', 'López', '37890123', 'diego.lopez@example.com', '1988-03-30', '3879876543', 'Belgrano 789', 'ACTIVE', 'PENDING'),
('Valeria', 'Gómez', '31234567', 'valeria.gomez@mail.com', '1990-05-10', '3519988776', 'Los Álamos 123', 'ACTIVE', 'ACTIVE'),
('Mariano', 'Sosa', '29876123', 'mariano.sosa@mail.com', '1985-11-25', '3498765432', 'Mitre 800', 'ACTIVE', 'INACTIVE'),
('Camila', 'Torres', '41789012', 'camila.torres@example.com', '2002-01-19', '3512233445', 'La Rioja 456', 'ACTIVE', 'ACTIVE'),
('Carlos', 'Méndez', '28765432', 'carlos.mendez@example.com', '1979-06-30', '3819988776', 'Av. Colón 999', 'ACTIVE', 'ACTIVE'),
('Lucía', 'Ruiz', '39871234', 'lucia.ruiz@gmail.com', '1996-03-14', '3414455667', 'Tucumán 234', 'ACTIVE', 'PENDING'),
('Juan', 'Díaz', '33440011', 'juan.diaz@mail.com', '1993-08-17', '3431122334', 'España 1010', 'ACTIVE', 'ACTIVE'),
('Bruno', 'Castro', '31887766', 'bruno.castro@example.com', '1987-12-05', '3513344556', 'Buenos Aires 55', 'ACTIVE', 'INACTIVE'),
('Julieta', 'Moreno', '40998877', 'julieta.moreno@mail.com', '2000-10-28', '3873344221', 'Santa Fe 89', 'ACTIVE', 'PENDING'),
('Emilia', 'Silva', '37771234', 'emilia.silva@gmail.com', '1999-07-07', '3819981234', 'Entre Ríos 321', 'ACTIVE', 'ACTIVE'),
('Tomás', 'Paz', '30445566', 'tomas.paz@mail.com', '1991-01-02', '3541554433', 'Lavalle 567', 'ACTIVE', 'ACTIVE'),
('Natalia', 'Herrera', '32889900', 'natalia.herrera@example.com', '1986-04-09', '3421112233', 'Av. Alem 222', 'ACTIVE', 'INACTIVE'),
('Facundo', 'Ibarra', '31223344', 'facundo.ibarra@gmail.com', '1994-09-15', '3517778899', 'Urquiza 787', 'ACTIVE', 'PENDING'),
('Martina', 'Luna', '41882233', 'martina.luna@mail.com', '2003-03-22', null, null, 'ACTIVE', 'ACTIVE'),
('Pedro', 'Ojeda', '36667788', 'pedro.ojeda@example.com', '1983-05-12', '3439998888', 'Corrientes 144', 'ACTIVE', 'ACTIVE'),
('Micaela', 'Acosta', '33775599', 'micaela.acosta@mail.com', '1997-06-18', '3543123456', 'Av. Rivadavia 300', 'ACTIVE', 'PENDING'),
('Ramiro', 'Villalba', '29998877', 'ramiro.villalba@mail.com', '1989-11-01', '3812345678', 'Dorrego 666', 'ACTIVE', 'ACTIVE'),
('Antonella', 'Navarro', '37665544', 'antonella.navarro@example.com', '1998-02-10', '3423344556', 'Alberdi 909', 'ACTIVE', 'INACTIVE'),
('Joaquín', 'Peralta', '31001122', 'joaquin.peralta@mail.com', '1992-08-03', '3435556677', 'Vélez Sarsfield 321', 'ACTIVE', 'PENDING'),
('Florencia', 'Correa', '42778899', 'florencia.correa@gmail.com', '2004-12-14', null, null, 'ACTIVE', 'ACTIVE'),
('Andrés', 'Rojas', '28880011', 'andres.rojas@example.com', '1984-10-27', '3541998877', 'Maipú 1100', 'ACTIVE', 'ACTIVE');


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
VALUES ('Basquet Masculino', 'Equipo de basquet', 'BASKETBALL');

INSERT INTO teams (name, description, sport)
VALUES ('Futbol femenino', 'Entrenamientos: martes y jueves 18:00hs', 'FOOTBALL');

INSERT INTO team_members(team_id, member_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10);

INSERT INTO team_members(team_id, member_id) VALUES
(2, 11),
(2, 12),
(2, 13),
(2, 14),
(2, 15),
(2, 16),
(2, 17),
(2, 18);


INSERT INTO news(title, summary, content, image_url, news_date, status, is_active) VALUES
('El basquet sigue con la racha de victorias', 'El equipo de basquet logró otra victoria para continuar puntero en la tabla',
 'Durante la jornada del sábado, el equipo de básquet pudo obtener otra victoria por 60 a 42, continuando así como punteros del torneo. Recordemos que el equipo continúa invicto, y vienen encaminados al título.',
 'https://res.cloudinary.com/dkcpxysrt/image/upload/v1749160177/ayuaae4dnqzeawbnttly.jpg', '2025-05-06', 'PUBLISHED', true);


INSERT INTO news(title, summary, content, image_url, news_date, status, is_active) VALUES
('El basquet sigue con la racha de victorias', 'El equipo de basquet logró otra victoria para continuar puntero en la tabla',
 'Durante la jornada del sábado, el equipo de básquet pudo obtener otra victoria por 60 a 42, continuando así como punteros del torneo. Recordemos que el equipo continúa invicto, y vienen encaminados al título.',
 'https://res.cloudinary.com/dkcpxysrt/image/upload/v1749160177/ayuaae4dnqzeawbnttly.jpg', '2025-05-06', 'PUBLISHED', true);


INSERT INTO news(title, summary, content, image_url, news_date, status, is_active) VALUES
('El basquet sigue con la racha de victorias', 'El equipo de basquet logró otra victoria para continuar puntero en la tabla',
 'Durante la jornada del sábado, el equipo de básquet pudo obtener otra victoria por 60 a 42, continuando así como punteros del torneo. Recordemos que el equipo continúa invicto, y vienen encaminados al título.',
 'https://res.cloudinary.com/dkcpxysrt/image/upload/v1749160177/ayuaae4dnqzeawbnttly.jpg', '2025-05-06', 'PUBLISHED', true);


INSERT INTO news(title, summary, content, image_url, news_date, status, is_active) VALUES
('El basquet sigue con la racha de victorias', 'El equipo de basquet logró otra victoria para continuar puntero en la tabla',
 'Durante la jornada del sábado, el equipo de básquet pudo obtener otra victoria por 60 a 42, continuando así como punteros del torneo. Recordemos que el equipo continúa invicto, y vienen encaminados al título.',
 'https://res.cloudinary.com/dkcpxysrt/image/upload/v1749160177/ayuaae4dnqzeawbnttly.jpg', '2025-05-06', 'PUBLISHED', true);


INSERT INTO news(title, summary, content, image_url, news_date, status, is_active) VALUES
('El basquet sigue con la racha de victorias', 'El equipo de basquet logró otra victoria para continuar puntero en la tabla',
 'Durante la jornada del sábado, el equipo de básquet pudo obtener otra victoria por 60 a 42, continuando así como punteros del torneo. Recordemos que el equipo continúa invicto, y vienen encaminados al título.',
 'https://res.cloudinary.com/dkcpxysrt/image/upload/v1749160177/ayuaae4dnqzeawbnttly.jpg', '2025-05-06', 'PUBLISHED', true);


INSERT INTO news(title, summary, content, image_url, news_date, status, is_active) VALUES
('El basquet sigue con la racha de victorias', 'El equipo de basquet logró otra victoria para continuar puntero en la tabla',
 'Durante la jornada del sábado, el equipo de básquet pudo obtener otra victoria por 60 a 42, continuando así como punteros del torneo. Recordemos que el equipo continúa invicto, y vienen encaminados al título.',
 'https://res.cloudinary.com/dkcpxysrt/image/upload/v1749160177/ayuaae4dnqzeawbnttly.jpg', '2025-05-06', 'PUBLISHED', true);


INSERT INTO news(title, summary, content, image_url, news_date, status, is_active) VALUES
('El basquet sigue con la racha de victorias', 'El equipo de basquet logró otra victoria para continuar puntero en la tabla',
 'Durante la jornada del sábado, el equipo de básquet pudo obtener otra victoria por 60 a 42, continuando así como punteros del torneo. Recordemos que el equipo continúa invicto, y vienen encaminados al título.',
 'https://res.cloudinary.com/dkcpxysrt/image/upload/v1749160177/ayuaae4dnqzeawbnttly.jpg', '2025-05-06', 'PUBLISHED', true);