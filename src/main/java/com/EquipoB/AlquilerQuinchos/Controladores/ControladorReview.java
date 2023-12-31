package com.EquipoB.AlquilerQuinchos.Controladores;

import com.EquipoB.AlquilerQuinchos.Entitades.Propiedad;
import com.EquipoB.AlquilerQuinchos.Entitades.Review;
import com.EquipoB.AlquilerQuinchos.Excepciones.ExcepcionInformacionInvalida;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioPropiedad;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioReview;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ControladorReview {

    private final ServicioReview servicioReview;
    private final ServicioUsuario servicioUsuario;
    private final ServicioPropiedad servicioPropiedad;

    @Autowired
    public ControladorReview(ServicioReview servicioReview, ServicioUsuario servicioUsuario, ServicioPropiedad servicioPropiedad) {
        this.servicioReview = servicioReview;
        this.servicioUsuario = servicioUsuario;
        this.servicioPropiedad = servicioPropiedad;
    }

    @GetMapping("/formulario")
    public String mostrarForm(Model model) {
        Review review = new Review();
        model.addAttribute("review", review);
        return "review_registro.html";
    }

    @PostMapping("/agregarReview")
    public String agregarReview(
            @RequestParam String nombrePropiedad,
            @RequestParam Long idUsuario,
            @RequestParam Integer puntuacion,
            @RequestParam String comentario,
            @RequestParam MultipartFile[] archivos,
            ModelMap model) {

        Propiedad propiedad = servicioPropiedad.mostrarPropiedadPorNombre(nombrePropiedad);
        if (propiedad != null) {
            try {
                servicioReview.crearReview(propiedad, servicioUsuario.traerUsuarioPorId(idUsuario), propiedad.getPropietario(), puntuacion, comentario, Arrays.asList(archivos));
                model.put("mensajeExito", "Posteado!");
                return "review_registro.html";
            } catch (ExcepcionInformacionInvalida e) {
                model.put("mensajeError", e.getMessage());
                return "review_registro.html";
            } catch (IOException e) {
                e.printStackTrace();
                model.put("mensajeError", "Error al procesar imágenes");
                return "review_registro.html";
            }
        } else {
            model.put("mensajeError", "Propiedad no encontrada");
        }
       return "redirect:/reviews/lista";
    }

    @GetMapping("/lista")
    public String mostrarReviews(Model model) {
        List<Review> reviews = servicioReview.listarReviews();

        model.addAttribute("reviews", reviews);

        return "reseña.html";
    }

    @GetMapping("/lista/{id}")
    public String mostrarReview(@PathVariable Long id, Model model) {

        Review review = servicioReview.mostrarPorId(id);

        model.addAttribute("review", review);

        return "reseña.html";
    }

    @GetMapping("/modificaciones/{id}")
    public String mostrarUpdateForm(@PathVariable Long id, Model model) {

        Review review = servicioReview.mostrarPorId(id);

        model.addAttribute("review", review);

        return "modificar_reseña.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificarReview(
            @PathVariable Long idReview,
            @RequestParam Integer puntuacion,
            @RequestParam String comentario,
            ModelMap model) {
        try {
            servicioReview.modificarReview(idReview, puntuacion, comentario);
            model.put("mensajeExito", "Reseña actualizada!");
        } catch (ExcepcionInformacionInvalida e) {
            model.put("mensajeError", e.getMessage());
        }
        return "redirect:/reviews/lista";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarReview(@PathVariable Long id, ModelMap model) {
            servicioReview.eliminarReview(id);
            model.put("mensajeExito", "Reseña eliminada!");

        return "redirect:/reviews/lista";
    }

    @PostMapping("/agregarImagenes/{id}")
    public String agregarImagenes(@PathVariable Long id, MultipartFile[] archivos) throws IOException {
        servicioReview.agregarImagen(id, Arrays.asList(archivos));
        return "redirect:/reviews/modificaciones";
    }
}
