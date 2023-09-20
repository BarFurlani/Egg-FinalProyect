package com.EquipoB.AlquilerQuinchos.Entitades;

import com.EquipoB.AlquilerQuinchos.Enumeraciones.RolUsuario;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @ToString
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private boolean alta;
    private boolean baja;

    @Enumerated(EnumType.STRING)
    private RolUsuario rol;

    @OneToMany(mappedBy = "propietario")
    private List<Propiedad> propiedades;

    @OneToOne(mappedBy = "inquilino")
    private Propiedad propiedadAlquilada;

    //Constructores

    public Usuario() {
    }

    public Usuario(Long id, String username, String email, String password, boolean alta, boolean baja, RolUsuario rol, List<Propiedad> propiedades, Propiedad propiedadAlquilada) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
//        agregar telefono

        this.alta = alta;
        this.baja = baja;
        this.rol = rol;
        this.propiedades = propiedades;
        this.propiedadAlquilada = propiedadAlquilada;
    }

    public Usuario(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
