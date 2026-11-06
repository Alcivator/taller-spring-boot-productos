package com.example.tienda.exception;

public class ProductoNoEncontradoException extends RuntimeException {

    public ProductoNoEncontradoException(Long id) {
        super("No se encontró un producto con id " + id);
    }
}
