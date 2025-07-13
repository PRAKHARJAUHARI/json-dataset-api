package com.example.jsondataset.controller;

import com.example.jsondataset.dto.ApiResponse;
import com.example.jsondataset.service.DatasetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dataset")
@RequiredArgsConstructor
public class DatasetController {
    private final DatasetService datasetService;

    @PostMapping("/{datasetName}/record")
    public ResponseEntity<ApiResponse> insert(
            @PathVariable String datasetName,
            @RequestBody String record) {
        return ResponseEntity.ok(datasetService.insert(datasetName, record));
    }

    @GetMapping("/{datasetName}/query")
    public ResponseEntity<Object> query(
            @PathVariable String datasetName,
            @RequestParam(required = false) String groupBy,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "false") boolean isNumeric,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "100") int limit

    ) {
        if (groupBy != null) {
            return ResponseEntity.ok(datasetService.group(datasetName, groupBy));
        } else if (sortBy != null) {
            return ResponseEntity.ok(datasetService.sort(datasetName, sortBy, order, limit, offset, isNumeric));
        } else {
            return ResponseEntity.badRequest().body("Please provide groupBy or sortBy");
        }
    }
}
