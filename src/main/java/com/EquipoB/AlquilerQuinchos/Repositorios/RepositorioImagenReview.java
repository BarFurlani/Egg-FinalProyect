package com.EquipoB.AlquilerQuinchos.Repositorios;

import com.EquipoB.AlquilerQuinchos.Entitades.ImagenReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioImagenReview extends JpaRepository<ImagenReview, Long> {
}
