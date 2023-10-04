package com.EquipoB.AlquilerQuinchos.Servicios;

import com.EquipoB.AlquilerQuinchos.Entitades.Propiedad;
import com.EquipoB.AlquilerQuinchos.Entitades.Usuario;
import com.EquipoB.AlquilerQuinchos.Enumeraciones.TipoDePropiedad;
import com.EquipoB.AlquilerQuinchos.Excepciones.ExcepcionInformacionInvalida;
import com.EquipoB.AlquilerQuinchos.Excepciones.ExcepcionNoEncontrado;
import com.EquipoB.AlquilerQuinchos.Repositorios.RepositorioImagenPropiedad;
import com.EquipoB.AlquilerQuinchos.Repositorios.RepositorioPropiedad;
import com.EquipoB.AlquilerQuinchos.Repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ServicioPropiedad {

    private final RepositorioPropiedad repositorioPropiedad;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioImagenPropiedad repositorioImagen;
    private final ServicioUsuario servicioUsuario;
    private final ServicioImagenPropiedad servicioImagen;

    @Autowired
    public ServicioPropiedad(RepositorioPropiedad repositorioPropiedad, RepositorioUsuario repositorioUsuario, RepositorioImagenPropiedad repositorioImagen, ServicioUsuario servicioUsuario, ServicioImagenPropiedad servicioImagen) {
        this.repositorioPropiedad = repositorioPropiedad;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioImagen = repositorioImagen;
        this.servicioUsuario = servicioUsuario;
        this.servicioImagen = servicioImagen;
    }

    //CREATE

    @Transactional
    public Propiedad registrarPropiedad(Usuario propietario, String nombre, String ciudad, String direccion, String descripcion, Double precioPorNoche, List<MultipartFile> imagenes) throws IOException {
        Propiedad propiedad = new Propiedad();
        propiedad.setPropietario(propietario);
        propiedad.setNombre(nombre);
        propiedad.setCiudad(ciudad);
        propiedad.setDireccion(direccion);
        propiedad.setDescripcion(descripcion);
        propiedad.setPrecioPorNoche(precioPorNoche);
        validacion(propiedad);
        repositorioPropiedad.save(propiedad);
        for (MultipartFile imagen : imagenes) {
            servicioImagen.guardarImagen(imagen, propiedad);
        }
        return propiedad;
    }

    //READ

    public List<Propiedad> mostrarTodasLasPropiedades() {
        return repositorioPropiedad.findAll();
    }

    public Propiedad mostrarPropiedadPorId(Long id) {
        return repositorioPropiedad.findById(id).orElseThrow(() -> new ExcepcionNoEncontrado("No se pudo encontrar a la propiedad con ID: " + id));
    }

    public Propiedad mostrarPropiedadPorNombre(String nombre) {
        return repositorioPropiedad.findByNombre(nombre).orElseThrow(() -> new ExcepcionNoEncontrado("No se pudo encontrar a la propiedad con nombre: " + nombre));
    }

    public List<Propiedad> mostrarPropiedadPorTipo(TipoDePropiedad tipoDePropiedad) {
        List<Propiedad> propiedades = repositorioPropiedad.findByTipoDePropiedad(tipoDePropiedad);
        if (propiedades.isEmpty()) {
            throw new ExcepcionNoEncontrado("No se pudo encontrar alguna propiedad de tipo: " + tipoDePropiedad);
        }
        return propiedades;
    }

    public List<Propiedad> mostrarPropiedadPorPropietario(Usuario propietario) {
        List<Propiedad> propiedades = repositorioPropiedad.findByPropietario(propietario);
        if (propiedades.isEmpty()) {
            throw new ExcepcionNoEncontrado("No se pudo encontrar alguna propiedad de algún propietario con ID: " + propietario.getId());
        }
        return propiedades;
    }

    public Propiedad mostrarPropiedadPorCliente(Usuario inquilino) {
        return repositorioPropiedad.findByInquilino(inquilino).orElseThrow(() -> new ExcepcionNoEncontrado("No se pudo encontrar alguna propiedad alquilada por algún cliente con ID: " + inquilino.getId()));
    }

    //UPDATE

    @Transactional
    public Propiedad actualizarPropiedad(Long id, String nombre, String ciudad, String direccion, String descripcion, Double precioPorNoche) {
        Optional<Propiedad> propiedadOptional = repositorioPropiedad.findById(id);
        if (propiedadOptional.isPresent()) {
            Propiedad newPropiedad = propiedadOptional.get();
            newPropiedad.setNombre(nombre);
            newPropiedad.setCiudad(ciudad);
            newPropiedad.setDireccion(direccion);
            newPropiedad.setDescripcion(descripcion);
            newPropiedad.setPrecioPorNoche(precioPorNoche);
            validacion(newPropiedad);
            return repositorioPropiedad.save(newPropiedad);
        } else {
            throw new ExcepcionNoEncontrado("No se pudo encontrar a la propiedad con ID: " + id);
        }
    }

    //DELETE

    @Transactional
    public boolean eliminarPropiedad(Long id) {
        Optional<Propiedad> propiedadOptional = repositorioPropiedad.findById(id);
        if (propiedadOptional.isPresent()) {
            Propiedad propiedad = propiedadOptional.get();
            repositorioPropiedad.delete(propiedad);
            return true;
        } else {
            throw new ExcepcionNoEncontrado("No se pudo encontrar a la propiedad con ID: " + id);
        }
    }

    //EXTRAS

    public boolean verDisponibilidad(LocalDate fecha, Propiedad propiedad) {
        Map<LocalDate, String> disponibilidad = propiedad.getCalendarioDeDisponibilidad();
        if (disponibilidad.containsKey(fecha)) {
            String estado = disponibilidad.get(fecha);
            if (estado != null && estado.equalsIgnoreCase("disponible")) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public Propiedad agregarServicio(Propiedad propiedad, String servicio) {
        List<String> servicios = propiedad.getServicios();
        servicios.add(servicio);
        propiedad.setServicios(servicios);
        return repositorioPropiedad.save(propiedad);
    }

    @Transactional
    public Propiedad eliminarServicio(Propiedad propiedad, String servicio) {
        List<String> servicios = propiedad.getServicios();
        servicios.remove(servicio);
        propiedad.setServicios(servicios);
        return repositorioPropiedad.save(propiedad);
    }

    public void agregarImagen(Long id, List<MultipartFile> imagenes) throws IOException {
        Optional<Propiedad> propiedadOptional = repositorioPropiedad.findById(id);
        if (propiedadOptional.isPresent()) {
            for (MultipartFile imagen : imagenes) {
                servicioImagen.guardarImagen(imagen, propiedadOptional.get());
            }
        } else {
            throw new ExcepcionNoEncontrado("No se pudo encontrar a la propiedad con ID: " + id);
        }
    }

    public void validacion(Propiedad propiedad) {
        if (propiedad.getNombre() == null || propiedad.getNombre().isEmpty()) {
            throw new ExcepcionInformacionInvalida("Nombre de propiedad requerido");
        }
        if (propiedad.getCiudad() == null || propiedad.getCiudad().isEmpty()) {
            throw new ExcepcionInformacionInvalida("Ciudad donde está la propiedad requerida");
        }
        if (propiedad.getDireccion() == null || propiedad.getDireccion().isEmpty()) {
            throw new ExcepcionInformacionInvalida("Dirección de la propiedad requerida");
        }
        if (propiedad.getPrecioPorNoche() == null) {
            throw new ExcepcionInformacionInvalida("Precio de alquiler por noche requerido");
        }
        if (propiedad.getDescripcion() == null || propiedad.getDescripcion().isEmpty()) {
            throw new ExcepcionInformacionInvalida("Descripción del lugar requerida");
        }

    }

}
