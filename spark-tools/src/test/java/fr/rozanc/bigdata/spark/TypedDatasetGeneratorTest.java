package fr.rozanc.bigdata.spark;

import fr.rozanc.bigdata.spark.sample.SampleData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypedDatasetGeneratorTest {

    private TypedDatasetGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new TypedDatasetGenerator();
    }

    @Test
    void test() {
        generator.forType(SampleData.class);
    }
}