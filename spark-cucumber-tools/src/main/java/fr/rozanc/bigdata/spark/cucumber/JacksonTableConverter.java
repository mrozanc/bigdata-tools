package fr.rozanc.bigdata.spark.cucumber;

import io.cucumber.datatable.DataTable;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class JacksonTableConverter implements DataTable.TableConverter {

    private final Class<?> type;

    public JacksonTableConverter() {
        this(Map.class);
    }

    public JacksonTableConverter(final Class<?> type) {
        this.type = type;
    }

    @Override
    public <T> T convert(DataTable dataTable, Type type) {
        return null;
    }

    @Override
    public <T> T convert(DataTable dataTable, Type type, boolean transposed) {
        return null;
    }

    @Override
    public <T> List<T> toList(DataTable dataTable, Type itemType) {
        return null;
    }

    @Override
    public <T> List<List<T>> toLists(DataTable dataTable, Type itemType) {
        return null;
    }

    @Override
    public <K, V> Map<K, V> toMap(DataTable dataTable, Type keyType, Type valueType) {
        return null;
    }

    @Override
    public <K, V> List<Map<K, V>> toMaps(DataTable dataTable, Type keyType, Type valueType) {
        return null;
    }
}
