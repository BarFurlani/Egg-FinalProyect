package com.EquipoB.AlquilerQuinchos.Servicios;

import com.EquipoB.AlquilerQuinchos.Entitades.Review;
import com.EquipoB.AlquilerQuinchos.Entitades.Usuario;
import com.EquipoB.AlquilerQuinchos.Excepciones.ExcepcionInformacionInvalida;
import com.EquipoB.AlquilerQuinchos.Excepciones.ExcepcionNoEncontrado;
import com.EquipoB.AlquilerQuinchos.Repositorios.RepositorioUsuario;
import com.EquipoB.AlquilerQuinchos.Repositorios.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioReview {

    private final ReviewRepository reviewRepository;
    private final RepositorioUsuario repositorioUsuario;
    private final ServicioUsuario servicioUsuario;

    @Autowired
    public ServicioReview(ReviewRepository reviewRepository, RepositorioUsuario repositorioUsuario, ServicioUsuario servicioUsuario) {
        this.reviewRepository = reviewRepository;
        this.repositorioUsuario = repositorioUsuario;
        this.servicioUsuario = servicioUsuario;
    }

    @Transactional
    public Review crearReview(Review review) {
        validacion(review);

        Optional<Usuario> inquilinoExistente =repositorioUsuario.findById(review.getInquilino().getId());
        if (inquilinoExistente.isPresent()) {
            review.setInquilino(inquilinoExistente.get());
        } else {
            Usuario inquilino = review.getInquilino();
            servicioUsuario.registrarUsuario(inquilino);
            review.setInquilino(inquilino);
        }
        return reviewRepository.save(review);
    }

    public List<Review> listarReviews() {
        return reviewRepository.findAll();
    }

    public Review mostrarPorId(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Transactional
    public Review modificarReview(Long id, Review review) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if (reviewOptional.isPresent()) {
            validacion(review);
            Review newReview = reviewOptional.get();

            newReview.setPuntuacion(review.getPuntuacion());
            newReview.setComentario(review.getComentario());
            newReview.setFecha(review.getFecha());

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

    public void validacion(Review review) {
        if (review.getComentario() == null || review.getComentario().isEmpty()) {
            throw new ExcepcionInformacionInvalida("El comentario no puede estar vacío");
        }
        if (review.getPuntuacion() == null || review.getPuntuacion() < 0 && review.getPuntuacion() > 5) {
            throw new ExcepcionInformacionInvalida("La puntuación debe ser entre 1 y 5");
        }
    }
}

