CREATE TABLE voluntarios (
                           id SERIAL primary key,
                           nombre VARCHAR(100) NOT NULL,
                           apellido VARCHAR(100) NOT NULL,
                           genero VARCHAR(10) NOT NULL,
                           fecha_nacimiento DATE,
                           telefono VARCHAR(15),
                           correo VARCHAR(100),
                           comentarios VARCHAR(150),
                           activo BOOLEAN NOT NULL
)