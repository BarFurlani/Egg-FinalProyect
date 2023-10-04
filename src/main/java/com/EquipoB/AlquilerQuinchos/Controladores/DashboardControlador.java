package com.EquipoB.AlquilerQuinchos.Controladores;

import com.EquipoB.AlquilerQuinchos.Entitades.Usuario;
import com.EquipoB.AlquilerQuinchos.Excepciones.ExcepcionInformacionInvalida;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dashboard")
public class DashboardControlador {

    @Autowired
    ServicioUsuario servicioUsuario;

    @PreAuthorize("hasAnyRole('ROL_ADMINISTRADOR')")
     @GetMapping("/metodos")
     public String dashboard(){
         return  "dashboard.html";
     }

     @GetMapping("/listar_usuarios")
     public String listarUsuarios(ModelMap modelo){
         modelo.addAttribute("listausuarios",servicioUsuario.traerATodosLosUsuarios());
         return  "dashboard_usuarios_listar.html";
     }


    @PostMapping("/listar_usuarios/{id}")
    public String perfil(@PathVariable Long id){
        try {
            servicioUsuario.darDeAltaAUsuario(id);
//            redirAttr.addFlashAttribute("exito","se dio de alta con exito");
            return  "redirect:/dashboard/listar_usuarios";
        } catch (ExcepcionInformacionInvalida e) {
//            redirAttr.addFlashAttribute("error", e.getMessage());
            return "redirect:/dashboard/listar_usuarios";

        }

    }



}
