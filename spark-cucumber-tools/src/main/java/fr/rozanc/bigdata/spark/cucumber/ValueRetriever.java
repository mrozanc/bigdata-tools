package fr.rozanc.bigdata.spark.cucumber;

import lombok.val;

import java.util.Map;
import java.util.regex.Pattern;

public class ValueRetriever {

    private final Pattern listPattern = Pattern.compile("[^\\[]+(?:\\[(\\d+)])+$");

    <T> T retrieveFromMap(final String key, final Map<String, Object> map) {
        val levels = key.split("\\.");
        int currentLevel = 0;
        while (currentLevel < levels.length) {
            val currentKey = levels[currentLevel];
            val matcher = listPattern.matcher(currentKey);
            while (matcher.find()) {
                System.out.println(matcher.group(1));
            }
//            System.out.println(matcher.groupCount());
//            System.out.println(matcher.group(1));
//            System.out.println(matcher.group(2));
            ++currentLevel;
        }
        return null;
    }
}
