package com.dagnerchuman.springbootmicroservice2Compra.consumer;

import com.dagnerchuman.springbootmicroservice2Compra.model.Compra;
import com.dagnerchuman.springbootmicroservice2Compra.repository.CompraRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class Consumer {

    @Autowired
    private CompraRepository compraRepository;

    @RabbitListener(queues = {"${ecommerce.queue.name}"})
    public void receive(@Payload Compra compra) {
        try {
            log.info("Received Objeto: " + compra);
            makeSlow();

            // Resto de la lógica de procesamiento
            compra.setFechaCompra(LocalDateTime.now());
            compraRepository.save(compra);

        } catch (Exception e) {
            log.error("Error al procesar el mensaje: " + e.getMessage());
        }
    }

    private void makeSlow() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            log.error("Error al hacer lento: " + e.getMessage());
        }
    }
}
