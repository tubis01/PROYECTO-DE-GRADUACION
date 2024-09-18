ALTER TABLE beneficiarios ADD CONSTRAINT unique_persona_proyecto UNIQUE (id_persona, id_proyecto);
