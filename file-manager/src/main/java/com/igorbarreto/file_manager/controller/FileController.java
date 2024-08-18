package com.igorbarreto.file_manager.controller;

import com.igorbarreto.file_manager.dtos.FileResponse;
import com.igorbarreto.file_manager.dtos.MessageResponse;
import com.igorbarreto.file_manager.entity.FileEntity;
import com.igorbarreto.file_manager.service.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
@CrossOrigin("http://localhost:8001")
public class FileController {

    private static final ZoneId BRAZIL_TIMEZONE = ZoneId.of("America/Sao_Paulo");

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadFile(@RequestParam("file")MultipartFile file) throws IOException {

        fileService.store(file);
        String message = "Uploaded the file successfully: " + file.getOriginalFilename();

        final MessageResponse messageResponse =
                new MessageResponse(LocalDateTime.now(BRAZIL_TIMEZONE).toString(),
                        HttpStatus.CREATED.value(), message, file.getOriginalFilename(), file.getContentType());

        return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
    }


    @GetMapping
    public ResponseEntity<List<FileResponse>> getListFiles() {

        var files = fileService.getAllFiles().map(fileFromDatabase -> {

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
                    .path(String.valueOf(fileFromDatabase.getId())).toUriString();

            return new FileResponse(fileFromDatabase.getName(), fileDownloadUri,
                    fileFromDatabase.getContentType(), fileFromDatabase.getData().length);

        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) throws FileNotFoundException {

        FileEntity fileFromDatabase = fileService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileFromDatabase.getName() + "\"")
                .body(fileFromDatabase.getData());
    }


}
