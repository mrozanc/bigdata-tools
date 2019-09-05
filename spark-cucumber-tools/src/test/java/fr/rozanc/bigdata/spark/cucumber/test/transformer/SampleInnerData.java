package fr.rozanc.bigdata.spark.cucumber.test.transformer;

import lombok.Data;

import java.io.Serializable;

@Data
public class SampleInnerData implements Serializable {

    private String nestedField;
}
