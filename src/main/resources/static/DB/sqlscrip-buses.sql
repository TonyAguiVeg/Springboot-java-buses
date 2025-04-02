CREATE database db_Buses;
USE db_Buses;

CREATE TABLE tb_marca (
    id_marca BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom_marca VARCHAR(255) NOT NULL,
    modelo VARCHAR(30),
    año INT
);

CREATE TABLE IF NOT EXISTS tb_bus (
    id_bus BIGINT PRIMARY KEY AUTO_INCREMENT,
    num_bus VARCHAR(6) UNIQUE,
    placa VARCHAR(7),
    caracteristicas VARCHAR(250),
    id_marca BIGINT,
    estado VARCHAR(50),
    fecha_creacion DATE,
    tipo VARCHAR(20),
    FOREIGN KEY (id_marca) REFERENCES tb_marca(id_marca)
);

INSERT INTO tb_marca (nom_marca, modelo, año) VALUES
('Volvo', 'B8R', 2022),
('wolfswagen', 'O500U', 2021),
('Scania', 'K320IB', 2023),
('Fiat', 'Ducato', 2020),
('kia', 'KLQ6125', 2021),
('Toyota', 'yaris', 2022),
('Nissan', 'Elantra', 2019);

INSERT INTO tb_bus (num_bus, placa, caracteristicas, id_marca, estado, fecha_creacion, tipo) VALUES('S-1', 'ABC123', 'Aire acondicionado, WiFi, Asientos reclinables', 1, 'Activo', '2023-01-15', 'Small');
INSERT INTO tb_bus (num_bus, placa, caracteristicas, id_marca, estado, fecha_creacion, tipo) VALUES('M-1', 'DEF456', 'Servicio premium, Pantallas individuales', 2, 'Activo', '2023-02-20', 'Medium');
INSERT INTO tb_bus (num_bus, placa, caracteristicas, id_marca, estado, fecha_creacion, tipo) VALUES('M-2', 'GHI789', 'Aire acondicionado, Baño', 1, 'Activo', '2023-03-10', 'Medium');
INSERT INTO tb_bus (num_bus, placa, caracteristicas, id_marca, estado, fecha_creacion, tipo) VALUES('L-1','zfcq123', 'Asientos reclinables,Aire acondicionado, WiFi,WiFi, Puertos USB', '2023-01-25', 'Large');
INSERT INTO tb_bus (num_bus, placa, caracteristicas, id_marca, estado, fecha_creacion, tipo) VALUES('X-1', 'MNO345', 'Bar a bordo, Asientos cama', 2, 'Activo', '2023-04-05', 'x_Large');
INSERT INTO tb_bus (num_bus, placa, caracteristicas, id_marca, estado, fecha_creacion, tipo) VALUES('S-2', 'PQR678', 'WiFi, Puertos USB', 3, 'Activo', '2023-02-15', 'Small');
INSERT INTO tb_bus (num_bus, placa, caracteristicas, id_marca, estado, fecha_creacion, tipo) VALUES('M-3', 'STU901', 'Aire acondicionado básico', 3, 'Inactivo', '2023-03-22', 'Medium');
INSERT INTO tb_bus (num_bus, placa, caracteristicas, id_marca, estado, fecha_creacion, tipo) VALUES('X-2', 'VWX234', 'Servicio de comida, Asientos VIP', 1, 'Activo', '2023-04-18', 'X_Large');
INSERT INTO tb_bus (num_bus, placa, caracteristicas, id_marca, estado, fecha_creacion, tipo) VALUES('S-3', 'YZA567', ' transporte básico', 6, 'Activo', '2023-05-30', 'Small');
INSERT INTO tb_bus (num_bus, placa, caracteristicas, id_marca, estado, fecha_creacion, tipo) VALUES('M-4', 'BCD890', 'Aire acondicionado, WiFi', 6, 'Inactivo', '2023-06-12', 'Medium');
INSERT INTO tb_bus (num_bus, placa, caracteristicas, id_marca, estado, fecha_creacion, tipo) VALUES('M-5', 'EFG123', 'Asientos reclinables 180°', 1, 'Activo', '2023-05-15', 'Medium');
INSERT INTO tb_bus (num_bus, placa, caracteristicas, id_marca, estado, fecha_creacion, tipo) VALUES('S-4', 'HIJ456', 'transporte básico', 1, 'Inactivo', '2023-07-01', 'Small');
INSERT INTO tb_bus (num_bus, placa, caracteristicas, id_marca, estado, fecha_creacion, tipo) VALUES('L-2', 'KLM789', 'Aire acondicionado, Espacio para equipaje', 5, 'Activo', '2023-06-20', 'Large');
INSERT INTO tb_bus (num_bus, placa, caracteristicas, id_marca, estado, fecha_creacion, tipo) VALUES('L-3', 'NOP012', 'Entretenimiento a bordo', 5, 'Activo', '2023-08-05', 'Large');
INSERT INTO tb_bus (num_bus, placa, caracteristicas, id_marca, estado, fecha_creacion, tipo) VALUES('L-4', 'QRS345', 'Aire acondicionado, WiFi', 7, 'Activo', '2023-07-15', 'Large');

DELIMITER $$

CREATE TRIGGER generar_num_bus
BEFORE INSERT ON tb_bus
FOR EACH ROW
BEGIN
    DECLARE conteoActual INT;
    
    -- Contar buses existentes del mismo tipo
    SELECT COUNT(*) INTO conteoActual
    FROM tb_bus
    WHERE tipo = NEW.tipo;
    
    -- Generar el número de bus según el tipo
    IF NEW.tipo = 'Small' THEN
        SET NEW.num_bus = CONCAT('S-', conteoActual + 1);
    ELSEIF NEW.tipo = 'Medium' THEN
        SET NEW.num_bus = CONCAT('M-', conteoActual + 1);
    ELSEIF NEW.tipo = 'Large' THEN
        SET NEW.num_bus = CONCAT('L-', conteoActual + 1);
    ELSEIF NEW.tipo = 'X_Large' THEN
        SET NEW.num_bus = CONCAT('X-', conteoActual + 1);
    ELSE
        -- Para cualquier otro tipo no especificado
        SET NEW.num_bus = CONCAT(UPPER(SUBSTRING(NEW.tipo, 1, 1), '-', conteoActual + 1);
    END IF;
END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER evitar_actualizacion_num_bus
BEFORE UPDATE ON tb_bus
FOR EACH ROW
BEGIN
    -- Impedir la actualización del número de bus si ya existe
    IF OLD.num_bus IS NOT NULL THEN
        SET NEW.num_bus = OLD.num_bus;
    END IF;
END $$

DELIMITER ;