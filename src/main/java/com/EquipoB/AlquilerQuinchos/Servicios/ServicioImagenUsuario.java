package com.EquipoB.AlquilerQuinchos.Servicios;

import com.EquipoB.AlquilerQuinchos.Entitades.ImagenPropiedad;
import com.EquipoB.AlquilerQuinchos.Entitades.ImagenUsuario;
import com.EquipoB.AlquilerQuinchos.Entitades.Usuario;
import com.EquipoB.AlquilerQuinchos.Excepciones.ExcepcionInformacionInvalida;
import com.EquipoB.AlquilerQuinchos.Excepciones.ExcepcionNoEncontrado;
import com.EquipoB.AlquilerQuinchos.Repositorios.RepositorioImagenPropiedad;
import com.EquipoB.AlquilerQuinchos.Repositorios.RepositorioImagenUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ServicioImagenUsuario {


    private final RepositorioImagenUsuario repositorioImagen;

    @Autowired
    public ServicioImagenUsuario(RepositorioImagenUsuario repositorioImagen) {
        this.repositorioImagen = repositorioImagen;
    }

    @Transactional
    public ImagenUsuario guardarImagen(MultipartFile archivo, Usuario usuario) throws IOException {
        validacion(archivo);

        ImagenUsuario imagen = new ImagenUsuario();
        imagen.setNombre(archivo.getOriginalFilename());
        imagen.setContentType(archivo.getContentType());
        imagen.setContenido(archivo.getBytes());
        imagen.setUsuario(usuario);

        return repositorioImagen.save(imagen);
    }

    public List<ImagenUsuario> listarImagenes() {
        return repositorioImagen.findAll();
    }

    @Transactional
    public ImagenUsuario actualizarImagen(Long id, MultipartFile archivo) throws IOException {
        validacion(archivo);

        ImagenUsuario imagenExistente = repositorioImagen.findById(id)
                .orElseThrow(() -> new ExcepcionNoEncontrado("No se pudo encontrar la imagen con ID: " + id));

        imagenExistente.setNombre(archivo.getOriginalFilename());
        imagenExistente.setContentType(archivo.getContentType());
        imagenExistente.setContenido(archivo.getBytes());

        return repositorioImagen.save(imagenExistente);
    }

    @Transactional
    public void eliminarImagen(Long id) {
        ImagenUsuario imagenExistente = repositorioImagen.findById(id)
                .orElseThrow(() -> new ExcepcionNoEncontrado("No se pudo encontrar la imagen con ID: " + id));

        repositorioImagen.delete(imagenExistente);
    }


    public void validacion(MultipartFile archivo) {
        if (archivo.isEmpty()) {
            throw new ExcepcionInformacionInvalida("El archivo de imagen está vacío");
        }

        String contentType = archivo.getContentType();
        if (contentType == null || !(contentType.equals("image/jpeg"))) {
            throw new ExcepcionInformacionInvalida("El archivo no es una imagen JPEG válida");
        }
    }
}
