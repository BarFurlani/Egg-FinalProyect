package com.EquipoB.AlquilerQuinchos.Servicios;

import com.EquipoB.AlquilerQuinchos.Entitades.ImagenPropiedad;
import com.EquipoB.AlquilerQuinchos.Entitades.ImagenReview;
import com.EquipoB.AlquilerQuinchos.Entitades.Propiedad;
import com.EquipoB.AlquilerQuinchos.Entitades.Review;
import com.EquipoB.AlquilerQuinchos.Excepciones.ExcepcionInformacionInvalida;
import com.EquipoB.AlquilerQuinchos.Excepciones.ExcepcionNoEncontrado;
import com.EquipoB.AlquilerQuinchos.Repositorios.RepositorioImagenPropiedad;
import com.EquipoB.AlquilerQuinchos.Repositorios.RepositorioImagenReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ServicioImagenReview {


    private final RepositorioImagenReview repositorioImagen;

    @Autowired
    public ServicioImagenReview(RepositorioImagenReview repositorioImagen) {
        this.repositorioImagen = repositorioImagen;
    }

    @Transactional
    public ImagenReview guardarImagen(MultipartFile archivo, Review review) throws IOException {
        validacion(archivo);

        ImagenReview imagen = new ImagenReview();
        imagen.setNombre(archivo.getOriginalFilename());
        imagen.setContentType(archivo.getContentType());
        imagen.setContenido(archivo.getBytes());
        imagen.setReview(review);

        return repositorioImagen.save(imagen);
    }

    public List<ImagenReview> listarImagenes() {
        return repositorioImagen.findAll();
    }

    @Transactional
    public ImagenReview actualizarImagen(Long id, MultipartFile archivo) throws IOException {
        validacion(archivo);

        ImagenReview imagenExistente = repositorioImagen.findById(id)
                .orElseThrow(() -> new ExcepcionNoEncontrado("No se pudo encontrar la imagen con ID: " + id));

        imagenExistente.setNombre(archivo.getOriginalFilename());
        imagenExistente.setContentType(archivo.getContentType());
        imagenExistente.setContenido(archivo.getBytes());

        return repositorioImagen.save(imagenExistente);
    }

    @Transactional
    public void eliminarImagen(Long id) {
        ImagenReview imagenExistente = repositorioImagen.findById(id)
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

    public byte[] imagenABite(Long id) {
        return repositorioImagen.findById(id).get().getContenido();
    }
}
