package com.example.jsondataset.repository.impl;

import com.example.jsondataset.repository.DatasetRecordRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DatasetRecordRepositoryImpl implements DatasetRecordRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> findSorted(String datasetName, String sortKey, String order, int limit, int offset, boolean isNumeric) {
        StringBuilder sql = new StringBuilder("SELECT data FROM dataset_records WHERE dataset_name = :datasetName ");

        if (isNumeric) {
            // ✅ Correct casting for PostgreSQL JSONB field to integer
            sql.append("ORDER BY CAST(data->>'").append(sortKey).append("' AS INTEGER) ");
        } else {
            sql.append("ORDER BY data->>'").append(sortKey).append("' ");
        }

        if (order.equalsIgnoreCase("desc")) {
            sql.append("DESC ");
        } else {
            sql.append("ASC ");
        }

        sql.append("LIMIT :limit OFFSET :offset");

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("datasetName", datasetName);
        query.setParameter("limit", limit);
        query.setParameter("offset", offset);

        @SuppressWarnings("unchecked")
        List<String> results = query.getResultList();
        return results;
    }



    @Override
    public List<Map<String, Object>> findGrouped(String datasetName, String groupKey) {
        String sql = """
            SELECT data ->> ? as group_value, json_agg(data) as records
            FROM dataset_records
            WHERE dataset_name = ?
            GROUP BY group_value
        """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, groupKey);
        query.setParameter(2, datasetName);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = query.getResultList();

        List<Map<String, Object>> grouped = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> map = new HashMap<>();
            map.put("group_value", row[0]);
            map.put("records", row[1]);
            grouped.add(map);
        }

        return grouped;
    }


}
