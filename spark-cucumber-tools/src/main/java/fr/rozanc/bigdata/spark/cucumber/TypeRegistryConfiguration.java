package fr.rozanc.bigdata.spark.cucumber;

import io.cucumber.core.api.TypeRegistry;
import io.cucumber.core.api.TypeRegistryConfigurer;
import fr.rozanc.bigdata.spark.cucumber.JacksonDataTableTransformer;
import lombok.val;

import java.util.Locale;

import static java.util.Locale.ENGLISH;

public class TypeRegistryConfiguration implements TypeRegistryConfigurer {

    @Override
    public Locale locale() {
        return ENGLISH;
    }

    @Override
    public void configureTypeRegistry(final TypeRegistry typeRegistry) {
        val jacksonDataTableTransformer = new JacksonDataTableTransformer();
        typeRegistry.setDefaultDataTableEntryTransformer(jacksonDataTableTransformer);
        typeRegistry.setDefaultDataTableCellTransformer(jacksonDataTableTransformer);
    }
}
