package fr.rozanc.bigdata.spark.cucumber.test.transformer;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class SampleData implements Serializable {

    private Long id;
    private String name;
    private Integer quantity;
    private Double amount;
    private LocalDate date;
    private final SampleInnerData innerData = new SampleInnerData();
    private List<Integer> integers;
}
