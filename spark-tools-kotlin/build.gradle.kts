plugins {
    id("kotlin.config")
}

dependencies {
    val scalaVersion: String by project

    implementation(enforcedPlatform(project(":bigdata-platform")))
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":spark-tools"))
    implementation("org.apache.spark:spark-sql_$scalaVersion")
}
