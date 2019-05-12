INSERT INTO roles (role_name, enabled, deleted)
VALUES
    ('ADMIN', TRUE , FALSE),
    ('DRIVER', TRUE , FALSE),
    ('CLIENT', TRUE , FALSE);


INSERT INTO users (firstname,lastname, email, password, role_id, enabled, deleted)
VALUES
    ('Driver','Sola', 'driver@email.com','167ef05b7a218b77f657ecc36b869b64dae7ea189535e1d40518feb60401031fb03e9d22e890f1d3' , 2, TRUE , FALSE),
    ('Admin','Digitrak', 'admin@email.com','167ef05b7a218b77f657ecc36b869b64dae7ea189535e1d40518feb60401031fb03e9d22e890f1d3' , 1, TRUE , FALSE),
    ('Client','Total', 'client@email.com','167ef05b7a218b77f657ecc36b869b64dae7ea189535e1d40518feb60401031fb03e9d22e890f1d3' , 3, TRUE , FALSE);



INSERT INTO orders (pickup, destination, weight, content, pickup_date, deleted, client_id,driver_id)
VALUES
    ('Jabi', 'Apo', '15-30 tons', 'perishable', TIMESTAMP '2019-05-11 12:00:00', false, 3,1);