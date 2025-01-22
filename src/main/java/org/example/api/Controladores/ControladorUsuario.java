package org.example.api.Controladores;

import jakarta.validation.Valid;
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
    public ResponseEntity<Usuario> getUsuario(@PathVariable Integer id) {
        return usuarioRepository.findById(String.valueOf(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: Insertar usuario
    @PostMapping("/usuario")
    public ResponseEntity<Usuario> addUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario usuarioPersistido = usuarioRepository.save(usuario);
        return ResponseEntity.ok().body(usuarioPersistido);
    }

    // PUT: Actualizar usuario con imagen de perfil opcional
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Integer id,
                                                 @RequestParam String nombre,
                                                 @RequestParam String email,
                                                 @RequestParam(required = false) MultipartFile imagen) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(String.valueOf(id));
        if (usuarioExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuarioActualizado = usuarioExistente.get();
        usuarioActualizado.setNombre(nombre);
        usuarioActualizado.setEmail(email);

        // Guardar imagen si se proporciona
        if (imagen != null && !imagen.isEmpty()) {
            String rutaDirectorio = "uploads/usuarios/";
            File directorio = new File(rutaDirectorio);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            String filePath = rutaDirectorio + imagen.getOriginalFilename();
            File destino = new File(filePath);
            try {
                imagen.transferTo(destino);
            } catch (IOException e) {
                return ResponseEntity.internalServerError().build();
            }
        }

        usuarioRepository.save(usuarioActualizado);
        return ResponseEntity.ok(usuarioActualizado);
    }

    // DELETE: Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable Integer id) {
        usuarioRepository.deleteById(String.valueOf(id));
        return ResponseEntity.ok("Usuario con ID: " + id + " eliminado");
    }
}

