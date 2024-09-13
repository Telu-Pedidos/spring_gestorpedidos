package com.api.gestor_pedidos_telu.infra.Exception;

public class InvalidOrderDateException extends RuntimeException {
    public InvalidOrderDateException(String message) {
        super(message);
    }
}
