package com.EcoMarket.Project.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.EcoMarket.Project.Controller.UsuarioControllerV2;
import com.EcoMarket.Project.Model.Usuario;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario){
        return EntityModel.of(usuario,
        linkTo(methodOn(UsuarioControllerV2.class).buscarPorId(usuario.getId())).withSelfRel(),
        linkTo(methodOn(UsuarioControllerV2.class).Listar()).withRel("usuarios"),
        linkTo(methodOn(UsuarioControllerV2.class).actualizar(usuario.getId(), usuario)).withRel("actualizar"));
    }

}
