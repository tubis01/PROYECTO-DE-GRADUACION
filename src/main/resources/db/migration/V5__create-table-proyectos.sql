-- src/main/resources/db/migration/V1__create_table_proyectos.sql
CREATE TABLE proyectos (
                           id SERIAL PRIMARY KEY,
                           nombre VARCHAR(100) NOT NULL,
                           descripcion TEXT NOT NULL,
                           fecha_inicio DATE NOT NULL,
                           fecha_fin DATE,
                           activo BOOLEAN NOT NULL,
                           estado VARCHAR(50)
);