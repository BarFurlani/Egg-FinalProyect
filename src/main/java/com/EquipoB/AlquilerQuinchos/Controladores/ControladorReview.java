package com.EquipoB.AlquilerQuinchos.Controladores;

import com.EquipoB.AlquilerQuinchos.Entitades.Propiedad;
import com.EquipoB.AlquilerQuinchos.Entitades.Review;
import com.EquipoB.AlquilerQuinchos.Excepciones.ExcepcionInformacionInvalida;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioPropiedad;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioReview;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reserva_pasada")
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

    @PreAuthorize("permitAll()")
    @GetMapping("/formulario")
    public String mostrarForm(Model model) {
        Review review = new Review();
        model.addAttribute("review", review);
        return "reserva_pasada_registro.html";
    }

    @PostMapping("/agregarReview")
    public String agregarReview(
            @RequestParam String nombrePropiedad,
            @RequestParam Long idInquilino,
            @RequestParam Integer puntuacion,
            @RequestParam String comentario,
            ModelMap model) {

        Propiedad propiedad = servicioPropiedad.mostrarPropiedadPorNombre(nombrePropiedad);
        if (propiedad != null) {
            try {
                servicioReview.crearReview(propiedad, servicioUsuario.traerUsuarioPorId(idInquilino), propiedad.getPropietario(), puntuacion, comentario);
                model.put("mensajeExito", "Posteado!");
            } catch (ExcepcionInformacionInvalida e) {
                model.put("mensajeError", e.getMessage());
            }
        } else {
            model.put("mensajeError", "Propiedad no encontrada");
        }
        return "redirect:/reserva_pasada/reseña";
    }

}
