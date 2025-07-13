package com.example.jsondataset.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@Table(name = "dataset_records")
@NoArgsConstructor
@AllArgsConstructor
public class DatasetRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String datasetName;

    @JdbcTypeCode(SqlTypes.JSON) // ✅ Required for PostgreSQL jsonb
    @Column(columnDefinition = "jsonb", nullable = false)
    private String data;
}
