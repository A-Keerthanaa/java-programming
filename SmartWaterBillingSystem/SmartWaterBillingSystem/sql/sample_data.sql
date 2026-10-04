INSERT INTO consumer (consumer_id, consumer_type, name, address, phone) VALUES
(1001, 'Residential', 'Ravi Kumar', '12 MG Road, Chennai', '9876543210'),
(1002, 'Commercial', 'Priya Singh', '45 Anna Nagar, Chennai', '8765432109');

INSERT INTO meter_reading (consumer_id, previous_reading, current_reading, reading_date) VALUES
(1001, 0, 120, CURDATE());

INSERT INTO bill (consumer_id, units_consumed, amount, due_date, paid) VALUES
(1001, 120, 555.00, DATE_ADD(CURDATE(), INTERVAL 15 DAY), FALSE);
