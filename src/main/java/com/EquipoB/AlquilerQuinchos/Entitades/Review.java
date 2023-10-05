package com.EquipoB.AlquilerQuinchos.Entitades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter @ToString @AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer puntuacion;
    private String comentario;
    private LocalDate fecha;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "review", fetch = FetchType.LAZY)
    private List<ImagenReview> imagenes;

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
