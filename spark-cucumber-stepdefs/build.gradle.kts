plugins {
    id("java.config")
}

dependencies {
    val scalaVersion: String by project

    implementation(project(":sample-project"))
    implementation(enforcedPlatform(project(":bigdata-platform")))
    implementation(project(":spark-cucumber-tools"))
    implementation("io.cucumber:cucumber-java8")
    implementation("io.cucumber:cucumber-spring")
    implementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.apache.spark:spark-sql_$scalaVersion") {
        exclude(group = "log4j", module = "log4j")
        exclude(group = "org.slf4j", module = "slf4j-log4j12")
    }
}
