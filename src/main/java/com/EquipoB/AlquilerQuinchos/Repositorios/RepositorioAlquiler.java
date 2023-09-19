package com.EquipoB.AlquilerQuinchos.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EquipoB.AlquilerQuinchos.Entitades.Alquiler;

public interface RepositorioAlquiler extends JpaRepository<Alquiler, Long> {
  
}
