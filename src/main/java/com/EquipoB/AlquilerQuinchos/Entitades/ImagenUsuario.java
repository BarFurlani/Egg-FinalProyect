package com.EquipoB.AlquilerQuinchos.Entitades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
public class ImagenUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String contentType;

    @Lob
    private byte[] contenido;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public ImagenUsuario() {
    }
}

