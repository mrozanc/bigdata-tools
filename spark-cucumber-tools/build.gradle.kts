plugins {
    id("java.config")
}

configurations.all {
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
}

dependencies {
    val scalaVersion: String by project

    implementation(enforcedPlatform(project(":bigdata-platform")))
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("io.cucumber:cucumber-java8")
    implementation("io.cucumber:cucumber-java")
    implementation("org.apache.spark:spark-sql_$scalaVersion")
    implementation("org.apache.spark:spark-core_$scalaVersion")
    implementation("org.apache.spark:spark-catalyst_$scalaVersion")
    implementation("org.assertj:assertj-core")

    testImplementation("io.cucumber:cucumber-junit")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
}
