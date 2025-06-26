
CREATE TABLE IF NOT EXISTS ventas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    idventa VARCHAR(255),
    venta_id BIGINT,
    producto_id BIGINT
);


INSERT INTO ventas (name, idventa, venta_id, producto_id) 
VALUES ('Esteban', 'IDV-0001', 1001, 2001);

INSERT INTO ventas (name, idventa, venta_id, producto_id) 
VALUES ('María', 'IDV-0002', 1002, 2002);

INSERT INTO ventas (name, idventa, venta_id, producto_id) 
VALUES ('Carlos', 'IDV-0003', 1003, 2003);
