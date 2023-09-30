package com.EquipoB.AlquilerQuinchos.Controladores;

import com.EquipoB.AlquilerQuinchos.Entitades.Propiedad;
import com.EquipoB.AlquilerQuinchos.Entitades.Usuario;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioImagenPropiedad;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioPropiedad;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/propiedades")
public class PropiedadesControlador {

    private final ServicioPropiedad servicioPropiedad;
    private final ServicioImagenPropiedad servicioImagen;
    private final ServicioUsuario servicioUsuario;

    @Autowired
    public PropiedadesControlador(ServicioPropiedad servicioPropiedad, ServicioImagenPropiedad servicioImagen, ServicioUsuario servicioUsuario) {
        this.servicioPropiedad = servicioPropiedad;
        this.servicioImagen = servicioImagen;
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
    public String showAllProperties(Model model) {
        List<Propiedad> propiedades = servicioPropiedad.mostrarTodasLasPropiedades();

        model.addAttribute("propiedades", propiedades);

        return "propiedades.html";
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/registrar")
    public String registrarPropiedad(
            @RequestParam Long idUsuario,
            @RequestParam String nombre,
            @RequestParam String ciudad,
            @RequestParam String direccion,
            @RequestParam Double precioPorNoche,
            @RequestParam String descripcion,
            @RequestParam MultipartFile[] archivos) {
        try {

            Propiedad propiedad = new Propiedad();

            propiedad = servicioPropiedad.registrarPropiedad(servicioUsuario.traerUsuarioPorId(idUsuario), nombre, ciudad, direccion, descripcion, precioPorNoche, Arrays.asList(archivos));

        } catch (IOException e) {
            e.getMessage();
        }

        return "propiedades.html";
    }

    @GetMapping("/lista/{id}")
    public String viewProperty(@PathVariable Long id, Model model) {

        Propiedad propiedad = servicioPropiedad.mostrarPropiedadPorId(id);

        model.addAttribute("propiedad", propiedad);

        return "propiedad.html";
    }

}

