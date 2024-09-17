CREATE TABLE usuarios (
                         id SERIAL PRIMARY KEY,
                         email VARCHAR(255) NOT NULL UNIQUE,
                         usuario VARCHAR(255) NOT NULL UNIQUE,
                         clave VARCHAR(255) NOT NULL,
                         activo BOOLEAN DEFAULT TRUE
);