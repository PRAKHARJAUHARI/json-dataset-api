package com.example.jsondataset.service;

import com.example.jsondataset.dto.ApiResponse;
import com.example.jsondataset.model.DatasetRecord;
import com.example.jsondataset.repository.DatasetRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatasetServiceTest {

    @Mock
    private DatasetRecordRepository repository;

    @InjectMocks
    private DatasetService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void insert_ShouldSaveRecord() {
        String jsonData = "{\"name\":\"John\"}";
        DatasetRecord savedRecord = new DatasetRecord(1L, "test", jsonData);

        when(repository.save(any(DatasetRecord.class))).thenReturn(savedRecord);

        ApiResponse response = service.insert("test", jsonData);

        assertEquals("Record added successfully", response.getMessage());
        assertEquals("test", response.getDataset());
        assertEquals(1L, response.getRecordId());
        verify(repository).save(any(DatasetRecord.class));
    }

    @Test
    void group_ShouldThrow_WhenNoRecords() {
        when(repository.countByDatasetName("empty")).thenReturn(0L);
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.group("empty", "dept"));
        assertEquals("No dataset found", thrown.getMessage());
    }

    @Test
    void sort_ShouldReturnSortedList() {
        // Arrange
        when(repository.countByDatasetName("test")).thenReturn(1L);
        when(repository.findSorted("test", "age", "desc", 2, 0, true))
                .thenReturn(List.of("{\"name\":\"A\"}", "{\"name\":\"B\"}"));

        // Act
        List<String> result = service.sort("test", "age", "desc", 2, 0, true);

        // Assert
        assertEquals(2, result.size());
        assertEquals("{\"name\":\"A\"}", result.get(0));
        assertEquals("{\"name\":\"B\"}", result.get(1));
        verify(repository).findSorted("test", "age", "desc", 2, 0, true);
    }

}
