package com.dagnerchuman.springbootmicroservice2Compra.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
@Entity
@Table(name ="compra")
public class Compra implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "precio", nullable = false)
    private Double precioCompra;

    @Column(name = "fecha_compra", nullable = false)
    private LocalDateTime fechaCompra;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "estado_compra", nullable = false)
    private String estadoCompra = "Pendiente Por Revisar";

    @Column(name = "tipoEnvio", nullable = false)
    private String tipoEnvio;

    @Column(name = "TipoDePago", nullable = false)
    private String TipoDePago;

    @Column(name = "codigo", nullable = false, unique = true, length = 8)
    private String codigo;

    @Column(name = "cargo_delivery", nullable = true)
    private Double cargoDelivery = 2.0; // Valor predeterminado de 2.0 soles

    public Compra() {
        this.codigo = generarCodigo();
    }

    private String generarCodigo() {
        // Genera un identificador único (UUID)
        UUID uuid = UUID.randomUUID();

        // Convierte el UUID a su representación hexadecimal
        String hexadecimal = uuid.toString().replace("-", "");

        // Toma los primeros 8 caracteres del identificador hexadecimal
        return hexadecimal.substring(0, 8);
    }

}
