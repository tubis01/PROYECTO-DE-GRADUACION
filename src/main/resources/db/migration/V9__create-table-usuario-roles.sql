CREATE TABLE usuario_roles (
                               id_usuario INT NOT NULL,
                               id_rol INT NOT NULL,
                               FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE,
                               FOREIGN KEY (id_rol) REFERENCES roles(id) ON DELETE CASCADE,
                               PRIMARY KEY (id_usuario, id_rol)
);