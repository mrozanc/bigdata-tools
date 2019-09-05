plugins {
    id("kotlin.config")
    kotlin("kapt")
}

configurations.all {
    exclude(group = "junit", module = "junit")
    exclude(group = "log4j", module = "log4j")
    exclude(group = "org.slf4j", module = "slf4j-log4j12")
}

dependencies {
    val scalaVersion: String by project

    api(enforcedPlatform(project(":bigdata-platform")))
    implementation(project(":bigdata-annotations"))
    implementation("org.apache.spark:spark-sql_$scalaVersion")
    implementation("com.google.guava:guava")
    compileOnly("com.google.auto.service:auto-service")
    implementation(kotlin("stdlib-jdk8"))

    kapt(enforcedPlatform(project(":bigdata-platform")))
    kapt("com.google.auto.service:auto-service")

    testImplementation("org.junit.jupiter:junit-jupiter")
}
