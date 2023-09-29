package com.EquipoB.AlquilerQuinchos.Controladores;

import com.EquipoB.AlquilerQuinchos.Entitades.ImagenPropiedad;
import com.EquipoB.AlquilerQuinchos.Entitades.Propiedad;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioImagenPropiedad;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioPropiedad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/propiedades")
public class PropiedadesControlador {

    private final ServicioPropiedad servicioPropiedad;
    private final ServicioImagenPropiedad servicioImagen;

    @Autowired
    public PropiedadesControlador(ServicioPropiedad servicioPropiedad, ServicioImagenPropiedad servicioImagen) {
        this.servicioPropiedad = servicioPropiedad;
        this.servicioImagen = servicioImagen;
    }

    @PreAuthorize("hasAnyRole('ROL_PROPIETARIO', 'ROL_ADMINISTRADOR')")
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

    @GetMapping("/lista/{id}")
    public String viewProperty(@PathVariable Long id, Model model) {

        Propiedad propiedad = servicioPropiedad.mostrarPropiedadPorId(id);

        model.addAttribute("propiedad", propiedad);

        return "propiedad.html";
    }

}

