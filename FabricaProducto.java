package com.mycompany.proyectofinal;

public class FabricaProducto {
    public static InterfazLogicaProducto getFabricaProducto(Integer tipoBaseDatos) {
        return switch (tipoBaseDatos) {
            case 1 -> new LogicaJSONProducto();
            case 2 -> new LogicaSQLProducto();
            default -> null;
        };
    }
}
