package com.EcoMarket.Project.Controller;

import com.EcoMarket.Project.Model.Usuario;
import com.EcoMarket.Project.Service.UsuarioService;
import com.EcoMarket.Project.Assembler.UsuarioModelAssembler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioControllerTest {

    private UsuarioService usuarioService;
    private UsuarioModelAssembler assembler;
    private UsuarioControllerV2 controller;

    @BeforeEach
    void setUp() {
        usuarioService = mock(UsuarioService.class);
        assembler = mock(UsuarioModelAssembler.class);

        controller = new UsuarioControllerV2();
        controller.setUsuarioService(usuarioService);
        controller.setAssembler(assembler);
    }

    @Test
    void testListarConUsuarios() {
        Usuario u1 = new Usuario();
        Usuario u2 = new Usuario();
        when(usuarioService.findAll()).thenReturn(List.of(u1, u2));
        when(assembler.toModel(any())).thenReturn(EntityModel.of(new Usuario()));

        var respuesta = controller.Listar();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(2, respuesta.getBody().getContent().size());
    }

    @Test
    void testListarSinUsuarios() {
        when(usuarioService.findAll()).thenReturn(List.of());

        var respuesta = controller.Listar();

        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }

    @Test
    void testCrearUsuario() {
        Usuario nuevo = new Usuario();
        nuevo.setId(1L);

        when(usuarioService.save(any(Usuario.class))).thenReturn(nuevo);
        when(assembler.toModel(nuevo)).thenReturn(EntityModel.of(nuevo));

        ResponseEntity<EntityModel<Usuario>> respuesta = controller.crearUsuario(nuevo);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(1L, respuesta.getBody().getContent().getId());
    }

    @Test
    void testEliminarExistente() {
        when(usuarioService.delete(1L)).thenReturn(true);

        var respuesta = controller.eliminar(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Usuario eliminado correctamente", respuesta.getBody());
    }

    @Test
    void testEliminarNoExistente() {
        when(usuarioService.delete(1L)).thenReturn(false);

        var respuesta = controller.eliminar(1L);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void testActualizarExistente() {
        Usuario original = new Usuario();
        original.setId(1L);

        when(usuarioService.update(eq(1L), any(Usuario.class))).thenReturn(Optional.of(original));
        when(assembler.toModel(original)).thenReturn(EntityModel.of(original));

        var respuesta = controller.actualizar(1L, original);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1L, respuesta.getBody().getContent().getId());
    }

    @Test
    void testActualizarNoExistente() {
        when(usuarioService.update(eq(1L), any(Usuario.class))).thenReturn(Optional.empty());

        var respuesta = controller.actualizar(1L, new Usuario());

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void testBuscarPorIdExistente() {
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        when(usuarioService.findById(2L)).thenReturn(Optional.of(usuario));

        var respuesta = controller.buscarPorId(2L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(2L, respuesta.getBody().getId());
    }

    @Test
    void testBuscarPorIdNoExistente() {
        when(usuarioService.findById(999L)).thenReturn(Optional.empty());

        var respuesta = controller.buscarPorId(999L);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }
}
