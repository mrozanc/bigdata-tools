package fr.rozanc.bigdata.sample.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class SampleModel implements Serializable {

    private static final long serialVersionUID = 6168538544463233620L;

    private Long id;
    private String name;
    private Double value;
    private Integer quantity;
    private String currency;
    private LocalDate date;
}
