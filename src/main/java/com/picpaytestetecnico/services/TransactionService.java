package com.picpaytestetecnico.services;

import com.picpaytestetecnico.domain.transaction.Transaction;
import com.picpaytestetecnico.domain.user.Users;
import com.picpaytestetecnico.dtos.TransactionDTO;
import com.picpaytestetecnico.repositories.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private UserServices userServices;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public void createTransaction(TransactionDTO transaction) throws Exception {
        Users sender = this.userServices.findUserById(transaction.senderId());
        Users receiver = this.userServices.findUserById(transaction.receiverId());

        userServices.validateTransaction(sender, transaction.value());

        boolean isAuthorized = this.authorizeTransaction();
        if(!isAuthorized){
            throw new Exception("Transação não autorizada");
        }

        Transaction transactionDb = new Transaction();
        transactionDb.setAmount(transaction.value());
        transactionDb.setSender(sender);
        transactionDb.setReceiver(receiver);
        transactionDb.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        this.repository.save(transactionDb);
        this.userServices.saveUser(sender);
        this.userServices.saveUser(receiver);

    }

    public boolean authorizeTransaction(){
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/c7a57845-3e28-41d9-b0f7-c3d782fc2537", Map.class);
        if(authorizationResponse.getStatusCode() == HttpStatus.OK){
            String msg = (String) authorizationResponse.getBody().get("message");
            return "Authorized".equalsIgnoreCase(msg);
        }else{
            return false;
        }
    }

}
