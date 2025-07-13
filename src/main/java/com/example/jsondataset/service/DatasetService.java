package com.example.jsondataset.service;

import com.example.jsondataset.dto.ApiResponse;
import com.example.jsondataset.model.DatasetRecord;
import com.example.jsondataset.repository.DatasetRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DatasetService {
    private final DatasetRecordRepository repository;

    public ApiResponse insert(String datasetName, String json) {
        DatasetRecord record = new DatasetRecord();
        record.setDatasetName(datasetName);
        record.setData(json);
        DatasetRecord saved = repository.save(record);
        return new ApiResponse("Record added successfully", datasetName, saved.getId());
    }

    public List<Map<String, Object>> group(String datasetName, String groupBy) {
        if (repository.countByDatasetName(datasetName) == 0)
            throw new RuntimeException("No dataset found");
        return repository.findGrouped(datasetName, groupBy);
    }

    public List<String> sort(String datasetName, String sortBy, String order, int limit, int offset, boolean isNumeric) {
        if (repository.countByDatasetName(datasetName) == 0)
            throw new RuntimeException("No dataset found");

        boolean asc = order.equalsIgnoreCase("asc");

        // Call single dynamic custom query method

        return repository.findSorted(datasetName, sortBy, String.valueOf(asc), limit, offset, isNumeric);
    }
}
