package com.mycompany.proyectofinal;

public class FabricaCliente {
    public static InterfazLogicaCliente getFabricaCliente(Integer tipoBaseDatos) {
        return switch (tipoBaseDatos) {
            case 1 -> new LogicaJSONCliente();
            case 2 -> new LogicaSQLCliente();
            default -> null;
        };
    }
}
