package com.amazon.demo.service;

import com.amazon.demo.model.User;
import com.amazon.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(String username, String email, String password) {
        User newUser = new User(username, email, password);
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);

        userRepository.save(newUser);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean resetPassword(String username, String oldPassword, String newPassword) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();


            if (user.getPassword().equals(oldPassword)) {

                user.setPassword(newPassword);
                userRepository.save(user);
                return true;
            }
        }

        return false;
    }
}
