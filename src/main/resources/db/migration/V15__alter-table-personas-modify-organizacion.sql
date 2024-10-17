
ALTER TABLE personas DROP COLUMN organizacion;

ALTER TABLE personas ADD COLUMN id_organizacion BIGINT;

ALTER TABLE personas
    ADD CONSTRAINT fk_persona_organizacion
        FOREIGN KEY (id_organizacion)
            REFERENCES organizaciones(id);
