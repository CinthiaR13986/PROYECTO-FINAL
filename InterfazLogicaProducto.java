package com.mycompany.proyectofinal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JTable;

public interface InterfazLogicaProducto {
    void cargarProductos(JTable jtable) throws IOException, SQLException;
    void guardarProductos() throws IOException;
    void agregarProducto(Producto producto) throws SQLException, IOException;
    Producto despacharProducto(int idProducto, int cantidadRequerida) throws SQLException, IOException;
    List<Producto> getProductos();
    Producto getProducto(int idProducto) throws SQLException, IOException;
}
