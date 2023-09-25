package com.EquipoB.AlquilerQuinchos.Entitades;

import com.EquipoB.AlquilerQuinchos.Enumeraciones.TipoDePropiedad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
//import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Getter @Setter @ToString @AllArgsConstructor
public class Propiedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private TipoDePropiedad tipoDePropiedad;

    private String ubicacion;
    private String descripcion;
    private Double precioPorNoche;
    private Double tarifaDeLimpieza;
    private Double tarifaPorServicio;
    private Integer numeroDeBanos;
    private Integer numeroDeCamas;
    private String reglas;

    @ElementCollection
    @CollectionTable(name = "reviews_propiedad", joinColumns = @JoinColumn(name = "propiedad_id"))
    @OneToMany(mappedBy = "propiedad", fetch = FetchType.LAZY)
    private List<Review> review;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propiedad", fetch = FetchType.LAZY)
    private List<Imagen> imagenes;

    @ElementCollection
    @CollectionTable(name = "servicios_propiedad", joinColumns = @JoinColumn(name = "propiedad_id"))
    @Column(name = "servicios")
    private List<String> servicios;

    @ElementCollection
    @CollectionTable(name = "disponibilidad", joinColumns = @JoinColumn(name = "propiedad_id"))
    @MapKeyColumn(name = "fecha")
    @Column(name = "estado")
    private Map<LocalDate, String> calendarioDeDisponibilidad;

    @OneToOne
    @JoinColumn(name = "inquilino")
    private Usuario inquilino;

    @ManyToOne
    @JoinColumn(name = "propietario")
    private Usuario propietario;

    public Propiedad() {
    }

}
