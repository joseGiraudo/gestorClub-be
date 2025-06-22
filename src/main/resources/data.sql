INSERT INTO members (name, last_name, dni, email, birthdate, phone, address, type, status, created_date) VALUES
-- ABRIL
('Ana', 'Ramírez', '34567890', 'ejemplo@gmail.com', '1992-04-15', '3511234567', 'Av. Siempre Viva 742', 'ACTIVE', 'ACTIVE', '2025-04-05T10:00:00'),
('Luis', 'Fernández', '29876543', '1999francogarcia@gmail.com', '1980-09-23', '3417654321', 'Calle Falsa 123', 'ACTIVE', 'ACTIVE', '2025-04-06T10:00:00'),
('Clara', 'Martínez', '40789012', 'clara.martinez@example.com', '2001-12-02', null, null, 'ACTIVE', 'INACTIVE', '2025-04-07T10:00:00'),
('Sofía', 'Pérez', '33445566', 'sofia.perez@example.com', '1995-07-08', '3812233445', 'San Martín 456', 'ACTIVE', 'PENDING', '2025-04-08T10:00:00'),
('Diego', 'López', '37890123', 'diego.lopez@example.com', '1988-03-30', '3879876543', 'Belgrano 789', 'ACTIVE', 'PENDING', '2025-04-09T10:00:00'),
-- MAYO
('Valeria', 'Gómez', '31234567', 'valeria.gomez@mail.com', '1990-05-10', '3519988776', 'Los Álamos 123', 'ACTIVE', 'ACTIVE', '2025-05-01T10:00:00'),
('Mariano', 'Sosa', '29876123', 'mariano.sosa@mail.com', '1985-11-25', '3498765432', 'Mitre 800', 'ACTIVE', 'INACTIVE', '2025-05-02T10:00:00'),
('Camila', 'Torres', '41789012', 'camila.torres@example.com', '2002-01-19', '3512233445', 'La Rioja 456', 'ACTIVE', 'ACTIVE', '2025-05-03T10:00:00'),
('Carlos', 'Méndez', '28765432', 'carlos.mendez@example.com', '1979-06-30', '3819988776', 'Av. Colón 999', 'ACTIVE', 'ACTIVE', '2025-05-04T10:00:00'),
('Lucía', 'Ruiz', '39871234', 'lucia.ruiz@gmail.com', '1996-03-14', '3414455667', 'Tucumán 234', 'ACTIVE', 'PENDING', '2025-05-05T10:00:00'),
('Juan', 'Díaz', '33440011', 'juan.diaz@mail.com', '1993-08-17', '3431122334', 'España 1010', 'ACTIVE', 'ACTIVE', '2025-05-06T10:00:00'),
('Bruno', 'Castro', '31887766', 'bruno.castro@example.com', '1987-12-05', '3513344556', 'Buenos Aires 55', 'ACTIVE', 'INACTIVE', '2025-05-07T10:00:00'),
('Julieta', 'Moreno', '40998877', 'julieta.moreno@mail.com', '2000-10-28', '3873344221', 'Santa Fe 89', 'ACTIVE', 'PENDING', '2025-05-08T10:00:00'),
('Emilia', 'Silva', '37771234', 'emilia.silva@gmail.com', '1999-07-07', '3819981234', 'Entre Ríos 321', 'ACTIVE', 'ACTIVE', '2025-05-09T10:00:00'),
('Tomás', 'Paz', '30445566', 'tomas.paz@mail.com', '1991-01-02', '3541554433', 'Lavalle 567', 'ACTIVE', 'ACTIVE', '2025-05-10T10:00:00'),
-- JUNIO
('Natalia', 'Herrera', '32889900', 'natalia.herrera@example.com', '1986-04-09', '3421112233', 'Av. Alem 222', 'ACTIVE', 'INACTIVE', '2025-06-01T10:00:00'),
('Facundo', 'Ibarra', '31223344', 'facundo.ibarra@gmail.com', '1994-09-15', '3517778899', 'Urquiza 787', 'ACTIVE', 'PENDING', '2025-06-02T10:00:00'),
('Martina', 'Luna', '41882233', 'martina.luna@mail.com', '2003-03-22', null, null, 'ACTIVE', 'ACTIVE', '2025-06-03T10:00:00'),
('Pedro', 'Ojeda', '36667788', 'pedro.ojeda@example.com', '1983-05-12', '3439998888', 'Corrientes 144', 'ACTIVE', 'ACTIVE', '2025-06-04T10:00:00'),
('Micaela', 'Acosta', '33775599', 'micaela.acosta@mail.com', '1997-06-18', '3543123456', 'Av. Rivadavia 300', 'ACTIVE', 'PENDING', '2025-06-05T10:00:00'),
('Ramiro', 'Villalba', '29998877', 'ramiro.villalba@mail.com', '1989-11-01', '3812345678', 'Dorrego 666', 'ACTIVE', 'ACTIVE', '2025-06-06T10:00:00'),
('Antonella', 'Navarro', '37665544', 'antonella.navarro@example.com', '1998-02-10', '3423344556', 'Alberdi 909', 'ACTIVE', 'INACTIVE', '2025-06-07T10:00:00'),
('Joaquín', 'Peralta', '31001122', 'joaquin.peralta@mail.com', '1992-08-03', '3435556677', 'Vélez Sarsfield 321', 'ACTIVE', 'PENDING', '2025-06-08T10:00:00'),
('Florencia', 'Correa', '42778899', 'florencia.correa@gmail.com', '2004-12-14', null, null, 'ACTIVE', 'ACTIVE', '2025-06-09T10:00:00'),
('Andrés', 'Rojas', '28880011', 'andres.rojas@example.com', '1984-10-27', '3541998877', 'Maipú 1100', 'ACTIVE', 'ACTIVE', '2025-06-10T10:00:00');



