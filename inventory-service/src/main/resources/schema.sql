DROP TABLE IF EXISTS vehicle_type;
DROP TABLE IF EXISTS vehicle;

CREATE TABLE vehicle_type
(
    id        bigint PRIMARY KEY auto_increment,
    type_uuid char(36),
    name      varchar(50)    NOT NULL UNIQUE,
    price     decimal(10, 2) NOT NULL
);

CREATE TABLE vehicle
(
    id              bigint PRIMARY KEY auto_increment,
    vehicle_type_id bigint,
    vehicle_id      varchar(40) NOT NULL UNIQUE,
    status          varchar(30) NOT NULL,
    purchase_date   date        NOT NULL,
    FOREIGN KEY (vehicle_type_id) REFERENCES vehicle_type (id)
);
