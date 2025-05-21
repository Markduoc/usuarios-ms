package com.EcoMarket.Project.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EcoMarket.Project.Model.Usuario;
import com.EcoMarket.Project.Repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id){
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public boolean delete(Long id){
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        } else{
            return false;
        }
    }

    public boolean update(Long id, Usuario usuario){
            Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
            if (usuarioExistente.isPresent()){
                Usuario u = usuarioExistente.get();
                u.setRun(usuario.getRun());
                u.setPrNombre(usuario.getPrNombre());
                u.setSeNombre(usuario.getSeNombre());
                u.setApPaterno(usuario.getApPaterno());
                u.setApMaterno(usuario.getApMaterno());
                usuarioRepository.save(u);
                return true;
            }
            return false;
    }
}
