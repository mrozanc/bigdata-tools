package fr.rozanc.bigdata.spark.cucumber;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.cucumber.datatable.TableCellByTypeTransformer;
import io.cucumber.datatable.TableEntryByTypeTransformer;
import lombok.extern.java.Log;
import lombok.val;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

@Log
public class JacksonDataTableTransformer implements TableEntryByTypeTransformer, TableCellByTypeTransformer {

    private final ObjectMapper objectMapper;

    public JacksonDataTableTransformer() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public JacksonDataTableTransformer(final ObjectMapper dataTableObjectMapper) {
        objectMapper = dataTableObjectMapper;
    }

    @Override
    public <T> T transform(String value, Class<T> cellType) {
        return transform(value, objectMapper.constructType(cellType));
    }

    private <T> T transform(String value, JavaType cellType) {
        return objectMapper.convertValue(value, cellType);
    }

    @Override
    public <T> T transform(final Map<String, String> entry,
                           final Class<T> type,
                           final TableCellByTypeTransformer cellTransformer) throws Throwable {
        return transform(entry, objectMapper.constructType(type), cellTransformer);
    }

    private <T> T transform(final Map<String, String> entry,
                            final JavaType type,
                            final TableCellByTypeTransformer cellTransformer) throws Throwable {

        // Group columns by common first field and strip the first field in the next level keys
        final Map<String, Map<String, String>> nestedEntries =
                entry.entrySet().stream()
                        .filter(e -> e.getKey().matches("[^.\\[\\]]+\\..*"))
                        .collect(groupingBy(e -> e.getKey().substring(0, e.getKey().indexOf('.')),
                                HashMap::new,
                                toMap(e -> e.getKey().substring(e.getKey().indexOf('.') + 1), Map.Entry::getValue)));

        final Map<String, List<String>> listEntries = new LinkedHashMap<>();


//        entry.entrySet().stream()
//                .filter(e -> e.getKey().matches("[^.\\[\\]]+\\[\\d*](\\..*|$)"))
//                .forEachOrdered((k, v) -> {
//
//                });
//                .map(e -> new AbstractMap.SimpleImmutableEntry<>(e.getKey().substring(0, e.getKey().indexOf('[')), e.getValue()))
//                .collect(groupingBy(AbstractMap.SimpleImmutableEntry::getKey, mapping(AbstractMap.SimpleImmutableEntry::getValue, toList())));

        // Make a recursive call to resolve the nested values
        final Map<String, Object> nestedValues = new HashMap<>();
        for (Map.Entry<String, Map<String, String>> mapEntry : nestedEntries.entrySet()) {
            String k = mapEntry.getKey();
            Map<String, String> v = mapEntry.getValue();
            try {
                nestedValues.put(k, transform(v, getFieldType(type, k), cellTransformer));
            } catch (NoSuchFieldException e) {
                log.log(Level.SEVERE, "Unable to get type of field '" + k + "'", e);
                throw e;
            }
        }

        // Combine the non-nested values of this level with the converted nested values
        nestedValues.putAll(
                entry.entrySet().stream()
                        .filter(e -> !e.getKey().contains("."))
                        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue)));

        // Perform the conversion for this level
        return objectMapper.convertValue(nestedValues, type);
    }

    private JavaType getFieldType(final JavaType type, final String fieldName) throws Exception {
        val fieldType = objectMapper.constructType(type.getRawClass().getDeclaredField(fieldName).getGenericType());
        if (fieldType.isMapLikeType() || fieldType.isCollectionLikeType()) {
            return fieldType.getContentType();
        }
        return fieldType;
    }
}
