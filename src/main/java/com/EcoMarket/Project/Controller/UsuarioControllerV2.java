package com.EcoMarket.Project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EcoMarket.Project.Assembler.UsuarioModelAssembler;
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
import org.springframework.web.bind.annotation.PutMapping;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@RequestMapping("/api/v2/usuarios")
@Tag(name = "Usuario", description = "Gestion de usuarios")    
public class UsuarioControllerV2 {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired 
    private UsuarioModelAssembler assembler;

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

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> Listar(){
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<EntityModel<Usuario>> usuariosModel = usuarios.stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());
        CollectionModel<EntityModel<Usuario>> collectionModel =
        CollectionModel.of(usuariosModel,
        linkTo(methodOn(UsuarioControllerV2.class).Listar()).withSelfRel());
        return ResponseEntity.ok(collectionModel);
    }


    @Operation(
        summary = "Creacion de usuario",
        description = "Permite la creacion de un nuevo usuario dentro de la bd"
    )
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Se crea el usuario"),
        @ApiResponse(responseCode = "400", description = "Se ingresaron datos no validos")
    })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> crearUsuario(@RequestBody Usuario usuario){
        Usuario nuevoUsuario = usuarioService.save(usuario);
        return ResponseEntity
        .created(linkTo(methodOn(UsuarioControllerV2.class).buscarPorId(nuevoUsuario.getId())).toUri())
        .body(assembler.toModel(nuevoUsuario));  
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

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        boolean eliminado = usuarioService.delete(id);
        if (eliminado) {
                return ResponseEntity
                .ok("Usuario eliminado correctamente");
        } else{
            return ResponseEntity
            .status(404).body("Usuario no fue eliminado");
        }
    }
    
    @Operation(
        summary = "Actualizar datos de usuario",
        description = "Permite actualizar los datos de un usuario en la bd a traves de su id y la solicitud de un cuerpo con los datos actualizados"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se ha modificado el usuario"),
        @ApiResponse(responseCode = "400", description = "Los datos ingresados fueron incorrectos"),
        @ApiResponse(responseCode = "404", description = "El usuario que se solicito actualizar no fue encontrado")
    })
    
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> actualizar(@PathVariable Long id, @RequestBody Usuario usuario){
        Optional<Usuario> actualizado = usuarioService.update(id, usuario);
        if (actualizado.isPresent()){
            Usuario Usuario2 = actualizado.get();
            EntityModel<Usuario> resource = EntityModel.of(Usuario2,
            linkTo(methodOn(UsuarioControllerV2.class).actualizar(id, usuario)).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).buscarPorId(id)).withRel("mensaje"),
                linkTo(methodOn(UsuarioControllerV2.class).Listar()).withRel("mensajes"),
                linkTo(methodOn(UsuarioControllerV2.class).eliminar(id)).withRel("eliminar"));
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.status(404).body(null);
    }
    @Operation(
        summary = "Busqueda de usuario",
        description = "Permite la busqueda de usuarios en base a su id"
    )
    
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
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioService.findById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else{
            return ResponseEntity.status(404).build();
        }
    }
}
