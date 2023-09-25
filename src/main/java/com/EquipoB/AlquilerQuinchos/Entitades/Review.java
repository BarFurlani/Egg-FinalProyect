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

    private Double puntuacion;
    private String comentario;
    private LocalDate fecha;

    @ManyToOne
    private Propiedad propiedad;

    @ManyToOne
    @JoinColumn(name = "inquilino")
    private Usuario inquilino;

    public Review() {
    }
}
