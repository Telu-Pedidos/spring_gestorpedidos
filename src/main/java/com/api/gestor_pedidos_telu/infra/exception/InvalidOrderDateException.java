package com.api.gestor_pedidos_telu.infra.exception;

public class InvalidOrderDateException extends RuntimeException {
    public InvalidOrderDateException(String message) {
        super(message);
    }
}
