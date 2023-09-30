package com.EquipoB.AlquilerQuinchos.Servicios;

import com.EquipoB.AlquilerQuinchos.Entitades.ImagenPropiedad;
import com.EquipoB.AlquilerQuinchos.Entitades.Propiedad;
import com.EquipoB.AlquilerQuinchos.Excepciones.ExcepcionInformacionInvalida;
import com.EquipoB.AlquilerQuinchos.Excepciones.ExcepcionNoEncontrado;
import com.EquipoB.AlquilerQuinchos.Repositorios.RepositorioImagenPropiedad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ServicioImagenPropiedad {


    private final RepositorioImagenPropiedad repositorioImagen;

    @Autowired
    public ServicioImagenPropiedad(RepositorioImagenPropiedad repositorioImagen) {
        this.repositorioImagen = repositorioImagen;
    }

    @Transactional
    public ImagenPropiedad guardarImagen(MultipartFile archivo, Propiedad propiedad) throws IOException {
        validacion(archivo);

        ImagenPropiedad imagen = new ImagenPropiedad();
        imagen.setNombre(archivo.getOriginalFilename());
        imagen.setContentType(archivo.getContentType());
        imagen.setContenido(archivo.getBytes());
        imagen.setPropiedad(propiedad);

        return repositorioImagen.save(imagen);
    }

    public List<ImagenPropiedad> listarImagenes() {
        return repositorioImagen.findAll();
    }

    @Transactional
    public ImagenPropiedad actualizarImagen(Long id, MultipartFile archivo) throws IOException {
        validacion(archivo);

        ImagenPropiedad imagenExistente = repositorioImagen.findById(id)
                .orElseThrow(() -> new ExcepcionNoEncontrado("No se pudo encontrar la imagen con ID: " + id));

        imagenExistente.setNombre(archivo.getOriginalFilename());
        imagenExistente.setContentType(archivo.getContentType());
        imagenExistente.setContenido(archivo.getBytes());

        return repositorioImagen.save(imagenExistente);
    }

    @Transactional
    public void eliminarImagen(Long id) {
        ImagenPropiedad imagenExistente = repositorioImagen.findById(id)
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
