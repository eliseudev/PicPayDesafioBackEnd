package com.picpaytestetecnico.controller;

import com.picpaytestetecnico.domain.user.Users;
import com.picpaytestetecnico.dtos.UserDTO;
import com.picpaytestetecnico.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServices userServices;

    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody UserDTO user){
        Users newUSer = userServices.createUser(user);
        return new ResponseEntity<>(newUSer, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers(){
        List<Users> users = userServices.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
