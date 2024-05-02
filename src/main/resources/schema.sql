CREATE TABLE person (
    id          BIGINT NOT NULL AUTO_INCREMENT,
    firstName   VARCHAR(255) NOT NULL,
    lastName    VARCHAR(255) NOT NULL,
    address     VARCHAR(255),
    city        VARCHAR(255),
    zip         VARCHAR(10),
    phone       VARCHAR(15),
    email       VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE (firstName, lastName)
);

CREATE TABLE firestation (
    id          BIGINT NOT NULL AUTO_INCREMENT,
    address     VARCHAR(255),
    station     VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE medicalrecord (
    id          BIGINT NOT NULL AUTO_INCREMENT,
    firstName   VARCHAR(255),
    lastName    VARCHAR(255),
    birthdate   DATE,
    medications TEXT,
    allergies   TEXT,
    PRIMARY KEY (id),
    FOREIGN KEY (firstName, lastName) REFERENCES person(firstName, lastName)
);

