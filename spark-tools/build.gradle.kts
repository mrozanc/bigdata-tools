plugins {
    id("java.config")
}

dependencies {
    implementation(enforcedPlatform(project(":bigdata-platform")))
    implementation("org.apache.spark:spark-core_2.12")
    implementation("org.apache.spark:spark-sql_2.12")
    
    testImplementation("org.junit.jupiter:junit-jupiter")
}
