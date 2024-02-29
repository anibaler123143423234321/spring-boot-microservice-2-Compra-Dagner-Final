package com.dagnerchuman.springbootmicroservice2Compra.publisher;

        import lombok.extern.slf4j.Slf4j;
        import org.springframework.amqp.core.Queue;
        import org.springframework.amqp.rabbit.core.RabbitTemplate;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class PublisherConfig {

    /**
     * Nombre de la cola a la cual apuntamos en el application.propiertes
     */
    @Value("${ecommerce.queue.name}")
    private String message;

    @Bean
    public Queue queue() {
        return new Queue(message, true, false, false);
    }


    /**
     * Elimina todos los mensajes de la cola al iniciar la aplicación.
     *
     * @param rabbitTemplate the rabbit template.
     * @return boolean
     */
    @Bean
    public Boolean clearQueueOnStartup(RabbitTemplate rabbitTemplate) {
        while (true) {
            log.info("Se van a eliminar los mensajes de la: " + message);
            Object messages = rabbitTemplate.receiveAndConvert(message);
            if (messages == null) {
                log.info("La cola está vacía");
                break;
            }
        }
        return true;
    }
}