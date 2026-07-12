INSERT INTO vehicle_type(type_uuid, name, price)
VALUES ('0a7a8aa3-5b54-40fd-8f72-95f71a6356cb', 'SEDAN', 100);

INSERT INTO vehicle_type(type_uuid, name, price)
VALUES ('1a7a8aa3-5b54-40fd-8f72-95f71a6356cb', 'SUV', 200);

INSERT INTO vehicle_type(type_uuid, name, price)
VALUES ('2a7a8aa3-5b54-40fd-8f72-95f71a6356cb', 'VAN', 300);

INSERT INTO vehicle(vehicle_type_id, vehicle_id, status, purchase_date)
VALUES (1, 'SEDAN-1', 'AVAILABLE', '2026-01-01');

INSERT INTO vehicle(vehicle_type_id, vehicle_id, status, purchase_date)
VALUES (1, 'SEDAN-2', 'DECOMMISSIONED', '2026-02-01');

INSERT INTO vehicle(vehicle_type_id, vehicle_id, status, purchase_date)
VALUES (1, 'SEDAN-3', 'AVAILABLE', '2026-02-01');

INSERT INTO vehicle(vehicle_type_id, vehicle_id, status, purchase_date)
VALUES (2, 'SUV-1', 'AVAILABLE', '2026-03-01');

INSERT INTO vehicle(vehicle_type_id, vehicle_id, status, purchase_date)
VALUES (2, 'SUV-2', 'DECOMMISSIONED', '2026-03-01');

INSERT INTO vehicle(vehicle_type_id, vehicle_id, status, purchase_date)
VALUES (3, 'VAN-1', 'AVAILABLE', '2026-04-01');
