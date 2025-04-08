package com.fondos.fondosApi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;

@Service
public class SNSService {

    private final AmazonSNS snsClient;

    @Value("${aws.sns.topic.arn}")
    private String topicArn;  // ARN del topic, inyectado desde el archivo de configuración

    // Constructor con inyección de dependencias para el cliente SNS
    public SNSService(AmazonSNS snsClient) {
        this.snsClient = snsClient;  // Cliente SNS inyectado por Spring
    }

    // Método para suscribir al usuario al topic SNS según su preferencia de notificación
    public void subscribeUser(String userEmail, String userPhoneNumber, String notificationMethod) {
        if ("email".equals(notificationMethod)) {
            subscribeEmail(userEmail);  // Suscripción por email
        } else if ("sms".equals(notificationMethod)) {
            subscribeSMS(userPhoneNumber);  // Suscripción por SMS
        }
    }

    // Método para suscribir un usuario por correo electrónico
    private void subscribeEmail(String email) {
        SubscribeRequest subscribeRequest = new SubscribeRequest()
            .withProtocol("email")
            .withEndpoint(email)  // El correo electrónico del usuario
            .withTopicArn(topicArn);
        
        snsClient.subscribe(subscribeRequest);
    }

    // Método para suscribir un usuario por SMS
    private void subscribeSMS(String phoneNumber) {
        SubscribeRequest subscribeRequest = new SubscribeRequest()
            .withProtocol("sms")
            .withEndpoint(phoneNumber)  // El número de teléfono del usuario
            .withTopicArn(topicArn);
        
        snsClient.subscribe(subscribeRequest);
    }

    // Método para enviar una notificación al topic
    public void sendNotification(String message) {
        PublishRequest publishRequest = new PublishRequest()
            .withMessage(message)
            .withTopicArn(topicArn);  // El ARN del topic
        
        snsClient.publish(publishRequest);
    }

}
