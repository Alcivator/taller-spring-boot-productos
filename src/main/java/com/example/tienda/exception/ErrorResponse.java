package com.example.tienda.exception;

import java.time.LocalDateTime;

public record ErrorResponse(int estado, String error, String mensaje, LocalDateTime fecha) {
}
