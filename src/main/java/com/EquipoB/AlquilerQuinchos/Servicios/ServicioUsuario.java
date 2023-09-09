package com.EquipoB.AlquilerQuinchos.Servicios;

import com.EquipoB.AlquilerQuinchos.Entitades.Propiedad;
import com.EquipoB.AlquilerQuinchos.Entitades.Usuario;
import com.EquipoB.AlquilerQuinchos.Excepciones.ExcepcionInformacionInvalida;
import com.EquipoB.AlquilerQuinchos.Excepciones.ExcepcionNoEncontrado;
import com.EquipoB.AlquilerQuinchos.Repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioUsuario {

    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioUsuario(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    //MÉTODO PARA CREAR USUARIOS

    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        validacion(usuario);
        return repositorioUsuario.save(usuario);
    }

    //MÉTODOS PARA LEER USUARIOS

    public List<Usuario> traerATodosLosUsuarios() {
        return repositorioUsuario.findAll();
    }

    public List<Usuario> traerUsuariosPorAlta() {
        return repositorioUsuario.findByAlta(true);
    }

    public List<Usuario> traerUsuarioPorBaja() {
        return repositorioUsuario.findByBaja(true);
    }

    public Usuario traerUsuarioPorId(Long id) {
        return repositorioUsuario.findById(id).orElseThrow(() -> new ExcepcionNoEncontrado("No se pudo encontrar al usuario con ID: " + id));
    }
    public Usuario traerUsuarioPorNombre(String username) {
        return repositorioUsuario.findByNombre(username).orElseThrow(() -> new ExcepcionNoEncontrado("No se pudo encontrar al usuario con nombre: " + username));
    }

    public Usuario traerUsuarioPorEmail(String email) {
        return repositorioUsuario.findByEmail(email).orElseThrow(() -> new ExcepcionNoEncontrado("No se pudo encontrar al usuario con email: " + email));
    }

    //MÉTODO PARA LEER PROPIEDADES SEGÚN EL USUARIO

    public List<Propiedad> traerPropiedadesPorUsuario(Long id) {
        Optional<Usuario> usuarioOptional = repositorioUsuario.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            return usuario.getPropiedades();
        } else {
            throw new ExcepcionNoEncontrado("No se pudo encontrar al usuario con ID: " + id);
        }
    }

    //MÉTODO PARA ACTUALIZAR/MODIFICAR USUARIOS

    @Transactional
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        return repositorioUsuario.findById(id)
                .map(usuario -> {
                    usuarioActualizado.setId(id);
                    validacion(usuarioActualizado);
                    return repositorioUsuario.save(usuarioActualizado);
                })
                .orElseThrow(() -> new ExcepcionNoEncontrado("No se pudo encontrar al usuario con ID: " + id));
    }

    //MÉTODOS PARA DAR DE ALTA Y BAJA A LOS USUARIOS(OPCIONAL)

    @Transactional
    public void darDeAltaAUsuario(Long id) {
        Optional<Usuario> usuarioOptional = repositorioUsuario.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setAlta(true);
            usuario.setBaja(false);
            repositorioUsuario.save(usuario);
        } else {
            throw new ExcepcionNoEncontrado("No se pudo encontrar al usuario con ID: " + id);
        }
    }

    @Transactional
    public void darDeBajaAUsuario(Long id) {
        Optional<Usuario> usuarioOptional = repositorioUsuario.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setAlta(false);
            usuario.setBaja(true);
            repositorioUsuario.save(usuario);
        } else {
            throw new ExcepcionNoEncontrado("No se pudo encontrar al usuario con ID: " + id);
        }
    }


    public void validacion(Usuario usuario) {
        if (usuario.getUsername() == null || usuario.getUsername().isEmpty()) {
            throw new ExcepcionInformacionInvalida("Nombre de usuario requerido");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new ExcepcionInformacionInvalida("Dirección de email requerida");
        }
        if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            throw new ExcepcionInformacionInvalida("Contraseña requerida");
        }
    }

}
