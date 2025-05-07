package org.example.init.domain;


import org.example.init.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserAccountService {
    @Autowired
    UserAccountRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserAccountRepository userAccountRepository;

    public List<UserAccount> list() {
        return repository.findAll();
    }

    public void save(UserAccount user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }
    public UserAccount addUser(UserAccount usuario) {
        return userAccountRepository.save(usuario);
    }
    public Optional<UserAccount> findById(Long id) {
        return userAccountRepository.findById(id);
    }

    public boolean setLimit(Long userId, RestriccionEmpresa limitDTO) {
        Optional<UserAccount> optionalUser = userAccountRepository.findById(userId);
        if (optionalUser.isPresent()) {
            UserAccount user = optionalUser.get();

            // Si ya tenía una restricción, puedes actualizarla aquí (opcional)
            RestriccionEmpresa nuevaRestriccion = new RestriccionEmpresa();
            nuevaRestriccion.setTipoModelo(limitDTO.getTipoModelo());
            nuevaRestriccion.setLimiteMensualTokens(limitDTO.getLimiteMensualTokens());
            nuevaRestriccion.setLimiteDiarioConsultas(limitDTO.getLimiteDiarioConsultas());

            // Si limitDTO.empresa no viene, puedes asignar manualmente una empresa:
            nuevaRestriccion.setEmpresa(limitDTO.getEmpresa());

            user.setRestriccion(nuevaRestriccion);
            userAccountRepository.save(user);  // Guarda usuario + restricción

            return true;
        }
        return false;
    }
    public UserAccount updateUser(UserAccount usuario) {
        Optional<UserAccount> optional = userAccountRepository.findById(usuario.getId());

        if (optional.isEmpty()) {
            throw new NoSuchElementException("Usuario con ID " + usuario.getId() + " no encontrado.");
        }

        UserAccount existente = optional.get();
        existente.setFirstName(usuario.getFirstName());
        existente.setLastName(usuario.getLastName());
        existente.setEmail(usuario.getEmail());
        // ⚠️ Solo actualiza la contraseña si viene una nueva (opcional):
        if (usuario.getPassword() != null && !usuario.getPassword().isBlank()) {
            existente.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        // Actualiza otras propiedades si las tienes...

        return userAccountRepository.save(existente);
    }


}
