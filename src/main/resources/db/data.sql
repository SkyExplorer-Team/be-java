INSERT INTO public.airport (
    id, abv, lat, lng, name, national_id, city
)
VALUES
    (gen_random_uuid(), 'ATL', 33.6407, -84.4277, 'Hartsfield-Jackson Atlanta International Airport', 'aeb3673d-44fb-4f57-9184-4d7f79d3d55a', 'Atlanta'),
    (gen_random_uuid(), 'PEK', 40.0799, 116.6031, 'Beijing Capital International Airport', 'f01dabda-af85-487d-8d3d-03b4357c0e6d', 'Beijing'),
    (gen_random_uuid(), 'LHR', 51.4696, -0.4474, 'London Heathrow Airport', 'f4e1e3a0-896b-4750-9867-69a0c3203aef', 'London'),
    (gen_random_uuid(), 'DXB', 25.2532, 55.3657, 'Dubai International Airport', 'e9e29bd0-4f52-4da8-bd85-8e62e3b8ef37', 'Dubai'),
    (gen_random_uuid(), 'CGK', -6.1256, 106.6558, 'Soekarno-Hatta International Airport', 'b2f0da72-2553-4871-8cf3-52af5b7160e1', 'Jakarta'),
    (gen_random_uuid(), 'LAX', 33.9416, -118.4085, 'Los Angeles International Airport', 'aeb3673d-44fb-4f57-9184-4d7f79d3d55a', 'Los Angeles'),
    (gen_random_uuid(), 'HND', 35.5494, 139.7798, 'Tokyo Haneda Airport', '8e4b9aa0-aa35-4b6d-b9d1-e6ed2d62e2d3', 'Tokyo'),
    (gen_random_uuid(), 'ORD', 41.9742, -87.9073, 'O''Hare International Airport ', 'aeb3673d-44fb-4f57-9184-4d7f79d3d55a', 'Chicago'),
    (gen_random_uuid(), 'DPS', -8.7481, 115.1675, 'Ngurah Rai International Airport', 'b2f0da72-2553-4871-8cf3-52af5b7160e1', 'Denpasar'),
    (gen_random_uuid(), 'HKG', 22.3080, 113.9185, 'Hong Kong International Airport', 'f01dabda-af85-487d-8d3d-03b4357c0e6d', 'Hong Kong'),
    (gen_random_uuid(), 'CDG', 49.0097, 2.5479, 'Paris Charles de Gaulle Airport', 'f441a3b3-5830-46d2-ba8d-81a98ba4eafc', 'Paris'),
    (gen_random_uuid(), 'DEN', 39.8561, -104.6737, 'Denver International Airport', 'aeb3673d-44fb-4f57-9184-4d7f79d3d55a', 'Denver'),
    (gen_random_uuid(), 'SIN', 1.3644, 103.9915, 'Singapore Changi Airport', 'e1f4c0cf-271b-4160-8e76-b5e8f33eb4e1', 'Singapore'),
    (gen_random_uuid(), 'SYD', -33.9461, 151.1772, 'Sydney Kingsford Smith Airport', '3fe47e68-76e3-4c9d-b2ed-31577356e534', 'Sydney'),
    (gen_random_uuid(), 'FRA', 50.0336, 8.5706, 'Frankfurt Airport', '7d58e7b5-c7e1-4f98-b93c-7359eb738a9f', 'Frankfurt'),
    (gen_random_uuid(), 'DFW', 32.8998, -97.0403, 'Dallas/Fort Worth International Airport', 'aeb3673d-44fb-4f57-9184-4d7f79d3d55a', 'Dallas'),
    (gen_random_uuid(), 'ICN', 37.4692, 126.4505, 'Incheon International Airport ', '92c1bbdb-903f-4d01-977f-b0bf8c1c345c', 'Seoul'),
    (gen_random_uuid(), 'AMS', 52.3086, 4.7639, 'Amsterdam Airport Schiphol', 'a66f78c4-1566-4c5e-8c3d-803df04ff586', 'Amsterdam'),
    (gen_random_uuid(), 'SOC', -7.5163, 110.7588, 'Adisumarmo International Airport', 'b2f0da72-2553-4871-8cf3-52af5b7160e1', 'Solo'),
    (gen_random_uuid(), 'SFO', 37.6213, -122.3790, 'San Francisco International Airport', 'aeb3673d-44fb-4f57-9184-4d7f79d3d55a', 'San Francisco');

INSERT INTO public.airplane (id, code, name, speed)
VALUES
  (gen_random_uuid(), 'ABC123', 'Boeing 747', 600.5),
  (gen_random_uuid(), 'DEF456', 'Airbus A380', 700.8),
  (gen_random_uuid(), 'GHI789', 'Cessna 172', 150.2),
  (gen_random_uuid(), 'JKL012', 'Bombardier Challenger 300', 550.3),
  (gen_random_uuid(), 'MNO345', 'Embraer E190', 500.7),
  (gen_random_uuid(), 'PQR678', 'Lockheed C-130 Hercules', 350.9),
  (gen_random_uuid(), 'STU901', 'Boeing 737', 550.6),
  (gen_random_uuid(), 'VWX234', 'Airbus A320', 600.1),
  (gen_random_uuid(), 'YZA567', 'Dassault Falcon 7X', 600.4),
  (gen_random_uuid(), 'BCD890', 'Gulfstream G650', 700.0);

