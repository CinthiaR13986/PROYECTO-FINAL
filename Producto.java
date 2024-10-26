package com.mycompany.proyectofinal;

public class Producto {
    private Integer productoId;
    private String nombre;
    private Integer stock;

    private String estado;

    public Producto() {
    }

    public Producto(Integer idProducto, String nombre, Integer stock, String estado) {
        this.productoId = idProducto;
        this.nombre = nombre;
        this.stock = stock;
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
