package com.picpaysimplificado.service;

import com.picpaysimplificado.DTO.NotificationDTO;
import com.picpaysimplificado.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

//        ResponseEntity<String> notificationApiResponse = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", notificationRequest, String.class);
//
//        if(!(notificationApiResponse.getStatusCode() == HttpStatus.OK)){
//            System.out.printf("erro ao enviar notificação");
//            throw new Exception("Serviço de notificação fora do ar");
//        }

        System.out.println("Notificação enviada para o usuário");

    }

}
