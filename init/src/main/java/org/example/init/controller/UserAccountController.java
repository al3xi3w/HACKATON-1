package org.example.init.controller;


import org.example.init.domain.RestriccionEmpresa;
import org.example.init.domain.UserAccount;
import org.example.init.domain.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/company/users")
public class UserAccountController {
    @Autowired
    UserAccountService service;
    @Autowired
    private UserAccountService userAccountService;

    @PostMapping
    public ResponseEntity<UserAccount> addUser(@RequestBody UserAccount usuario) {
        return ResponseEntity.ok(userAccountService.addUser(usuario));
    }

    @GetMapping
    public ResponseEntity<List<UserAccount>> getAllUsers() {
        List<UserAccount> usuarios = userAccountService.list();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAccount> getUser(@PathVariable Long id) {
        Optional<UserAccount> usuario = userAccountService.findById(id);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserAccount> updateUser(@PathVariable Long id, @RequestBody UserAccount usuario) {
        usuario.setId(id);
        UserAccount updatedUser = userAccountService.updateUser(usuario);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/{id}/limits")
    public ResponseEntity<String> setUserLimit(@PathVariable Long id, @RequestBody RestriccionEmpresa limitDTO) {
        boolean result = userAccountService.setLimit(id, limitDTO);
        if (result) {
            return ResponseEntity.ok("Límite actualizado correctamente.");
        } else {
            return ResponseEntity.badRequest().body("No se pudo actualizar el límite.");
        }
    }
}

//falta añadir función seLimit en service
//service updateUser función :)
//archivo limitDto
