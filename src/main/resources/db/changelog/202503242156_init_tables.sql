CREATE SCHEMA filmplus;

CREATE TABLE filmplus.user
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR        NOT NULL,
    email    VARCHAR(20) UNIQUE NOT NULL,
    login    VARCHAR(10) UNIQUE NOT NULL,
    birthday DATE
);

CREATE TABLE filmplus.film
(
    id           SERIAL PRIMARY KEY,
    title        VARCHAR(40) NOT NULL,
    description  TEXT,
    release_date DATE,
    duration     NUMERIC(3),
    genre        VARCHAR(15)
);

CREATE TABLE filmplus.friend
(
    user_id   INT REFERENCES filmplus.user (id),
    friend_id INT REFERENCES filmplus.user (id),
    PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE filmplus.like
(
    user_id  INT REFERENCES filmplus.user (id),
    film_id INT REFERENCES filmplus.film (id),
    PRIMARY KEY (user_id, film_id)
);

CREATE TABLE filmplus.review
(
    id          SERIAL PRIMARY KEY,
    user_id     INT REFERENCES filmplus.user (id),
    film_id     INT REFERENCES filmplus.film (id),
    review_text TEXT,
    date_create TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);