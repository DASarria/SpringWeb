package co.edu.escuelaing.tls.securespring2.service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.escuelaing.tls.securespring2.model.User;
import co.edu.escuelaing.tls.securespring2.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepo;

    public UserService(UserRepository userRepo){
        this.userRepo = userRepo;
    }
    
    public boolean createUser(User user){        
        Optional<User> userSearched = userRepo.findByEmail(user.getEmail());
        if(!userSearched.isEmpty()){
            return false;
        }
        user.setPassword(hashPasswordDeterministic(user.getPassword()));
        userRepo.save(user);
        return true;

    }

    

    public List<User>getAllUsers(){
        return userRepo.findAll();
    }

    public boolean login(String email, String rawPassword) {
        if (email == null || rawPassword == null) {
            return false;
        }

        Optional<User> userSearched = userRepo.findByEmail(email);
        if (userSearched.isEmpty()) {
            return false;
        }

        String incomingHash = hashPasswordDeterministic(rawPassword);
        return incomingHash.equals(userSearched.get().getPassword());
    }

    private String hashPasswordDeterministic(String rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            return new BigInteger(1, hash).toString(10);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    }

    
}
