/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectofinal;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JFrame;

/**
 *
 * @author Cinthia
 */
public class Main extends JFrame {
    public static void main(String[] args) throws IOException, SQLException {
        ClienteGUI cliente = new ClienteGUI(1);
        cliente.setVisible(true);
        cliente.loadData();
    }
}
