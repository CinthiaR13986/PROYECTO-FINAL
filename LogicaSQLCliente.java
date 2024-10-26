package com.mycompany.proyectofinal;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class LogicaSQLCliente implements InterfazLogicaCliente {
    private static List<Cliente> clientes = new ArrayList<>();
    @Override
    public void cargarClientes(JTable jtable) throws IOException, SQLException {
        Connection con = MysqlConfiguracion.getConnection();
        PreparedStatement statement = con.prepareStatement("SELECT * FROM cliente");
        ResultSet result = statement.executeQuery();

        clientes = new ArrayList<>();
        // Get metadata to determine column names
        ResultSetMetaData metaData = result.getMetaData();
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        while (result.next()) {
            Cliente cliente = new Cliente();
            cliente.setNombreCliente(result.getString("nombreCliente"));
            cliente.setDireccion(result.getString("direccion"));
            cliente.setNitCliente(result.getString("nitCliente"));
            cliente.setCelular(result.getString("celular"));
            Object[] rowData = {cliente.getNitCliente(), cliente.getNombreCliente(), cliente.getDireccion(), cliente.getCelular()};
            model.addRow(rowData);

            PreparedStatement statementPedido = con.prepareStatement("SELECT pedido.* FROM pedido INNER JOIN cliente ON cliente.nitCliente = pedido.nitCliente where pedido.nitCliente = ?");
            statementPedido.setString(1, cliente.getNitCliente());
            ResultSet resultPedido = statementPedido.executeQuery();
            ArrayList<Pedido> pedidos = new ArrayList<>();
            while (resultPedido.next()) {
                Pedido pedido = new Pedido();
                pedido.setPedidoId(resultPedido.getInt("pedidoId"));
                pedido.modificarEstado(resultPedido.getString("estado"));
                pedido.setFechaOrden(resultPedido.getDate("fechaOrden"));

                PreparedStatement stmtProducto = con.prepareStatement("SELECT producto.* FROM producto INNER JOIN pedido_productos ON pedido_productos.productoId = producto.productoId where pedido_productos.pedidoId = ?");
                stmtProducto.setInt(1, pedido.getPedidoId());
                ResultSet resultProducto = stmtProducto.executeQuery();

                ArrayList<Producto> productos = new ArrayList<>();
                while (resultProducto.next()) {
                    Producto producto = new Producto();
                    producto.setNombre(resultProducto.getString("nombre"));
                    producto.setProductoId(resultProducto.getInt("productoId"));
                    producto.setEstado(resultProducto.getString("estado"));
                    productos.add(producto);
                }
                pedido.setProductos(productos);

                pedidos.add(pedido);
            }

            cliente.setPedidosLista(pedidos);
            clientes.add(cliente);
        }
        jtable.setModel(model);
    }

    @Override
    public void guardarClientes() throws IOException {
        System.out.println("Guardando clientes en MySQL");
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    @Override
    public void agregarCliente(Cliente cliente, Integer productoId) throws SQLException, IOException {
        Connection con = MysqlConfiguracion.getConnection();
        PreparedStatement statement;

        statement = con.prepareStatement("SELECT * FROM cliente where nitCliente = ?");
        statement.setString(1, cliente.getNitCliente());
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            statement = con.prepareStatement("UPDATE cliente SET nombreCliente = ?, direccion = ?, celular = ? where nitCliente = ?");
            statement.setString(1, cliente.getNombreCliente());
            statement.setString(2, cliente.getDireccion());
            statement.setString(3, cliente.getCelular());
            statement.setString(4, cliente.getNitCliente());
            statement.executeUpdate();
            this.createPedido(cliente, productoId);
            return;
        }

        statement = con.prepareStatement("INSERT INTO cliente (nitCliente, nombreCliente, direccion, celular) VALUES (?, ?, ?, ?)");

        statement.setString(1, cliente.getNitCliente());
        statement.setString(2, cliente.getNombreCliente());
        statement.setString(3, cliente.getDireccion());
        statement.setString(4, cliente.getCelular());
        statement.executeUpdate();
        this.createPedido(cliente, productoId);
    }

    private void createPedido(Cliente cliente, Integer productoId) throws SQLException, IOException {
        Connection con = MysqlConfiguracion.getConnection();
        PreparedStatement statement = con.prepareStatement("INSERT INTO pedido (nitCliente, fechaOrden, estado) VALUES (?, ?, 'GENERADO')", PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, cliente.getNitCliente());
        statement.setDate(2, new Date(new java.util.Date().getTime()));
        statement.executeUpdate();

        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Error creando pedido.");
        }

        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            var pedidoId = generatedKeys.getInt(1);
            statement = con.prepareStatement("INSERT INTO pedido_productos (pedidoId, productoId) VALUES (?, ?)");
            statement.setInt(1, pedidoId);
            statement.setInt(2, productoId);
            statement.executeUpdate();
        }
    }
}
