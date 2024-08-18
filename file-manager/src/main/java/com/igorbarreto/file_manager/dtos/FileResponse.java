package com.igorbarreto.file_manager.dtos;

public record FileResponse(
          String name,
          String  url,
          String type,
          long size
) {
}
