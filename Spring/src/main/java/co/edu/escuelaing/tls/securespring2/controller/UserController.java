package co.edu.escuelaing.tls.securespring2.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.escuelaing.tls.securespring2.model.User;
import co.edu.escuelaing.tls.securespring2.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    private UserService userService;

    public UserController( UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createUser(@RequestBody User user){
        Map<String, String> response = new HashMap<>();
        if(userService.createUser(user)){
            response.put("mensaje", "Usuario creado correctamente");
            response.put("correo", user.getEmail());
            response.put("password", user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("mensaje", "El usuario ya existe");
            response.put("correo", user.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @GetMapping
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody User user) {
        Map<String,String> response = new HashMap<>();
        if (userService.login(user.getEmail(), user.getPassword())) {
            response.put("mensaje", "Login existoso");
            response.put("correo", user.getEmail());
            return ResponseEntity.ok(response);
        }
        response.put("mensaje", "Credenciales inválidas");
        response.put("correo", user.getEmail());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    
    
}
