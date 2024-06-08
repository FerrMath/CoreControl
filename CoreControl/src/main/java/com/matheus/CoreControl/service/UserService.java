package com.matheus.CoreControl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matheus.CoreControl.model.User;
import com.matheus.CoreControl.repository.UserRepo;
import com.matheus.CoreControl.util.BcryptUtil;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    private BcryptUtil bcPswEncoder = new BcryptUtil();

    // Temp encodePassword method
    public String encodePassword(String password) {
        return bcPswEncoder.encode(password);
    }

    public User saveUser(User user) {
        user.setPassword(bcPswEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public User findUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public User findUserByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    public boolean validateUser(String login, String password) {
        User user = userRepo.findByLogin(login);
        if (user != null) {
            boolean result = bcPswEncoder.matches(password, user.getPassword());
            return result;
        }
        return false;
    }
}
