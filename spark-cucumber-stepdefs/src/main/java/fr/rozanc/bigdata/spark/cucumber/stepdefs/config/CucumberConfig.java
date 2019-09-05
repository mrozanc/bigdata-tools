package fr.rozanc.bigdata.spark.cucumber.stepdefs.config;

import fr.rozanc.bigdata.spark.cucumber.stepdefs.World;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;

@Configuration
@DirtiesContext
public class CucumberConfig {

    @Bean
    public SparkSession sparkSession() {
        return SparkSession.builder().master("local").getOrCreate();
    }

    @Bean
    public World world(final SparkSession sparkSession) {
        return new World(sparkSession);
    }
}
