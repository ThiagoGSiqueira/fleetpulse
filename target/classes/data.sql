

-- ==========================================
-- VEHICLES
-- ==========================================
INSERT INTO vehicles (id, license_plate, model, km_since_last_maintenance, status) VALUES 
(1, 'ABC1D23', 'Scania R 450', 4950.00, 'AVAILABLE'),
(2, 'DEF4G56', 'Mercedes-Benz Actros 2651', 100.00, 'AVAILABLE'),
(3, 'GHI7890', 'Ford Cargo 2429', 5200.00, 'MAINTENANCE'),
(4, 'JKL1M23', 'Volkswagen Constellation 24.280', 50.20, 'AVAILABLE'),
(5, 'MNO4P56', 'Iveco Stralis 480', 4500.00, 'BUSY'),
(6, 'PQR7S89', 'Volvo FH 540', 300.00, 'AVAILABLE'),
(7, 'STU0V12', 'DAF XF 460', 6000.00, 'INACTIVE');

SELECT setval('seq_vehicle', 50, true);