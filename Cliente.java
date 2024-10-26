package com.mycompany.proyectofinal;

import java.util.List;

public class Cliente {
    private String nitCliente;
    private String nombreCliente;
    private String direccion;
    private String celular;

    private List<Pedido> pedidosLista;

    public List<Pedido> getPedidosLista() {
        return pedidosLista;
    }

    public void setPedidosLista(List<Pedido> pedidosLista) {
        this.pedidosLista = pedidosLista;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}
