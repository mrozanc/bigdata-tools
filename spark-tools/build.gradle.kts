plugins {
    id("kotlin.config")
    kotlin("kapt")
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

    compileOnly(project(":spark-typed-dataset-generator"))
    kapt(project(":spark-typed-dataset-generator"))

    testImplementation("org.junit.jupiter:junit-jupiter")
}
