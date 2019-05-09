plugins {
    id("kotlin.config")
}

dependencies {
    implementation(enforcedPlatform(project(":bigdata-platform")))
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":spark-tools"))
    implementation("org.apache.spark:spark-sql_2.12")
}
