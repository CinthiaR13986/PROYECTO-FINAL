package com.mycompany.proyectofinal;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class LogicaJSONProducto implements InterfazLogicaProducto {
    private static List<Producto> productos = new ArrayList<>();
    private static Map<Integer, Integer> map = new HashMap<>();

    public void cargarProductos(JTable jtable) throws IOException {
        System.out.println("Cargando productos...");
        URL url = new URL("file:src/main/resources/producto.json");
        productos = new ArrayList<>();
        map = new HashMap<>();
        
        String[] columnNames = new String[]{"productoId", "nombre", "Inventario", "estado"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        ObjectMapper mapper = new ObjectMapper();
        Producto[] produtosTemp = mapper.readValue(new File(url.getPath()), Producto[].class);

        Integer index = 0;
        for (Producto producto : produtosTemp) {
            if (map.get(producto.getProductoId()) != null) {
                System.out.println("Producto con ID duplicado id: " + producto.getProductoId()+ " nombre: " + producto.getNombre() + " se procede a ignorarlo.");
                continue;
            }
            map.put(producto.getProductoId(), index++);
            productos.add(producto);
            
        Object[] rowData = {producto.getProductoId(), producto.getNombre(), producto.getStock(), producto.getEstado()};
        model.addRow(rowData);
        }
        jtable.setModel(model);

        System.out.println("Productos cargados: " + productos.size() + " \n");
    }

    public void guardarProductos() throws IOException {
        System.out.println("Guardando productos...");
        URL url = new URL("file:src/main/resources/producto.json");

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(url.getPath()), productos);
        System.out.println("Productos guardados: " + productos.size());
    }

    public void agregarProducto(Producto producto) {
        System.out.println("Agregando producto id: " + producto.getProductoId() + " nombre: " + producto.getNombre());
        if (map.get(producto.getProductoId()) != null) {
            throw new IllegalArgumentException("Producto con ID duplicado id: " + producto.getProductoId() + " nombre: " + producto.getNombre());
        }
        productos.add(producto);
    }

    public Producto despacharProducto(int idProducto, int cantidadRequerida)  {
        Producto producto = getProducto(idProducto);
        Integer cantidad = producto.getStock();

        if (cantidad < cantidadRequerida) {
            throw new IllegalArgumentException("No hay suficiente cantidad de producto id: " + idProducto + " nombre: " + producto.getNombre());
        }

        producto.setStock(cantidad - cantidadRequerida);

        if (producto.getStock()== 0) {
            producto.setEstado("SIN EXISTENCIAS");
        }
        return new Producto(producto.getProductoId(), producto.getNombre(), cantidadRequerida, producto.getEstado());
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public Producto getProducto(int idProducto) {
        Integer productoId = map.get(idProducto);
        if (productoId == null) {
            throw new IllegalArgumentException("Producto no encontrado id: " + idProducto);
        }
        return productos.get(productoId);
    }
}
