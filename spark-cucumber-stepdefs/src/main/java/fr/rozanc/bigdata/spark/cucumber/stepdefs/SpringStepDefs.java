package fr.rozanc.bigdata.spark.cucumber.stepdefs;

import fr.rozanc.bigdata.spark.cucumber.stepdefs.config.CucumberConfig;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CucumberConfig.class})
abstract public class SpringStepDefs {

    @Autowired
    protected World world;
}
