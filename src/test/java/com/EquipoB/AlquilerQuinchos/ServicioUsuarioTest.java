package com.EquipoB.AlquilerQuinchos;

import com.EquipoB.AlquilerQuinchos.Repositorios.RepositorioUsuario;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioUsuario;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServicioUsuarioTest {

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @InjectMocks
    private ServicioUsuario servicioUsuario;


    @Test
    public void registrarUsuarioTest() {

    }

    @Test
    public void traerATodosLosUsuariosTest() {

    }

    @Test
    public void traerUsuariosPorAltaTest() {

    }

    @Test
    public void traerUsuariosPorBajaTest() {

    }

    @Test
    public void traerUsuarioPorIdTest() {

    }

    @Test
    public void traerUsuarioPorNombreTest() {

    }

    @Test
    public void traerUsuarioPorEmailTest() {

    }

    @Test
    public void actualizarUsuarioTest() {

    }

    @Test
    public void darDeAltaAUsuarioTest() {

    }

    @Test
    public void darDeBajaAUsuarioTest() {

    }

}
