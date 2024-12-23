package com.example.JEE.services;

import com.example.JEE.entities.Tables;
import com.example.JEE.repositories.TableRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TableServiceTest {
    @Mock
    private TableRepository tableRepository;

    @InjectMocks
    private TableService tableService;

    public TableServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTable() {
        // Arrange
        Tables table = new Tables(1, 4, null, true);
        when(tableRepository.save(table)).thenReturn(table);

        // Act
        Tables createdTable = tableService.createTable(table);

        // Assert
        assertNotNull(createdTable);
        assertEquals(4, createdTable.getCapacity());
        verify(tableRepository, times(1)).save(table);
    }

    @Test
    void getTableById() {
        // Arrange
        Tables table = new Tables(1, 4, null, true);
        when(tableRepository.findById(1)).thenReturn(Optional.of(table));

        // Act
        Optional<Tables> foundTable = tableService.getTableById(1);

        // Assert
        assertTrue(foundTable.isPresent());
        assertEquals(1, foundTable.get().getTableID());
        verify(tableRepository, times(1)).findById(1);
    }

    @Test
    void getAllTables() {
        // Arrange
        Tables table1 = new Tables(1, 4, null, true);
        Tables table2 = new Tables(2, 6, null, false);
        when(tableRepository.findAll()).thenReturn(Arrays.asList(table1, table2));

        // Act
        List<Tables> tables = tableService.getAllTables();

        // Assert
        assertNotNull(tables);
        assertEquals(2, tables.size());
        assertEquals(4, tables.get(0).getCapacity());
        assertEquals(6, tables.get(1).getCapacity());
        verify(tableRepository, times(1)).findAll();
    }

    @Test
    void deleteTable() {
        // Arrange
        int tableId = 1;
        doNothing().when(tableRepository).deleteById(tableId);
        when(tableRepository.existsById(tableId)).thenReturn(true);

        // Act
        tableService.deleteTable(tableId);

        // Assert
        verify(tableRepository, times(1)).deleteById(tableId);
    }
}