INSERT INTO fees (month_period, year_period, amount) VALUES
(1, 2025, 5000),
(2, 2025, 5000),
(3, 2025, 5000),
(4, 2025, 5000),
(5, 2025, 5000),
(6, 2025, 5000);

INSERT INTO payments (member_id, fee_id, issued_date, payment_date, status, method, mercado_pago_id, recorded_by) VALUES
(1, 1, '2025-05-01', '2025-05-01', 'APPROVED', 'TRANSFER', NULL, NULL),
(2, 1, '2025-05-02', NULL, 'PENDING', NULL, NULL, NULL),
(3, 1, '2025-05-03', NULL, 'PENDING', NULL, NULL, NULL),
(4, 1, '2025-05-04', '2025-05-04', 'APPROVED', 'TRANSFER', NULL, NULL),
(5, 1, '2025-05-04', NULL, 'PENDING', NULL, NULL, NULL),
(6, 1, '2025-05-04', NULL, 'PENDING', NULL, NULL, NULL),
(7, 1, '2025-05-04', NULL, 'PENDING', NULL, NULL, NULL),
(8, 1, '2025-05-04', '2025-05-04', 'APPROVED', 'TRANSFER', NULL, NULL),
(9, 1, '2025-05-04', '2025-05-04', 'APPROVED', 'TRANSFER', NULL, NULL),
(1, 2, '2025-05-04', '2025-05-04', 'APPROVED', 'TRANSFER', NULL, NULL),
(2, 2, '2025-05-04', '2025-05-04', 'APPROVED', 'TRANSFER', NULL, NULL),
(3, 2, '2025-05-05', NULL, 'PENDING', NULL, NULL, NULL);


INSERT INTO users (name, last_name, email, password, role, is_active) VALUES
('Admin', 'Admin', 'admin@correo.com', 'admin123', 'ADMIN', true),
('President', 'President', 'president@correo.com', 'president123', 'PRESIDENT', true);


INSERT INTO teams (name, description, sport)
VALUES ('Basquet Masculino', 'Equipo de basquet', 'BASKETBALL');

INSERT INTO teams (name, description, sport)
VALUES ('Futbol femenino', 'Entrenamientos: martes y jueves 18:00hs', 'FOOTBALL');

