

CREATE TABLE consumer (
    consumer_id   INT PRIMARY KEY,
    consumer_type VARCHAR(20) NOT NULL CHECK (consumer_type IN ('Residential', 'Commercial')),
    name          VARCHAR(60) NOT NULL,
    address       VARCHAR(120) NOT NULL,
    phone         VARCHAR(10) NOT NULL
);

CREATE TABLE meter_reading (
    reading_id       INT PRIMARY KEY AUTO_INCREMENT,
    consumer_id      INT NOT NULL,
    previous_reading DOUBLE NOT NULL,
    current_reading  DOUBLE NOT NULL,
    reading_date     DATE NOT NULL,
    FOREIGN KEY (consumer_id) REFERENCES consumer(consumer_id)
);

CREATE TABLE bill (
    bill_id        INT PRIMARY KEY AUTO_INCREMENT,
    consumer_id    INT NOT NULL,
    units_consumed DOUBLE NOT NULL,
    amount         DECIMAL(10, 2) NOT NULL,
    due_date       DATE NOT NULL,
    paid           BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (consumer_id) REFERENCES consumer(consumer_id)
);
