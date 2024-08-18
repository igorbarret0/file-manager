package com.igorbarreto.file_manager.service;

import com.igorbarreto.file_manager.entity.FileEntity;
import com.igorbarreto.file_manager.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileService {

    private FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileEntity store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(fileName);
        fileEntity.setContentType(file.getContentType());
        fileEntity.setSize(file.getSize());
        fileEntity.setData(file.getBytes());

        return fileRepository.save(fileEntity);
    }

    public FileEntity getFile(Long id) throws FileNotFoundException {

        return fileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("File not found with id: " + id));
    }

    public Stream<FileEntity> getAllFiles() {

        return fileRepository.findAll().stream();
    }

}
