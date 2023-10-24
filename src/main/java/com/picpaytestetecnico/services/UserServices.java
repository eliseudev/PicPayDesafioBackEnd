package com.picpaytestetecnico.services;

import com.picpaytestetecnico.domain.user.UserType;
import com.picpaytestetecnico.domain.user.Users;
import com.picpaytestetecnico.dtos.UserDTO;
import com.picpaytestetecnico.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserServices {
    @Autowired
    private UserRepository repository;

    public void validateTransaction(Users sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() != UserType.MERCHANT){
            throw new Exception("Usuário do tipo logista não está autorizado a realizar transsação");
        }

        if(sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Saldo insuficiente.");
        }
    }

    public Users findUserById(Long id) throws Exception {
        return this.repository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
    }

    public Users createUser(UserDTO data){
        Users newUser = new Users(data);
        this.saveUser(newUser);
        return newUser;
    }

    public List<Users> getAllUsers(){
        return this.repository.findAll();
    }

    public void saveUser(Users user){
        this.repository.save(user);
    }

}
