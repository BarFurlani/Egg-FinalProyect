package com.EquipoB.AlquilerQuinchos.Repositorios;

import com.EquipoB.AlquilerQuinchos.Entitades.Propiedad;
import com.EquipoB.AlquilerQuinchos.Entitades.Usuario;
import com.EquipoB.AlquilerQuinchos.Enumeraciones.TipoDePropiedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioPropiedad extends JpaRepository<Propiedad, Long> {

    Optional<Propiedad> findByNombre(String nombre);

    List<Propiedad> findByTipoDePropiedad(TipoDePropiedad tipoDePropiedad);

    List<Propiedad> findByPropietario(Usuario propietario);

    Optional<Propiedad> findByInquilino(Usuario inquilino);

}
