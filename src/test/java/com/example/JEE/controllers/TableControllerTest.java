package com.example.JEE.controllers;

import com.example.JEE.entities.Tables;
import com.example.JEE.services.TableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TableControllerTest {

    @InjectMocks
    private TableController tableController;

    @Mock
    private TableService tableService;

    private Tables mockTable;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockTable = new Tables(1, 4, null, true);
    }

    @Test
    void createTable() {
        when(tableService.createTable(mockTable)).thenReturn(mockTable);

        Tables createdTable = tableController.createTable(mockTable);

        assertEquals(mockTable, createdTable);
        verify(tableService, times(1)).createTable(mockTable);
    }

    @Test
    void getTableById() {
        when(tableService.getTableById(1)).thenReturn(Optional.of(mockTable));

        Optional<Tables> retrievedTable = tableController.getTableById(1);

        assertEquals(Optional.of(mockTable), retrievedTable);
        verify(tableService, times(1)).getTableById(1);
    }

    @Test
    void getAllTables() {
        List<Tables> tables = Arrays.asList(
                mockTable,
                new Tables(2, 6, null, true)
        );
        when(tableService.getAllTables()).thenReturn(tables);

        List<Tables> retrievedTables = tableController.getAllTables();

        assertEquals(tables, retrievedTables);
        verify(tableService, times(1)).getAllTables();
    }

    @Test
    void deleteTable() {
        doNothing().when(tableService).deleteTable(1);

        tableController.deleteTable(1);

        verify(tableService, times(1)).deleteTable(1);
    }
}
