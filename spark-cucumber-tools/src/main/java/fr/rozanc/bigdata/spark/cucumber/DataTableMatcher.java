package fr.rozanc.bigdata.spark.cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.apache.spark.sql.Dataset;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class DataTableMatcher {

    private final ObjectMapper objectMapper;

    public DataTableMatcher(final ObjectMapper dataTableObjectMapper) {
        objectMapper = dataTableObjectMapper;
    }

    @SuppressWarnings("unchecked")
    public void containsInAnyOrder(final Dataset<?> dataset, final DataTable expected) {

        val datasetRows = dataset.javaRDD().collect();
        if (datasetRows.isEmpty()) {
            assertThat(expected.asList()).describedAs("dataset is empty").isEmpty();
            return;
        }

        val datasetElementClass = datasetRows.get(0).getClass();
        val expectedRows = expected.asList(datasetElementClass);

        val expectedColumns = expected.cells().get(0);
        final List<Map<String, Object>> expectedMaps = expectedRows.stream()
                .map(r -> (Map<String, Object>) objectMapper.convertValue(r, Map.class)).collect(toList());

        val maps = datasetRows.stream()
                .map(r -> {
                    Map<String, Object> map = objectMapper.convertValue(r, Map.class);
                    expectedColumns.forEach(map::remove);
                    return map;
                }).collect(toList());

        assertThat(maps).containsExactlyInAnyOrderElementsOf(expectedMaps);
    }

}
