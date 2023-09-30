package com.EquipoB.AlquilerQuinchos.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardControlador {


     @GetMapping("")
     public String dashboard(){
         return  "dashboard.html";
     }

     @GetMapping("/listar_usuarios")
     public String listarUsuarios(){
         return  "dashboard_usuarios_listar.html";
     }
}
