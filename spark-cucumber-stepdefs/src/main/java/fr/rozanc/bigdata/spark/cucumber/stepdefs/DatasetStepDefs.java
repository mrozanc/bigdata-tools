package fr.rozanc.bigdata.spark.cucumber.stepdefs;

import io.cucumber.java.en.Given;
import fr.rozanc.bigdata.sample.model.SampleModel;
import io.cucumber.datatable.DataTable;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;

import java.util.List;

public class DatasetStepDefs extends SpringStepDefs {

    @Given("the current Dataset is:")
    public void givenTheCurrentDatasetIs(final DataTable datasetRows) {
        final List<SampleModel> rows = datasetRows.asList(SampleModel.class);
        final Dataset<SampleModel> dataset = world.sparkSession.createDataset(rows, Encoders.bean(SampleModel.class));
        world.setCurrentDataset(dataset);
    }
}
