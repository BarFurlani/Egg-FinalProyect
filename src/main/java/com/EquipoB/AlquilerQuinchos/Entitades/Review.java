package com.EquipoB.AlquilerQuinchos.Entitades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter @ToString @AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer puntuacion;
    private String comentario;
    private LocalDate fecha;

    @ManyToOne
    private Propiedad propiedad;

    @ManyToOne
    @JoinColumn(name = "inquilino")
    private Usuario inquilino;

    @ManyToOne
    @JoinColumn(name = "propietario")
    private Usuario propietario;

    public Review() {
    }
}
