package fr.rozanc.bigdata.spark.cucumber;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ValueRetrieverTest {

    @Test
    void testSimpleMap() {
        val retriever = new ValueRetriever();
        retriever.retrieveFromMap("myKey[0][121]", new HashMap<>());
    }
}