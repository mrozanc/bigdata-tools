package fr.rozanc.bigdata.spark.generator.test;

import fr.rozanc.bigdata.spark.annotation.GenerateDataset;
import lombok.Data;

import java.io.Serializable;

@GenerateDataset
@Data
public class SampleSerializableData implements Serializable {
    private static final long serialVersionUID = -2694708012545915464L;

    private Integer id;
    private String name;
}
