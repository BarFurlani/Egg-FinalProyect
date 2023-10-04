package com.EquipoB.AlquilerQuinchos.Servicios;

import com.EquipoB.AlquilerQuinchos.Entitades.Propiedad;
import com.EquipoB.AlquilerQuinchos.Entitades.Review;
import com.EquipoB.AlquilerQuinchos.Entitades.Usuario;
import com.EquipoB.AlquilerQuinchos.Excepciones.ExcepcionInformacionInvalida;
import com.EquipoB.AlquilerQuinchos.Excepciones.ExcepcionNoEncontrado;
import com.EquipoB.AlquilerQuinchos.Repositorios.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioReview {

    private final ReviewRepository reviewRepository;
    private final ServicioImagenReview servicioImagen;

    @Autowired
    public ServicioReview(ReviewRepository reviewRepository, ServicioImagenReview servicioImagen) {
        this.reviewRepository = reviewRepository;
        this.servicioImagen = servicioImagen;
    }

    @Transactional
    public Review crearReview(Propiedad propiedad, Usuario inquilino, Usuario propietario, Integer rating, String comentario, List<MultipartFile> imagenes) throws IOException {
        Review review = new Review();
        review.setPropiedad(propiedad);
        review.setInquilino(inquilino);
        review.setPropietario(propietario);
        review.setPuntuacion(rating);
        review.setComentario(comentario);
        review.setFecha(LocalDate.now());
        validacion(review);
        for (MultipartFile imagen : imagenes) {
            servicioImagen.guardarImagen(imagen, review);
        }
        return  reviewRepository.save(review);
    }

    public List<Review> listarReviews() {
        return reviewRepository.findAll();
    }

    public Review mostrarPorId(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Transactional
    public Review modificarReview(Long id, Integer rating, String comentario) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if (reviewOptional.isPresent()) {
            Review newReview = reviewOptional.get();
            newReview.setPuntuacion(rating);
            newReview.setComentario(comentario);
            validacion(newReview);
            return reviewRepository.save(newReview);
        } else {
            throw new ExcepcionNoEncontrado("No se pudo encontrar review con ID: " + id);
        }
    }

    @Transactional
    public boolean eliminarReview(Long id) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if (reviewOptional.isPresent()) {
            reviewRepository.deleteById(id);
            return true;
        } else {
            throw new ExcepcionNoEncontrado("No se pudo encontrar review con ID: " + id);
        }
    }

    @Transactional
    public void agregarImagen(Long id, List<MultipartFile> imagenes) throws IOException {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if (reviewOptional.isPresent()) {
            for (MultipartFile imagen : imagenes) {
                servicioImagen.guardarImagen(imagen, reviewOptional.get());
            }
        } else {
            throw new ExcepcionNoEncontrado("No se pudo encontrar review con ID: " + id);
        }
    }

    public void validacion(Review review) {
        if (review.getComentario() == null || review.getComentario().isEmpty()) {
            throw new ExcepcionInformacionInvalida("El comentario no puede estar vacío");
        }
        if (review.getPuntuacion() == null || review.getPuntuacion() < 0 && review.getPuntuacion() > 5) {
            throw new ExcepcionInformacionInvalida("La puntuación debe ser entre 1 y 5");
        }
    }
}

