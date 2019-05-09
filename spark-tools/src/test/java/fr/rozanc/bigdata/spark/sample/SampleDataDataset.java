package fr.rozanc.bigdata.spark.sample;

public class SampleDataDataset extends org.apache.spark.sql.Dataset<fr.rozanc.bigdata.spark.sample.SampleData> {

    public SampleDataDataset(org.apache.spark.sql.Dataset<fr.rozanc.bigdata.spark.sample.SampleData> delegate) {
        super(delegate.sparkSession(), delegate.queryExecution(), fr.rozanc.bigdata.spark.sample.SampleData.encoder);
    }

    public class Cols {
        public org.apache.spark.sql.Column id() {
            return col("id");
        }

        public org.apache.spark.sql.Column name() {
            return col("name");
        }
    }

    public final Cols cols = new Cols();
}
