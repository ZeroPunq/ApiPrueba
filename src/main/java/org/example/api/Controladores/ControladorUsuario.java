package org.example.api.Controladores;

import jakarta.validation.Valid;
import org.example.api.Modelo.Prestamo;
import org.example.api.Modelo.Usuario;
import org.example.api.Repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class ControladorUsuario {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public ControladorUsuario(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // GET: Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    // GET: Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable int id) {
        Usuario u = this.usuarioRepository.findById(id).get();
        return ResponseEntity.ok(u);

    }


    // POST: Insertar usuario
    @PostMapping("/usuario")
    public ResponseEntity<Usuario> addUsuario(@Valid @RequestBody Usuario usuario) {
        this.usuarioRepository.save(usuario);
        return ResponseEntity.created(null).body(usuario);
    }

    // PUT: Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@Valid @RequestBody Usuario usuario, @PathVariable int id) {
        Usuario persistido = usuarioRepository.save(usuario);
        return ResponseEntity.ok().body(persistido);
    }

    // DELETE: Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable Integer id) {
        usuarioRepository.deleteById(Integer.valueOf(String.valueOf(id)));
        return ResponseEntity.ok("Usuario con ID: " + id + " eliminado");
    }
}

