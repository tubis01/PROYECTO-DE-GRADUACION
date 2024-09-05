CREATE TABLE personas (
  id_persona SERIAL PRIMARY KEY,
  dpi VARCHAR(15) UNIQUE NOT NULL ,
  primer_nombre VARCHAR(50),
  segundo_nombre VARCHAR(50),
  tercer_nombre VARCHAR(50),
  primer_apellido VARCHAR(50),
  segundo_apellido VARCHAR(50),
  nit VARCHAR(15) UNIQUE,
  telefono VARCHAR(15) UNIQUE,
  fecha_nacimiento DATE,
  etnia VARCHAR(50),
  genero VARCHAR(10),
  estado_civil VARCHAR(20),
  numero_hijos INT,
  tipo_vivienda VARCHAR(100),
  codigo_ubicacion VARCHAR(100),  -- Aquí se almacena el enum como String
  comunidad VARCHAR(100),
  discapacidad_auditiva BOOLEAN,
  discapacidad_motora BOOLEAN,
  discapacidad_intelectual BOOLEAN,
  comunidad_linguistica VARCHAR(100),
  area VARCHAR(100),
  cultivo VARCHAR(100),
  vende_excedente_cosecha BOOLEAN,
  tipo_productor VARCHAR(100),
  id_responsable INT,
  organizacion VARCHAR(50),
  activo BOOLEAN NOT NULL     ,
  CONSTRAINT fk_responsable FOREIGN KEY (id_responsable) REFERENCES responsables (id) ON DELETE SET NULL  -- Relación con 'responsables'

);
