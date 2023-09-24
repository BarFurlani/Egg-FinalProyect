
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EquipoB.AlquilerQuinchos.Controladores;

import com.EquipoB.AlquilerQuinchos.Entitades.Usuario;
import com.EquipoB.AlquilerQuinchos.Excepciones.ExcepcionInformacionInvalida;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 *
 * @author mateo
 */
@Controller
@RequestMapping("/usuario")
public class ControladorUsuario {
    
    @Autowired
    private ServicioUsuario ServicioUsuario;
         
    @GetMapping("/registrar")
    public String registrar() {
        return "registro_usuario.html"; // formulario de registro de usuario
    }


//    agregar parametro de rol
    @PostMapping("/registro")// post de formulario de registro de usuario, con los datos ingresados
    public String registro(@RequestParam String usuarioNombre, @RequestParam String usuarioEmail, @RequestParam String password, @RequestParam String password2, ModelMap modelo) {

        try {
            ServicioUsuario.registrarUsuario(usuarioNombre, usuarioEmail, password, password2);

            modelo.put("exito", "Usuario registrado correctamente");// para enviar mensaje a la vista mediante un modelo llamado, con la referencia "exito"
            return "index.html"; //retorna nuevamente vista inicio 

        } catch (ExcepcionInformacionInvalida ex) {
            modelo.put("error", ex.getMessage());//mensaje de error enviado a la vista mediante un modelo, con la referencia "error".
            modelo.put("email", usuarioEmail);
            return "registro.html";//retorna nuevamente vista inicio.
        }

    }


    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "Usuario o contraseña inválidos.");//envia un mensaje a la vista mediante un modelo, con la referencia "error" si la variable error contiene una excepción.
        }
        return "login.html";//vista de formulario para inicio de sesion.
    }





    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        return "inicio.html";
    }

    @GetMapping("/perfil")
    public String perfil(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "Usuario o contraseña inválidos.");//envia un mensaje a la vista mediante un modelo, con la referencia "error" si la variable error contiene una excepción.
        }
        return "usuario.html";//vista de formulario para inicio de sesion.
    }



//
}
