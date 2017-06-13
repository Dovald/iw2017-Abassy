CREATE TABLE local(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	direccion text,
	ciudad text
    );

CREATE TABLE cierre_caja(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	importe float,
        importe_total float,
	fecha timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
	);

CREATE TABLE zona(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombre text,
	id_local int,
	FOREIGN KEY (id_local) REFERENCES local(id)
    );

-- numero hace referencia al numero de la mesa visible por el camarero
CREATE TABLE mesa(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	numero int,
	id_zona int,
	FOREIGN KEY (id_zona) REFERENCES zona(id)
    );

-- tipo: gerente (1), encargado (2), camarero (3)
CREATE TABLE usuario(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	tipo int NOT NULL,
	nombre text,
	apellidos text,
	password text,
	id_local int NOT NULL,
	FOREIGN KEY (id_local) REFERENCES local(id)
    );

CREATE TABLE cliente(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombre text,
	direccion text,
	telefono text
    );

CREATE TABLE familia_producto(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombre text
    ); 

-- prod_tipo sera Simple (0) o Compuesto (1)
CREATE TABLE producto(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombre text,
	precio float,
	tipo  BIT   NULL   DEFAULT 0,
	id_familia int,
	imagen LONGBLOB, 
	FOREIGN KEY (id_familia) REFERENCES familia_producto(id)
    );

-- Usuario siempre sera un camarero
CREATE TABLE pedido(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_cliente int NOT NULL,
	id_mesa int,
	id_usuario int NOT NULL,
	id_local int NOT NULL,
	importe float NOT NULL,
	fecha timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	FOREIGN KEY (id_local) REFERENCES local(id),
	FOREIGN KEY (id_mesa) REFERENCES mesa(id),
	FOREIGN KEY (id_usuario) REFERENCES usuario(id),
	FOREIGN KEY (id_cliente) REFERENCES cliente(id)
    );

CREATE TABLE linea_pedido(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	cantidad int,
	id_pedido int,
	id_producto int,
	FOREIGN KEY (id_producto) REFERENCES producto(id),
	FOREIGN KEY (id_pedido) REFERENCES pedido(id)
    );

-- relacion varios a varios Producto - Compuesto donde un compuesto estara formado por varios productos simples
CREATE TABLE producto_compuesto(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_producto int,
	id_compuesto int,
	FOREIGN KEY (id_producto) REFERENCES producto(id),
	FOREIGN KEY (id_compuesto) REFERENCES producto(id)
	);




