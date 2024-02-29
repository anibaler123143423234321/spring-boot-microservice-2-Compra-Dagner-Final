package com.dagnerchuman.springbootmicroservice2Compra.publisher;


import com.dagnerchuman.springbootmicroservice2Compra.model.Compra;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@EnableRabbit
public class Publisher {

    /**
     * The rabbit template
     */
    private final RabbitTemplate rabbitTemplate;

    /**
     * The Queue
     */
    private final Queue queue;

    public Publisher(RabbitTemplate rabbitTemplate, Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    /**
     * Envía un mensaje a la cola. Este método se puede utilizar en cualquier clase donde queramos
     * enviar un mensaje a dicha cola.
     * @param compra the generar pedido dto.
     */
    public void send(final Compra compra) {
        rabbitTemplate.convertAndSend(queue.getName(), compra);
    }

    public CompletableFuture<Void> sendAsync(final Compra compra) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                rabbitTemplate.convertAndSend(queue.getName(), compra);
                return null; // Devuelve nulo o algún resultado si es necesario
            } catch (Exception e) {
                log.error("Error al enviar la compra: " + e.getMessage());
                throw new RuntimeException("Error al enviar la compra", e);
            }
        });
    }



}