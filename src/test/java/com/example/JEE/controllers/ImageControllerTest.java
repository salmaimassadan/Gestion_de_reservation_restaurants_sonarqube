package com.example.JEE.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ImageController imageController;

    private final String FOLDER_PATH = "C:/5iir/ProjectJEE/restaurant-frontend/src/assets/images/";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        imageController = new ImageController(); // Explicit initialization
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    void getImage_FileExists_ReturnsImage() throws Exception {
        // Arrange
        String fileName = "testImage.jpg";
        Path testFilePath = Paths.get(FOLDER_PATH, fileName);
        Files.createDirectories(testFilePath.getParent());
        Files.createFile(testFilePath);

        // Act & Assert
        mockMvc.perform(get("/images/{fileName}", fileName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG));

        // Clean up
        Files.deleteIfExists(testFilePath);
    }

    @Test
    void getImage_FileDoesNotExist_ReturnsNotFound() throws Exception {
        // Arrange
        String fileName = "nonExistentImage.jpg";

        // Act & Assert
        mockMvc.perform(get("/images/{fileName}", fileName))
                .andExpect(status().isNotFound());
    }
}
