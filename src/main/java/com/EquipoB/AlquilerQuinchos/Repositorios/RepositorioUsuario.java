package com.EquipoB.AlquilerQuinchos.Repositorios;

import com.EquipoB.AlquilerQuinchos.Entitades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByAlta(boolean alta);

    List<Usuario> findByBaja(boolean baja);
}
