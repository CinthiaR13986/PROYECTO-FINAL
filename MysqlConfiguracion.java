package com.mycompany.proyectofinal;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class MysqlConfiguracion {
    private static Connection connection;
    private MysqlConfiguracion() {}
    public static Connection getConnection() throws SQLException, IOException {
        Map<String, String> credentials = getProperties();
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(credentials.get("url"), credentials.get("username"), credentials.get("password"));
        }
        return connection;
    }
    public static Map<String, String> getProperties() throws IOException {
        ObjectMapper map = new ObjectMapper();
        URL resource = new URL("file:src/main/resources/mysql-config.json");
        return map.readValue(new File(resource.getPath()), Map.class);
    }
}
