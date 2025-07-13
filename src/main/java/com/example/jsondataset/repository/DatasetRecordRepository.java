package com.example.jsondataset.repository;

import com.example.jsondataset.model.DatasetRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DatasetRecordRepository extends JpaRepository<DatasetRecord, Long>, DatasetRecordRepositoryCustom {
    List<Map<String, Object>> findGrouped(@Param("datasetName") String datasetName, @Param("groupKey") String groupKey);

    long countByDatasetName(String datasetName);
}
