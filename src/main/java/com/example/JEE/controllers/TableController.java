package com.example.JEE.controllers;

import com.example.JEE.entities.Tables;
import com.example.JEE.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tables")
public class TableController {
    @Autowired
    private TableService tableService;

    @PostMapping
    public Tables createTable(@RequestBody Tables tables) {
        return tableService.createTable(tables);
    }

    @GetMapping("/{id}")
    public Optional<Tables> getTableById(@PathVariable int id) {
        return tableService.getTableById(id);
    }

    @GetMapping
    public List<Tables> getAllTables() {
        return tableService.getAllTables();
    }

    @DeleteMapping("/{id}")
    public void deleteTable(@PathVariable int id) {
        tableService.deleteTable(id);
    }
}

