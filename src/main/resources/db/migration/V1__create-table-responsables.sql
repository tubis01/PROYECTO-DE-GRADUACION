CREATE TABLE responsables (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    genero VARCHAR(10) NOT NULL,  -- Enum almacenado como String
    fecha_nacimiento DATE,
    telefono VARCHAR(15) UNIQUE,
    correo VARCHAR(100) UNIQUE,
    activo BOOLEAN NOT NULL
);
