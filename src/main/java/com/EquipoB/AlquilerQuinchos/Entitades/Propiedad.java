package com.EquipoB.AlquilerQuinchos.Entitades;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
public class Propiedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "inquilino")
    private Usuario inquilino;

    @ManyToOne
    @JoinColumn(name = "propietario")
    private Usuario propietario;

    public Propiedad() {
    }

    public Propiedad(Long id, Usuario inquilino, Usuario propietario) {
        this.id = id;
        this.inquilino = inquilino;
        this.propietario = propietario;
    }
}
