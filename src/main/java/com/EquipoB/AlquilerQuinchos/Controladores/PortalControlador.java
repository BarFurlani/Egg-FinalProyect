package com.EquipoB.AlquilerQuinchos.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @GetMapping("/")
    public String index(){
        return "index.html";
    }

//    este metodo va servir si para presentar otro index
    @GetMapping("/inicio")
    public String inicio(){
        return "index.html";
    }


}
