package com.example.JEE.services;

import com.example.JEE.entities.Tables;
import com.example.JEE.repositories.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TableService {
    @Autowired
    private TableRepository tableRepository;

    public Tables createTable(Tables tables) {
        return tableRepository.save(tables);
    }

    public Optional<Tables> getTableById(int id) {
        return tableRepository.findById(id);
    }

    public List<Tables> getAllTables() {
        return tableRepository.findAll();
    }

    public void deleteTable(int id) {
        tableRepository.deleteById(id);
    }
}
