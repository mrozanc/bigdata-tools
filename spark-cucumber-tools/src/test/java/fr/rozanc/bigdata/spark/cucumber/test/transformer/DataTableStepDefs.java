package fr.rozanc.bigdata.spark.cucumber.test.transformer;

import io.cucumber.java.en.Given;
import io.cucumber.datatable.DataTable;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.byLessThan;
import static org.junit.jupiter.api.Assertions.assertAll;

public class DataTableStepDefs {

    @Given("I read this DataTable:")
    public void IReadThisDataTable(final DataTable dataTable) {
        final List<SampleData> data = dataTable.asList(SampleData.class);
        assertThat(data).hasSize(2);
        assertAll(
                () -> assertThat(data.get(0).getId()).as("1st line 'id'").isEqualTo(5L),
                () -> assertThat(data.get(0).getName()).as("1st line 'name'").isEqualTo("hello"),
                () -> assertThat(data.get(0).getQuantity()).as("1st line 'quantity'").isEqualTo(15),
                () -> assertThat(data.get(0).getAmount()).as("1st line 'amount'").isEqualTo(45.12, byLessThan(1e-2)),
                () -> assertThat(data.get(0).getDate()).as("1st line 'date'").isEqualTo(LocalDate.of(2019, Month.JUNE, 18)),
                () -> assertThat(data.get(0).getInnerData().getNestedField()).as("1st line 'innerData.nestedField'").isEqualTo("example"),
                () -> assertThat(data.get(1).getId()).as("2nd line 'id'").isEqualTo(7L),
                () -> assertThat(data.get(1).getName()).as("2nd line 'name'").isEqualTo("world"),
                () -> assertThat(data.get(1).getQuantity()).as("2nd line 'quantity'").isEqualTo(-4),
                () -> assertThat(data.get(1).getAmount()).as("2nd line 'amount'").isEqualTo(3.14, byLessThan(1e-2)),
                () -> assertThat(data.get(1).getDate()).as("2nd line 'date'").isEqualTo(LocalDate.of(2019, Month.JUNE, 1)),
                () -> assertThat(data.get(1).getInnerData().getNestedField()).as("2nd line 'innerData.nestedField'").isEqualTo("I'm nested")
        );
    }
}
