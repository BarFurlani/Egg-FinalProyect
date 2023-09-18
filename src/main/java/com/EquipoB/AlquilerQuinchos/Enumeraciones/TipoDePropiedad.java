package com.EquipoB.AlquilerQuinchos.Enumeraciones;

public enum TipoDePropiedad {
    TIPO_QUINCHO("Quincho"),
    TIPO_SALON("Salon de fiesta"),
    TIPO_QUINTA("Casa quinta");

    private final String mostrarTipo;

    TipoDePropiedad(String mostrarTipo) {
        this.mostrarTipo = mostrarTipo;
    }
}
