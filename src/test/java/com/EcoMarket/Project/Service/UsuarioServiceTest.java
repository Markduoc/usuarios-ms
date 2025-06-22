package com.EcoMarket.Project.Service;

import com.EcoMarket.Project.Model.Usuario;
import com.EcoMarket.Project.Repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioService = new UsuarioService();

        // Inyección del mock usando reflexión (porque el campo es privado sin setter)
        var field = UsuarioService.class.getDeclaredFields();
        for (var f : field) {
            if (f.getName().equals("usuarioRepository")) {
                f.setAccessible(true);
                try {
                    f.set(usuarioService, usuarioRepository);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Test
    void testFindAll() {
        Usuario u1 = new Usuario();
        Usuario u2 = new Usuario();
        when(usuarioRepository.findAll()).thenReturn(List.of(u1, u2));

        List<Usuario> result = usuarioService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void testFindByIdExistente() {
        Usuario u = new Usuario();
        u.setId(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(u));

        Optional<Usuario> result = usuarioService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindByIdNoExistente() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Usuario> result = usuarioService.findById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void testSave() {
        Usuario u = new Usuario();
        u.setPrNombre("Marco");

        when(usuarioRepository.save(u)).thenReturn(u);

        Usuario saved = usuarioService.save(u);

        assertEquals("Marco", saved.getPrNombre());
    }

    @Test
    void testDeleteExistente() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        boolean result = usuarioService.delete(1L);

        assertTrue(result);
        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void testDeleteNoExistente() {
        when(usuarioRepository.existsById(99L)).thenReturn(false);

        boolean result = usuarioService.delete(99L);

        assertFalse(result);
        verify(usuarioRepository, never()).deleteById(99L);
    }

    @Test
    void testUpdateExistente() {
        Usuario existente = new Usuario();
        existente.setId(1L);
        existente.setPrNombre("NombreAntiguo");

        Usuario nuevo = new Usuario();
        nuevo.setPrNombre("NombreNuevo");
        nuevo.setRun(12345678);
        nuevo.setSeNombre("Segundo");
        nuevo.setApPaterno("Paterno");
        nuevo.setApMaterno("Materno");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(existente);

        Optional<Usuario> result = usuarioService.update(1L, nuevo);

        assertTrue(result.isPresent());
        assertEquals("NombreNuevo", result.get().getPrNombre());
    }

    @Test
    void testUpdateNoExistente() {
        Usuario nuevo = new Usuario();
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Usuario> result = usuarioService.update(99L, nuevo);

        assertFalse(result.isPresent());
    }
}
