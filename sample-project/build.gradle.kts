plugins {
    id("java.config")
}

configurations.all {
    exclude(group = "ch.qos.logback")
}

dependencies {
    val scalaVersion: String by project
    implementation(enforcedPlatform(project(":bigdata-platform")))
    implementation("org.apache.spark:spark-core_$scalaVersion") {
        exclude(group = "log4j", module = "log4j")
        exclude(group = "org.slf4j", module = "slf4j-log4j12")
    }
    implementation("org.apache.spark:spark-sql_$scalaVersion") {
        exclude(group = "log4j", module = "log4j")
        exclude(group = "org.slf4j", module = "slf4j-log4j12")
    }
    compileOnly(project(":bigdata-annotations"))
    annotationProcessor(project(":spark-typed-dataset-generator"))

    testImplementation(project(":spark-cucumber-stepdefs"))
    testImplementation(project(":spark-cucumber-tools"))
    testImplementation("io.cucumber:cucumber-junit")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
}
