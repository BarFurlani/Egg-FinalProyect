package com.EquipoB.AlquilerQuinchos.Controladores;

import com.EquipoB.AlquilerQuinchos.Entitades.Imagen;
import com.EquipoB.AlquilerQuinchos.Entitades.Propiedad;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioImagen;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioPropiedad;
import org.springframework.beans.factory.annotation.Autowired;
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
   private final ServicioImagen servicioImagen;

   @Autowired
    public PropiedadesControlador(ServicioPropiedad servicioPropiedad, ServicioImagen servicioImagen) {
        this.servicioPropiedad = servicioPropiedad;
       this.servicioImagen = servicioImagen;
   }

    @GetMapping("/registrar")
    public String mostrarForm(Model model) {
        Propiedad propiedad = new Propiedad();
        model.addAttribute("propiedad", propiedad);

        return "registro_propiedades.html";
    }

    @PostMapping("/registro")
    public String registrarPropiedad(
            @RequestParam String nombre,
            @RequestParam String ciudad,
            @RequestParam String direccion,
            @RequestParam Double precioPorNoche,
            @RequestParam String descripcion,
            @RequestParam(required = false) MultipartFile[] archivos,
            RedirectAttributes redirectAttributes) {
        try {
            Propiedad propiedad = new Propiedad();
            propiedad.setNombre(nombre);
            propiedad.setCiudad(ciudad);
            propiedad.setDireccion(direccion);
            propiedad.setPrecioPorNoche(precioPorNoche);
            propiedad.setDescripcion(descripcion);

            propiedad = servicioPropiedad.registrarPropiedad(nombre, ciudad, direccion, descripcion, precioPorNoche);

            List<Imagen> listaImagen = new ArrayList<>();
            for (MultipartFile archivo : archivos) {
                Imagen imagen = servicioImagen.guardarImagen(archivo);
                imagen.setPropiedad(propiedad);
                listaImagen.add(imagen);
            }
            propiedad.setImagenes(listaImagen);

            servicioPropiedad.actualizarPropiedad(propiedad.getId(), propiedad);

            redirectAttributes.addFlashAttribute("mensajeDeExito", "Propiedad registrada correctamente");

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("mensajeDeError", "Error al subir imágenes de propiedad");
        }

        return "redirect:/propiedades/registrar";
    }

    @GetMapping("/lista")
    public String showAllProperties(Model model) {
        List<Propiedad> propiedades = servicioPropiedad.mostrarTodasLasPropiedades();

        model.addAttribute("propiedades", propiedades);

        return "propiedades.html";
    }

    @GetMapping("/vista/{id}")
    public String viewProperty(@PathVariable Long id, Model model) {

        Propiedad propiedad = servicioPropiedad.mostrarPropiedadPorId(id);

        model.addAttribute("propiedad", propiedad);

        return "propiedad.html";
    }


}
