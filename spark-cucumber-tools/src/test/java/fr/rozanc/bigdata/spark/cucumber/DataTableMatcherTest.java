package fr.rozanc.bigdata.spark.cucumber;

import fr.rozanc.bigdata.spark.cucumber.test.transformer.SampleData;
import lombok.val;
import org.apache.spark.sql.Encoders;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataTableMatcherTest {

    @Test
    void testDatasetComparison() {
        val encoder = Encoders.bean(SampleData.class);
        System.out.println(encoder.schema());
    }
}
