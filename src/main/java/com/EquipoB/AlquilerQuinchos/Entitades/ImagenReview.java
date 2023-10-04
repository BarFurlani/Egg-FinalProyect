package com.EquipoB.AlquilerQuinchos.Entitades;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
public class ImagenReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String contentType;

    @Lob
    private byte[] contenido;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public ImagenReview() {
    }
}

