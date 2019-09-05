package fr.rozanc.bigdata.spark.generator.test;

import fr.rozanc.bigdata.spark.annotation.GenerateDataset;
import lombok.Data;
import lombok.EqualsAndHashCode;

@GenerateDataset
@Data
@EqualsAndHashCode(callSuper = true)
public class SampleData extends SampleSerializableData {
    private static final long serialVersionUID = -2760297910209884066L;

    private Double value;
}
