CREATE TABLE beneficiarios(
    id SERIAL PRIMARY KEY ,
    id_persona INT NOT NULL,
    id_proyecto INT NOT NULL,
    activo BOOLEAN NOT NULL,
    CONSTRAINT fk_beneficiarios_personas FOREIGN KEY (id_persona) REFERENCES personas(id_persona),
    CONSTRAINT fk_beneficiarios_proyectos FOREIGN KEY (id_proyecto) REFERENCES proyectos(id)

)