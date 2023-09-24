package com.EquipoB.AlquilerQuinchos.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
 @RequestMapping("/propiedades")
public class PropiedadesControlador {

    @GetMapping("")
    public  String propiedades(){
        return "propiedades.html";
    }

//    metodo proboar html propiedades borrar para que no pise al de jose
    @GetMapping("/registro")
    public String propiedades(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "Usuario o contraseña inválidos.");//envia un mensaje a la vista mediante un modelo, con la referencia "error" si la variable error contiene una excepción.
        }
        return "registro_propiedades.html";//vista de formulario para inicio de sesion.
    }

}
