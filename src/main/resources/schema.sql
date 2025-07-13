CREATE TABLE IF NOT EXISTS dataset_records (
    id SERIAL PRIMARY KEY,
    dataset_name TEXT NOT NULL,
    data JSONB NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_dataset_name ON dataset_records(dataset_name);
CREATE INDEX IF NOT EXISTS idx_data_age ON dataset_records((data->>'age'));
CREATE INDEX IF NOT EXISTS idx_data_department ON dataset_records((data->>'department'));