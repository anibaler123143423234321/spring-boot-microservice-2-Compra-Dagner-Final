package com.dagnerchuman.springbootmicroservice2Compra.service;

import com.dagnerchuman.springbootmicroservice2Compra.model.Compra;
import com.dagnerchuman.springbootmicroservice2Compra.repository.CompraRepository;
import lombok.extern.slf4j.Slf4j;
import com.dagnerchuman.springbootmicroservice2Compra.publisher.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@Transactional
public class CompraServiceImpl implements CompraService {

    @Autowired
    private CompraRepository compraRepository;
    private final Publisher  publisher;  // Reemplaza Object con el tipo correcto

    public CompraServiceImpl(CompraRepository compraRepository, Publisher publisher) {
        this.compraRepository = compraRepository;
        this.publisher = publisher;
    }

    @Override
    public Compra saveCompra(Compra compra) {
        try {
            // Publicar el mensaje y esperar a que la publicación se complete
            CompletableFuture<Void> publishFuture = publisher.sendAsync(compra);
            publishFuture.join();

            return compra;

        } catch (Exception e) {
            log.error("Error al guardar la compra: " + e.getMessage());
            return null;
        }
    }


    @Override
    public List<Compra> findAllComprasOfUser(Long userId)
    {

        return compraRepository.findAllByUserId(userId);
    }


    @Override
    public Compra updateCompra(Long id, Compra compra) {
        Optional<Compra> existingCompra = compraRepository.findById(id);

        if (existingCompra.isPresent()) {
            Compra updatedCompra = existingCompra.get();
            updatedCompra.setEstadoCompra(compra.getEstadoCompra());
            return compraRepository.save(updatedCompra);
        } else {
            return null; // La compra no existe
        }
    }

    @Override
    public List<Compra> findAllCompras() {
        return compraRepository.findAll();
    }
}
