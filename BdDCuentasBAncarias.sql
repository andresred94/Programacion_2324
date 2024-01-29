DROP DATABASE IF EXISTS CuentasBancarias;
CREATE DATABASE CuentasBancarias;
USE CuentasBancarias;
DROP TABLE IF EXISTS cuentas;
DROP TABLE IF EXISTS gestores;
DROP TABLE IF EXISTS personas;
CREATE TABLE gestores (
	id INT UNSIGNED AUTO_INCREMENT,
    cod_Gestor VARCHAR (20),
	id_cuenta INT UNSIGNED DEFAULT NULL,
    nombre VARCHAR (29) DEFAULT NULL,
    telefono VARCHAR(9) DEFAULT NULL,
    CONSTRAINT gestores_PK PRIMARY KEY (id)
);
CREATE TABLE cuentas(
	numCuenta INT UNSIGNED AUTO_INCREMENT,
    id_persona INT UNSIGNED ,
	cod_Gestor VARCHAR (20),
    saldo DECIMAL (8,2) DEFAULT 0,
    entidadBancaria VARCHAR (20) DEFAULT NULL,
    id_gestor INT UNSIGNED,
    CONSTRAINT cuentas_PK PRIMARY KEY (numCuenta,id_persona),
    CONSTRAINT cuentas_FK1 FOREIGN KEY (id_gestor) REFERENCES gestores (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);
CREATE TABLE personas(
	id_persona INT UNSIGNED AUTO_INCREMENT,
    DNI VARCHAR(9),
    id_cuenta INT UNSIGNED DEFAULT NULL,
    id_gestor VARCHAR (20) DEFAULT NULL,
    nombre VARCHAR(20),
    ape1 VARCHAR(20),
    ape2 VARCHAR(20),
    telefono VARCHAR(9),
    CONSTRAINT personas_PK PRIMARY KEY (id_persona,DNI),
    CONSTRAINT personas_fk FOREIGN KEY(id_cuenta,id_persona) REFERENCES cuentas(numCuenta,id_persona)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

-- Insertar datos en la tabla 'cuentas'
INSERT INTO cuentas (numCuenta, id_persona, cod_Gestor, saldo, entidadBancaria)
VALUES (NULL, 1001, 'GESTOR001', 1000.00, 'Banco A');

-- Insertar datos en la tabla 'personas'
INSERT INTO personas (id_persona, DNI, id_cuenta, nombre, ape1, ape2, telefono)
VALUES (2001, '12345d789', 1, 'Juan', 'Pérez', 'Gómez', '123456789');


INSERT INTO personas(id_persona,DNI,id_cuenta,nombre,ape1,ape2,telefono) VALUES (NULL,'a1',1,'a3','a4','a5','a6');


-- Insertar Datos para dar de alta 
INSERT INTO personas(id_persona,DNI,id_cuenta,nombre,ape1,ape2,telefono) VALUES (NULL, ? ,NULL, ?, ?, ?, ?);
-- INSERT INTO personas(id_persona,DNI,id_cuenta,nombre,ape1,ape2,telefono) VALUES (NULL, 'a' ,NULL, 'b', 'c','q','q'); -- prueba de inserccion
INSERT INTO cuentas(id_persona,numCuenta,id_gestor,saldo,entidadBancaria) VALUES ( ?,NULL,NULL,NULL,NULL);
-- INSERT INTO cuentas(id_persona,numCuenta,id_gestor,saldo,entidadBancaria) VALUES (NULL,NULL, 'DNI',NULL,NULL);

-- Obtener el numero de persona
SELECT id_persona FROM personas WHERE DNI = ?;
-- Obtener el numero de cuenta
SELECT numCuenta FROM cuentas WHERE id_persona = ?;
INSERT INTO cuentas(id_persona,numCuenta,id_gestor,saldo,entidadBancaria) VALUES (NULL,NULL, ?,NULL,NULL);
-- actualizar el numero de cuenta
UPDATE personas SET id_cuenta = ? WHERE DNI = ?;

SELECT * FROM personas;
SELECT * FROM cuentas;
SELECT * FROM gestores;
use CuentasBancarias;
