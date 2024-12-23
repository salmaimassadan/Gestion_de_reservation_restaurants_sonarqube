package com.example.JEE.services;

import com.example.JEE.entities.FileData;
import com.example.JEE.repositories.FileDataRepository;
import com.example.JEE.repositories.StorageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StorageServiceTest {

    @Mock
    private StorageRepository storageRepository;

    @Mock
    private FileDataRepository fileDataRepository;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private StorageService storageService;

    private final String FOLDER_PATH = "C:/5iir/ProjectJEE/restaurant-frontend/src/assets/images/";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void uploadImageToFileSystem() throws IOException {
        // Arrange
        String fileName = "testImage.jpg";
        String filePath = FOLDER_PATH + fileName;
        String fileType = "image/jpeg";

        FileData mockFileData = FileData.builder()
                .name(fileName)
                .type(fileType)
                .filePath(filePath)
                .build();

        when(multipartFile.getOriginalFilename()).thenReturn(fileName);
        when(multipartFile.getContentType()).thenReturn(fileType);
        when(fileDataRepository.save(any(FileData.class))).thenReturn(mockFileData);

        // Act
        String result = storageService.uploadImageToFileSystem(multipartFile);

        // Assert
        assertNotNull(result);
        assertTrue(result.contains(fileName));
        verify(multipartFile, times(1)).transferTo(any(File.class));
        verify(fileDataRepository, times(1)).save(any(FileData.class));
    }


    @Test
    void downloadImageFromFileSystem_FileNotFound() {
        // Arrange
        String fileName = "nonExistentImage.jpg";
        when(fileDataRepository.findFirstByName(fileName)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IOException.class, () -> storageService.downloadImageFromFileSystem(fileName));
        verify(fileDataRepository, times(1)).findFirstByName(fileName);
    }
}
