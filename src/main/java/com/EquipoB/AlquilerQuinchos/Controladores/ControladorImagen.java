package com.EquipoB.AlquilerQuinchos.Controladores;

import com.EquipoB.AlquilerQuinchos.Entitades.ImagenPropiedad;
import com.EquipoB.AlquilerQuinchos.Entitades.Usuario;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioImagenPropiedad;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioPropiedad;
import com.EquipoB.AlquilerQuinchos.Servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/imagen")
public class ControladorImagen {

    @Autowired
    private ServicioUsuario servicioUsuario;
    @Autowired
    private ServicioPropiedad servicioPropiedad;
    @Autowired
    private ServicioImagenPropiedad servicioImagen;

    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable Long id) {
        Usuario usuario = servicioUsuario.traerUsuarioPorId(id);

        if (usuario == null || usuario.getImagen() == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] imagen = usuario.getImagen().getContenido();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }


    @GetMapping("/propiedades/{id}")
    public ResponseEntity<byte[]> imagenPropiedad(@PathVariable Long id) {
        byte[] imgPropiedad = servicioImagen.imagenABite(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imgPropiedad, headers, HttpStatus.OK);
    }


//    hacer un metodo igual solo que busque imagenes por id de la imagen no de la propiedad
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> imagenPropiedades(@PathVariable Long id) {
        byte[] imgPropiedad = servicioImagen.imagenABite(id);
       HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
       return new ResponseEntity<>(imgPropiedad, headers, HttpStatus.OK);
   }


}
