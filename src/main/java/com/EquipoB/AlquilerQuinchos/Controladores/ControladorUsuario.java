
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EquipoB.AlquilerQuinchos.Controladores;

import com.EquipoB.AlquilerQuinchos.Entitades.ImagenPropiedad;
import com.EquipoB.AlquilerQuinchos.Entitades.Propiedad;
import com.EquipoB.AlquilerQuinchos.Entitades.Usuario;
import com.EquipoB.AlquilerQuinchos.Excepciones.ExcepcionInformacionInvalida;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioImagenPropiedad;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioPropiedad;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author mateo
 */
@Controller
@RequestMapping("/usuario")
public class ControladorUsuario {

    private final ServicioPropiedad servicioPropiedad;
    private final ServicioImagenPropiedad servicioImagenPropiedad;
    private final ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorUsuario(ServicioPropiedad servicioPropiedad, ServicioImagenPropiedad servicioImagenPropiedad, ServicioUsuario servicioUsuario) {
        this.servicioPropiedad = servicioPropiedad;
        this.servicioImagenPropiedad = servicioImagenPropiedad;
        this.servicioUsuario = servicioUsuario;
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registro_usuario.html"; // formulario de registro de usuario
    }


    @PostMapping("/registro")// post de formulario de registro de usuario, con los datos ingresados
    public String registro(@RequestParam String usuarioNombre, @RequestParam String usuarioEmail, @RequestParam String password, @RequestParam String password2,@RequestParam("Rol") String rolSeleccionado, ModelMap modelo) {

        try {
            servicioUsuario.registrarUsuario(usuarioNombre, usuarioEmail, password, password2, rolSeleccionado);

            modelo.put("exito", "Usuario registrado correctamente");// para enviar mensaje a la vista mediante un modelo llamado, con la referencia "exito"
            return "registro_usuario.html"; //retorna nuevamente vista inicio

        } catch (ExcepcionInformacionInvalida ex) {
            modelo.put("error", ex.getMessage());//mensaje de error enviado a la vista mediante un modelo, con la referencia "error".
            modelo.put("email", usuarioEmail);

            return "registro_usuario.html";//retorna nuevamente vista inicio.
        }

    }


    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        boolean ocultarBoton = true;
        modelo.addAttribute("ocultarBoton", ocultarBoton);

        if (error != null) {
            modelo.put("error", "Usuario o contraseña inválidos.");//envia un mensaje a la vista mediante un modelo, con la referencia "error" si la variable error contiene una excepción.
        }

        return "login.html";//vista de formulario para inicio de sesion.
    }



    @PreAuthorize("permitAll()")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {
        return "inicio.html";
    }

    @GetMapping("/perfil")
    public String perfil(@RequestParam(required = false) String error,HttpSession session, ModelMap modelo) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        System.out.println(usuario.getUsername());
        modelo.put("usuario", servicioUsuario.traerUsuarioPorId(usuario.getId()));
        if (error != null) {
            modelo.put("error", "Usuario o contraseña inválidos.");//envia un mensaje a la vista mediante un modelo, con la referencia "error" si la variable error contiene una excepción.
        }
        return "usuario.html";//vista de formulario para inicio de sesion.
    }

}
