CREATE DATABASE CuentasBancarias;
USE CuentasBancarias;
DROP TABLE IF EXISTS cuentas;
CREATE TABLE cuentas(
	-- numCuenta INT AUTOINCREMENT PRIMARY KEY,
	DNI VARCHAR(9) PRIMARY KEY DEFAULT '',
    titular VARCHAR(30),
    saldo DECIMAL DEFAULT 0
);
SELECT * FROM cuentas;