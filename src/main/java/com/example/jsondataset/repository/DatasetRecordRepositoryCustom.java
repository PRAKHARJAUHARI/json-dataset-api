package com.example.jsondataset.repository;

import java.util.*;

public interface DatasetRecordRepositoryCustom {
    List<String> findSorted(String datasetName, String sortKey, String order, int limit, int offset, boolean isNumeric);

    List<Map<String, Object>> findGrouped(String datasetName, String groupKey);

    //List<String> findSorted(String datasetName, String sortKey, int limit, int offset, boolean isNumeric, boolean asc);
}