INSERT INTO teams (name, description, sport)
VALUES ('Futbol Masculino', 'Equipo de futbol 7. entrenamientos miercoles 21:00hs y partidos sabados', 'FOOTBALL');

INSERT INTO teams (name, description, sport)
VALUES ('Futbol femenino B', 'Entrenamientos: martes y jueves 20:00hs', 'FOOTBALL');

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
('Se juega una nueva fecha del básquet masculino', 'El próximo sábado se disputa la fecha 7 del torneo, donde enfrentan a El Rejunte',
 'El equipo buscará alcanzar una nueva victoria para mantenerse en lo alto de la tabla, luego de un fin de semana de descanso. \n Con algunas bajas por lesiones, son 9 los jugadores que se presentarán a defender los colores del club, y se espera un partido apretado.\n Vamos Inde!',
 'https://res.cloudinary.com/dkcpxysrt/image/upload/v1750443094/xf6jntwfv3am86v50a5r.png', '2025-06-20', 'PUBLISHED', true);

INSERT INTO news(title, summary, content, image_url, news_date, status, is_active) VALUES
('Victoria ajustada en casa', 'El equipo ganó por pocos puntos en un partido clave',
 'En un encuentro muy parejo, el equipo local se impuso 78 a 75, consolidando su racha de victorias en casa y manteniéndose entre los primeros puestos. \nAsí suma su cuarta victoria en la temporada y se mantiene en los primeros puestos del torneo.',
 'https://media.istockphoto.com/id/2162305941/photo/portrait-of-professional-womens-basketball-team.jpg?s=1024x1024&w=is&k=20&c=UwYYh9wTPQE3dTaLqJAf98czLLgszFVCAIkr-JCQhVY=', '2025-06-10', 'PUBLISHED', true);

INSERT INTO news(title, summary, content, image_url, news_date, status, is_active) VALUES
('Récord defensivo en la última fecha', 'El conjunto logró el menor puntaje recibido de la temporada',
 'Con una defensa sólida, el equipo solo permitió 55 puntos al rival, estableciendo así un nuevo récord defensivo en la campaña.',
 'https://cdn.pixabay.com/photo/2021/09/27/13/25/football-6660800_1280.jpg', '2025-06-08', 'PUBLISHED', true);

INSERT INTO news(title, summary, content, image_url, news_date, status, is_active) VALUES
('Jugadora destacada del mes', 'La capitana fue elegida MVP del mes en la liga',
 'Gracias a su promedio de 20 puntos y 8 rebotes, la capitana del equipo fue nombrada mejor jugadora del mes por la prensa especializada.',
 'https://media.istockphoto.com/id/2148828024/photo/woman-taking-a-break-from-playing-at-the-basketball-court-and-texting-on-mobile.jpg?s=1024x1024&w=is&k=20&c=dV2IzYE8MVeTZtOTxMqXQGv4HLBP4tk6mU7vF88vtKk=', '2025-06-05', 'PUBLISHED', true);

INSERT INTO news(title, summary, content, image_url, news_date, status, is_active) VALUES
('Triunfo contundente en la ruta', 'El equipo visitante obtuvo una victoria sólida fuera de casa',
 'Lejos de su estadio, el equipo ganó 85 a 60, destacando el rendimiento colectivo y la efectividad en los tiros de 3 puntos.',
 'https://cdn.pixabay.com/photo/2024/02/26/14/13/people-8598065_1280.jpg', '2025-06-02', 'PUBLISHED', true);

INSERT INTO news(title, summary, content, image_url, news_date, status, is_active) VALUES
('El basquet sigue con la racha de victorias', 'El equipo de basquet logró otra victoria para continuar puntero en la tabla',
 'Durante la jornada del sábado, el equipo de básquet pudo obtener otra victoria por 60 a 42, continuando así como punteros del torneo. Recordemos que el equipo continúa invicto, y vienen encaminados al título.',
 'https://res.cloudinary.com/dkcpxysrt/image/upload/v1749160177/ayuaae4dnqzeawbnttly.jpg', '2025-05-06', 'PUBLISHED', true);
