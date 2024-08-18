package com.igorbarreto.file_manager.dtos;

public record MessageResponse(
        String timeStamp,
        Integer status,
        String message,
        String path,
        String method
) {
}
