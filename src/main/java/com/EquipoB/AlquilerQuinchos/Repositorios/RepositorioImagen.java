package com.EquipoB.AlquilerQuinchos.Repositorios;

import com.EquipoB.AlquilerQuinchos.Entitades.ImagenPropiedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioImagen extends JpaRepository<ImagenPropiedad, Long> {
}
