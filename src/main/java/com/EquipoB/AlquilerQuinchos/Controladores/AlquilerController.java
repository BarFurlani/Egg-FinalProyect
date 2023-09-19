package com.EquipoB.AlquilerQuinchos.Controladores;

/*Verifica */

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EquipoB.AlquilerQuinchos.Entitades.Alquiler;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioAlquiler;

@RestController
@RequestMapping("/alquiler")
public class AlquilerController {
  @Autowired
  private ServicioAlquiler alquilerService;

  @GetMapping
  public ArrayList<Alquiler> getAlquileres() {
    return this.alquilerService.getAlquileres();
  }

  @PostMapping
  public Alquiler saveAlquiler(@RequestBody Alquiler alquiler) {
    return this.alquilerService.saveAlquiler(alquiler);
  }

  @GetMapping(path = "/{id}")
  public Optional<Alquiler> getAlquilerById(@PathVariable("id") Long id) {
       return this.alquilerService.getById(id);
  }

  @PutMapping(path = "/{id}")
  public Alquiler updateAlquilerById(@RequestBody Alquiler request, @PathVariable("id") Long id) {
      return this.alquilerService.updateById(request, id);
  }

  @DeleteMapping(path = "/{id}")
  public String deleteById(@PathVariable("id") Long id) {
    boolean ok = this.alquilerService.deleteAlquiler(id);

    if(ok) {
      return "Alquiler with id: "+ id +" deleted successfully";

  } else {
      return "Error of id: " + id + " deleted wrong";
  }

 }
}
