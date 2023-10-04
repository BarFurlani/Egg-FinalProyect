package com.EquipoB.AlquilerQuinchos.Entitades;

import com.EquipoB.AlquilerQuinchos.Enumeraciones.RolUsuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @ToString @AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
//    @Column(unique = true)
    private String username;
//    @Column(unique = true)
    private String email;
    private String password;
    private boolean alta;
    private boolean baja;

    @OneToMany(mappedBy = "inquilino", fetch = FetchType.LAZY)
    private List<Review> reviewsDadas;

    @OneToMany(mappedBy = "propietario", fetch = FetchType.LAZY)
    private List<Review> reviewsRecibidas;

    @OneToOne
    private ImagenUsuario imagen;

    @Enumerated(EnumType.STRING)
    private RolUsuario rol;

    @OneToMany(mappedBy = "propietario")
    private List<Propiedad> propiedades;

    @OneToOne(mappedBy = "inquilino")
    private Propiedad propiedadAlquilada;

    //Constructores

    public Usuario() {
    }

    public Usuario(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.alta = true;
    }
}
