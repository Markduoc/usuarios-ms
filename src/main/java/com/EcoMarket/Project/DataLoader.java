package com.EcoMarket.Project;

import com.EcoMarket.Project.Model.Usuario;
import com.EcoMarket.Project.Repository.UsuarioRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) {
        Faker faker = new Faker();
        Random rand = new Random();
        Set<Integer> usedRuts = new HashSet<>();

        for (int i = 0; i < 15; i++) {
            Usuario usuario = new Usuario();

            // Generar run único entre 1.000.000 y 99.999.999
            int run;
            do {
                run = 1_000_000 + rand.nextInt(98_999_999);
            } while (usedRuts.contains(run));
            usedRuts.add(run);

            usuario.setRun(run);
            usuario.setPrNombre(faker.name().firstName());
            usuario.setSeNombre(faker.name().firstName());
            usuario.setApPaterno(faker.name().lastName());
            usuario.setApMaterno(faker.name().lastName());

            usuarioRepository.save(usuario);
        }

        System.out.println("👤 Usuarios de prueba cargados (perfil dev)");
    }
}
