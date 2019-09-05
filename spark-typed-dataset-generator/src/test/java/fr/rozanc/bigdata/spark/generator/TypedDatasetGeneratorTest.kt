package fr.rozanc.bigdata.spark.generator

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.PrintWriter

internal class TypedDatasetGeneratorTest {

    @Test
    fun testClassGeneration() {
        val generator = TypedDatasetGenerator()
        generator.write(className = "PersonDataset",
                destinationPackage = "test",
                underlyingType = "Person",
                fieldNames = listOf("aaa", "bbb", "ccc"),
                serialVersionUid = 1L,
                printWriter = PrintWriter(System.out))
    }
}