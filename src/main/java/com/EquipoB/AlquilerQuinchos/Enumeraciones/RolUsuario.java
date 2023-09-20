package com.EquipoB.AlquilerQuinchos.Enumeraciones;

import lombok.Getter;

@Getter
public enum RolUsuario {
    ROL_ADMINISTRADOR("Administrador"),

    ROL_PROPIETARIO("Propietario"),
    ROL_INQUILINO("Inquilino");

    private final String mostrarNombre;

    RolUsuario(String mostrarNombre) {
        this.mostrarNombre = mostrarNombre;
    }

}
