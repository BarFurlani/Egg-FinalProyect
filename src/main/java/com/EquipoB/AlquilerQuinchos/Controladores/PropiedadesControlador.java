package com.EquipoB.AlquilerQuinchos.Controladores;

import com.EquipoB.AlquilerQuinchos.Entitades.Propiedad;
import com.EquipoB.AlquilerQuinchos.Excepciones.ExcepcionInformacionInvalida;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioImagenPropiedad;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioPropiedad;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/propiedades")
public class PropiedadesControlador {

    @Autowired
    private final ServicioPropiedad servicioPropiedad;

    @Autowired
    private final ServicioUsuario servicioUsuario;

    @Autowired
    public PropiedadesControlador(ServicioPropiedad servicioPropiedad, ServicioUsuario servicioUsuario) {
        this.servicioPropiedad = servicioPropiedad;
        this.servicioUsuario = servicioUsuario;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/formulario")
    public String mostrarForm(Model model) {
        Propiedad propiedad = new Propiedad();
        model.addAttribute("propiedad", propiedad);

        return "registro_propiedades.html";
    }

    @GetMapping("/lista")
    public String mostrarPropiedades(Model model) {
        List<Propiedad> propiedades = servicioPropiedad.mostrarTodasLasPropiedades();

        model.addAttribute("propiedades", propiedades);

        return "propiedades.html";
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/registro")
    public String registrarPropiedad(
            @RequestParam Long idUsuario,
            @RequestParam String nombre,
            @RequestParam String ciudad,
            @RequestParam String direccion,
            @RequestParam Double precioPorNoche,
            @RequestParam String descripcion,
            @RequestParam MultipartFile[] archivos,
            ModelMap model) {
        try {
            servicioPropiedad.registrarPropiedad(servicioUsuario.traerUsuarioPorId(idUsuario), nombre, ciudad, direccion, descripcion, precioPorNoche, Arrays.asList(archivos));
            model.put("mensajeExito", "Propiedad registrada con éxito!");
            return "registro_propiedades.html";
        } catch (IOException e) {
            e.printStackTrace();
            model.put("mensajeError", "Error al procesar las imágenes");
            return "registro_propiedades.html";
        } catch (ExcepcionInformacionInvalida e) {
            model.put("mensajeError", e.getMessage());
            return "registro_propiedades.html";
        }

    }

    @GetMapping("/lista/{id}")
    public String mostrarPropiedad(@PathVariable Long id, Model model) {

        Propiedad propiedad = servicioPropiedad.mostrarPropiedadPorId(id);

        model.addAttribute("propiedad", propiedad);

        return "propiedad.html";
    }

    @GetMapping("/modificaciones/{id}")
    public String mostrarUpdateForm(@PathVariable Long id, Model model) {

        Propiedad propiedad = servicioPropiedad.mostrarPropiedadPorId(id);

        model.addAttribute("propiedad", propiedad);

        return "modificar_propiedad.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificarPropiedad(
            @PathVariable Long idPropiedad,
            @RequestParam String nombre,
            @RequestParam String ciudad,
            @RequestParam String direccion,
            @RequestParam Double precioPorNoche,
            @RequestParam String descripcion,
            ModelMap model) {
        try {
            servicioPropiedad.actualizarPropiedad(idPropiedad, nombre, ciudad, direccion, descripcion, precioPorNoche);
            model.put("mensajeExito", "Propiedad actualizada!");
        } catch (ExcepcionInformacionInvalida e) {
            model.put("mensajeError", e.getMessage());
        }
        return "redirect:/propiedades/lista";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPropiedad(@PathVariable Long id, ModelMap model) {
            servicioPropiedad.eliminarPropiedad(id);
            model.put("mensajeExito", "Propiedad eliminada!");

            return "redirect:/propiedades/lista";
    }

    @PostMapping("/agregarImagenes/{id}")
    public String agregarImagenes(@PathVariable Long id, MultipartFile[] archivos) throws IOException {
        servicioPropiedad.agregarImagen(id, Arrays.asList(archivos));
        return "redirect:/propiedades/modificaciones" + id;
    }

    @GetMapping("/listaPorPropietario/{id}")
    public String mostrarPropiedadesPorPropietario(@PathVariable Long idPropietario, Model model) {

        List<Propiedad> propiedades = servicioPropiedad.mostrarPropiedadPorPropietario(servicioUsuario.traerUsuarioPorId(idPropietario));

        model.addAttribute("propiedades", propiedades);

        return "";
    }




}
