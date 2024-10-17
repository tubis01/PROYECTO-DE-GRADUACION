CREATE TABLE organizaciones (
                       id  SERIAL PRIMARY KEY,
                       nombre VARCHAR(255) NOT NULL UNIQUE,
                        activo BOOLEAN DEFAULT TRUE
);