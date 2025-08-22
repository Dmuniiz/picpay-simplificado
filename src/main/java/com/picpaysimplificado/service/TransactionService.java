package com.picpaysimplificado.service;

import com.picpaysimplificado.DTO.TransactionDTO;
import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    public Transaction payload(TransactionDTO transaction) throws Exception {
        User sender = this.userService.findUserByid(transaction.senderId());
        User receiver = this.userService.findUserByid(transaction.receiverId());

        userService.validateTransaction(sender, transaction.amount());

        if(!authorizeTransaction()){
            throw new Exception("Transação não autorizada");
        }

        Transaction payload = new Transaction();
        payload.setAmount(transaction.amount());
        payload.setSender(sender);
        payload.setReceiver(receiver);
        payload.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.amount()));
        receiver.setBalance(receiver.getBalance().add(transaction.amount()));

        this.transactionRepository.save(payload);

        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transação realizada ");
        this.notificationService.sendNotification(receiver, "Transação recebida");

        return payload;

    }

    public boolean authorizeTransaction(){
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

        if(authorizationResponse.getStatusCode() == HttpStatus.FORBIDDEN){
            throw new AccessDeniedException("ACESSO NEGADO");
        }else if(authorizationResponse.getStatusCode() == HttpStatus.OK){
            String message = (String) authorizationResponse.getBody().get("status");
            return message.equalsIgnoreCase("success");
        }
        return false;
    }

}
