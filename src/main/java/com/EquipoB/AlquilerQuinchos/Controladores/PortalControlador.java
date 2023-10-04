package com.EquipoB.AlquilerQuinchos.Controladores;

import com.EquipoB.AlquilerQuinchos.Entitades.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    //    este metodo va servir si para presentar otro index
    @PermitAll
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado != null && logueado.getRol() != null && logueado.getRol()
                                                                     .toString()
                                                                     .equals("ROL_ADMINISTRADOR")) {
            return "redirect:/dashboard/metodos";
        }
        return "index.html";
    }
}
