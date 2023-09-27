package com.EquipoB.AlquilerQuinchos.Entitades;

import com.EquipoB.AlquilerQuinchos.Enumeraciones.RolUsuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

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

    @ElementCollection
    @CollectionTable(name = "reviews_usuario", joinColumns = @JoinColumn(name = "usuario_id"))
    @OneToMany(mappedBy = "inquilino", fetch = FetchType.LAZY)
    private List<Review> review;

    @OneToOne
    private Imagen imagen;

    @Enumerated(EnumType.STRING)
    private RolUsuario rol;

    @OneToMany(mappedBy = "propietario")
    private List<Propiedad> propiedades;

    @OneToOne(mappedBy = "inquilino")
    private Propiedad propiedadAlquilada;

    //Constructores

    public Usuario() {
    }

//<<<<<<< HEAD
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

//=======
//>>>>>>> 100912c56915c8ee71faca72d8d06b8ec3f26cb1
    public Usuario(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
