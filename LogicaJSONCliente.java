package com.mycompany.proyectofinal;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class LogicaJSONCliente implements InterfazLogicaCliente {
    private static List<Cliente> clientes = new ArrayList<>();
    private static Map<String, String> map = new HashMap<>();

    public void cargarClientes(JTable jtable) throws IOException {
        URL url = new URL("file:src/main/resources/cliente.json");

        ObjectMapper mapper = new ObjectMapper();
        Cliente[] clientesTmp = mapper.readValue(new File(url.getPath()), Cliente[].class);
        clientes = new ArrayList<>();
        map = new HashMap<>();
        
        String[] columnNames = new String[]{"nitCliente", "nombreCliente", "direccion", "celular"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Cliente cliente : clientesTmp) {
            if (map.get(cliente.getNitCliente()) != null) {
                System.out.println("Cliente con ID duplicado nit: " + cliente.getNitCliente()+ " nombre: " + cliente.getNombreCliente()+ " se procede a ignorarlo.");
                continue;
            }
            map.put(cliente.getNitCliente(), cliente.getNombreCliente());
            clientes.add(cliente);
            
            Object[] rowData = {cliente.getNitCliente(), cliente.getNombreCliente(), cliente.getDireccion(), cliente.getCelular()};
            model.addRow(rowData);
        }
        jtable.setModel(model);

        System.out.println("clientes cargados: " + clientes.size());
    }

    public void guardarClientes() throws IOException {
        System.out.println("Actualizando clientes...");
        URL url = new URL("file:src/main/resources/cliente.json");

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(url.getPath()), clientes);
        System.out.println("clientes guardados: " + clientes.size());
    }

    public void agregarCliente(Cliente cliente, Integer pedidoId) {
        if (map.get(cliente.getNitCliente()) != null) {
            clientes.forEach(c -> {
                if (c.getNitCliente().equals(cliente.getNitCliente())) {
                    cliente.getPedidosLista().forEach(pedido -> c.getPedidosLista().add(pedido));
                }
            });
            return;
        }
        System.out.println("Agregando cliente id: " + cliente.getNitCliente()+ " nombre: " + cliente.getNombreCliente());
        clientes.add(cliente);
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}
