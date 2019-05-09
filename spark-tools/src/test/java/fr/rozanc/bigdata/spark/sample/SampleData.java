package fr.rozanc.bigdata.spark.sample;

import lombok.Data;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

@Data
public class SampleData {

    public static final Encoder<SampleData> encoder = Encoders.bean(SampleData.class);

    private Long id;
    private String name;
}
