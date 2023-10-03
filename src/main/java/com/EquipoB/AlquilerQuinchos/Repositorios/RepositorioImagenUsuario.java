package com.EquipoB.AlquilerQuinchos.Repositorios;

import com.EquipoB.AlquilerQuinchos.Entitades.ImagenUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioImagenUsuario extends JpaRepository<ImagenUsuario, Long> {
}
