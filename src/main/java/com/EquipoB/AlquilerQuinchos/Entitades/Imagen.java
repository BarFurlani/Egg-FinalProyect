package com.EquipoB.AlquilerQuinchos.Entitades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString @AllArgsConstructor
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String contentType;

    @Lob
    private byte[] contenido;

    @OneToOne
    private Usuario usuario;

    @ManyToOne
    private Propiedad propiedad;

    public Imagen() {
    }
}

