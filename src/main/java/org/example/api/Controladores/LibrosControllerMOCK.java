package org.example.api.Controladores;

import jakarta.validation.Valid;
import org.example.api.Modelo.Libro;
import org.example.api.Repositorios.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/libros")
public class LibrosControllerMOCK {
    private final LibrosRepository repositorioLibros;

    @Autowired
    public LibrosControllerMOCK(LibrosRepository repositorioLibros) {
        this.repositorioLibros = repositorioLibros;
    }

    //GET --> SELECT *
    @GetMapping
    public ResponseEntity<List<Libro>> getLibro() {
        List<Libro> lista = this.repositorioLibros.findAll();
        System.out.println(lista);
        return ResponseEntity.ok(lista);
    }

    //GET BY ISBN --> SELECT BY ISBN
    @GetMapping("/{isbn}")
    @Cacheable
    public ResponseEntity<Libro> getLibroJson(@PathVariable String isbn) {
        Libro l = this.repositorioLibros.findById(isbn).get();
        return ResponseEntity.ok(l);
    }

    //POST --> INSERT
    @PostMapping("/libro")
    public ResponseEntity<Libro> addLibro(@Valid @RequestBody Libro libro) {
        System.out.println("Entra aqui");
        Libro libroPersistido = this.repositorioLibros.save(libro);
        return ResponseEntity.ok().body(libroPersistido);
    }

    //POST con Form normal, se trabajará con JSONs normalmente...
    @PostMapping(value = "/libroForm", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Libro> addLibroForm(@RequestParam String isbn,
                                              @RequestParam String titulo,
                                              @RequestParam String autor) {
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        this.repositorioLibros.save(libro);
        return ResponseEntity.created(null).body(libro);
    }

    //POST con Form normal y fichero, se trabajará con JSONs normalmente...
    @PostMapping(value = "/libroFormFichero", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Libro> addLibroFormFichero(@RequestParam String isbn,
                                                     @RequestParam String titulo,
                                                     @RequestParam String autor,
                                                     @RequestParam MultipartFile imagen) {
        //Datos básicos del libro
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAutor(autor);

        // Guardado del libro en la base de datos
        this.repositorioLibros.save(libro);

        // Devolución del objeto en formato JSON para el cliente
        return ResponseEntity.created(null).body(libro);
    }

    //PUT --> UPDATE (Actualizar libro y archivo adjunto)
   @PutMapping("/{isbn}")
    public ResponseEntity<Libro> updateLibro(@RequestBody Libro libro, @PathVariable String isbn) {
        Libro libroPersisitido = repositorioLibros.save(libro);
        return ResponseEntity.ok().body(libroPersisitido);
   }


    //DELETE
    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> deleteLibro(@PathVariable String isbn) {
        repositorioLibros.deleteById(isbn);
        String mensaje = "Libro con ISBN: " + isbn + " borrado";
        return ResponseEntity.ok().body(mensaje);
    }
}
