
-- ==========================================
-- DRIVERS
-- ==========================================
INSERT INTO drivers (id, name, cnh_number, status) VALUES 
(1, 'Carlos Eduardo Silva', '123456', 'AVAILABLE'),
(2, 'Mariana Oliveira Santos', '234567', 'AVAILABLE'),
(3, 'Roberto Almeida Costa', '345678', 'BUSY'),
(4, 'Ana Paula Ferreira', '456789', 'AVAILABLE'),
(5, 'João Pedro Machado', '567890', 'OFF_DUTY'),
(6, 'Fernanda Lima Souza', '678901', 'AVAILABLE'),
(7, 'Ricardo Gomes Pereira', '789012', 'INACTIVE');

SELECT setval('seq_driver', 50, true);

-- ==========================================
-- VEHICLES
-- ==========================================
INSERT INTO vehicles (id, license_plate, model, status) VALUES 
(1, 'ABC1D23', 'Scania R 450', 'AVAILABLE'),
(2, 'DEF4G56', 'Mercedes-Benz Actros 2651', 'AVAILABLE'),
(3, 'GHI7890', 'Ford Cargo 2429', 'MAINTENANCE'),
(4, 'JKL1M23', 'Volkswagen Constellation 24.280', 'AVAILABLE'),
(5, 'MNO4P56', 'Iveco Stralis 480', 'BUSY'),
(6, 'PQR7S89', 'Volvo FH 540', 'AVAILABLE'),
(7, 'STU0V12', 'DAF XF 460', 'INACTIVE');

SELECT setval('seq_vehicle', 50, true);