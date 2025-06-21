package com.EcoMarket.Project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EcoMarket.Project.Model.Usuario;
import com.EcoMarket.Project.Service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Optional;




//VERSION SIN UTILIZAR
@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuario", description = "Gestion de usuarios")    
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Operation(
        summary = "Listar los usuarios registrados",
        description = "Devuelve todos los registros de usuario"
    )
    @ApiResponse(
    responseCode = "200",
    description = "Lista de usuarios",
    content = @Content(
      mediaType = "application/json",
      examples = @ExampleObject(value = """
        [
         {
          "id": 1,
            "run": 219040636,
           "prNombre": "Marco",
           "seNombre": "Agustin",
           "apPaterno": "Corrales",
           "apMaterno": "Ahumada"
          }

          {
           "id": 2,
            "run": 219040635,
            "prNombre": "Mateo",
            "seNombre": "Andres",
            "apPaterno": "Godoy",
            "apMaterno": "Bobadilla"
          }
        ]
      """)
    )
  )

    @GetMapping
    public ResponseEntity<List<Usuario>> Listar(){
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    } 

    @Operation(
        summary = "Creacion de usuario",
        description = "Permite la creacion de un nuevo usuario dentro de la bd"
    )
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Se crea el usuario"),
        @ApiResponse(responseCode = "400", description = "Se ingresaron datos no validos")
    })
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario){
        Usuario nuevoUsuario = usuarioService.save(usuario);
        return ResponseEntity.ok(nuevoUsuario);  
    }
    
    @Operation(
        summary = "Eliminar usuario",
        description = "Permite eliminar un usuario de la bd utilizando como parametro su id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se ha eliminado el usuario"),
        @ApiResponse(responseCode = "400", description = "Los datos ingresados fueron incorrectos"),
        @ApiResponse(responseCode = "404", description = "El usuario que se solicito eliminar no fue encontrado")
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id){
        boolean eliminado = usuarioService.delete(id);
        if (eliminado) {
                return ResponseEntity.ok("Usuario eliminado correctamente");
            
        } else{
            return ResponseEntity.status(404).body("Usuario no fue eliminado");
        }
    }
    
    
    @ApiResponse(
    responseCode = "200",
    description = "Busqueda de usuario en base a su id",
    content = @Content(
      mediaType = "application/json",
      examples = @ExampleObject(value = """
        [
         {
            "id": 2,
            "run": 219040636,
           "prNombre": "Marco",
           "seNombre": "Agustin",
           "apPaterno": "Corrales",
           "apMaterno": "Ahumada"
          }
        ]
      """)
    )
  )
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioService.findById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else{
            return ResponseEntity.status(404).build();
        }
    }
}
