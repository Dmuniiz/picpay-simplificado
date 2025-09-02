package com.picpaysimplificado.service;

import com.picpaysimplificado.DTO.UserDTO;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT){
            throw new Exception("usuário do tipo lojista não está autorizada a realizar transação");
        }

        if(sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Saldo insuficiente");
        }
    }

    public User findUserByid(Long id) throws Exception {
        return this.userRepository.findUserById(id).get();
    }

    public void saveUser(User user){
        this.userRepository.save(user);
    }

    public User createUser(UserDTO userData) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(userData.password());
        User newUser = new User(userData, encryptedPassword);


        this.saveUser(newUser);
        return newUser;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();

    }

    public boolean deleteUser(Long id) {
        User user = userRepository.getReferenceById(id);
        if(!(userRepository.getReferenceById(id).isEnabled())){
            return false;
        }else{
           userRepository.delete(user);
           return true;
        }
    }
}
