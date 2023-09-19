/*Verifica Modificación */
package com.EquipoB.AlquilerQuinchos.Servicios;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EquipoB.AlquilerQuinchos.Entitades.Alquiler;
import com.EquipoB.AlquilerQuinchos.Repositorios.RepositorioAlquiler;

@Service
public class ServicioAlquiler {
  @Autowired
    RepositorioAlquiler alquilerRepo;

  public ArrayList<Alquiler> getAlquileres() {
    return (ArrayList<Alquiler>) alquilerRepo.findAll();
  }

  public Alquiler saveAlquiler(Alquiler alquiler) {
    return alquilerRepo.save(alquiler);
  }

  public Optional<Alquiler> getById(Long id) {
    return alquilerRepo.findById(id);
  }

  public Alquiler updateById(Alquiler request, Long id) {
    Alquiler alquiler = alquilerRepo.findById(id).get();

    alquiler.setId_Inquiline(request.getId_Inquiline());
    alquiler.setId_Property(request.getId_Property());
    alquiler.setFecIni(request.getFecIni());
    alquiler.setFecTer(request.getFecTer());

    return alquiler;

  }

  public Boolean deleteAlquiler (Long id) {
    try {
      alquilerRepo.deleteById(id);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
