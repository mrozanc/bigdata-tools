package fr.rozanc.bigdata.spark.cucumber.stepdefs;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class World {

    public static final String DEFAULT_CURRENT_DATASET_NAME = "current";

    public String currentDatasetName = DEFAULT_CURRENT_DATASET_NAME;

    public final Map<String, Dataset<?>> datasets = new HashMap<>();

    public SparkSession sparkSession;

    public World(final SparkSession sparkSession) {
        this.sparkSession = sparkSession;
    }

    public Dataset<?> getCurrentDataset() {
        return datasets.get(currentDatasetName);
    }

    public void setCurrentDataset(final Dataset<?> dataset) {
        datasets.put(currentDatasetName, dataset);
    }
}
