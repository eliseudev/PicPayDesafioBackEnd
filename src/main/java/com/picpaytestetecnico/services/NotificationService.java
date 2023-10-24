package com.picpaytestetecnico.services;

import com.picpaytestetecnico.domain.user.Users;
import com.picpaytestetecnico.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(Users user, String msg) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, msg);

       ResponseEntity<String> responseNotify = restTemplate.postForEntity("http://o4d9z.mocklab.io/notify", notificationRequest, String.class);

       if(!(responseNotify.getStatusCode() == HttpStatus.OK)){
           throw new Exception("Serviço de notificação está fora do ar");
       }
    }
}
