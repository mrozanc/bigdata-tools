plugins {
    id("java-platform")
    id("maven-publish")
}

ext {
    set("auto-service.version", "1.0-rc6")
    set("cucumber.version", "4.7.2")
    set("junit.version", "5.5.1")
    set("guava.version", "28.0-jre")
    set("spark.version", "2.4.3")
    set("spring-boot.version", "2.1.7.RELEASE")
}

javaPlatform {
    allowDependencies()
}

dependencies {
    val kotlinVersion: String by project
    val scalaVersion: String by project

    constraints {
        api(project(":spark-tools"))
        api(project(":spark-tools-kotlin"))

        api("com.google.auto.service:auto-service:${project.ext["auto-service.version"]}")
        api("com.google.guava:guava:${project.ext["guava.version"]}")
        api("io.cucumber:cucumber-java:${project.ext["cucumber.version"]}")
        api("io.cucumber:cucumber-java8:${project.ext["cucumber.version"]}")
        api("io.cucumber:cucumber-junit:${project.ext["cucumber.version"]}")
        api("io.cucumber:cucumber-spring:${project.ext["cucumber.version"]}")
        api("org.assertj:assertj-core:3.11.1")
        api("org.apache.spark:spark-catalyst_$scalaVersion:${project.ext["spark.version"]}")
        api("org.apache.spark:spark-core_$scalaVersion:${project.ext["spark.version"]}")
        api("org.apache.spark:spark-sql_$scalaVersion:${project.ext["spark.version"]}")
        api("org.apache.spark:spark-streaming_$scalaVersion:${project.ext["spark.version"]}")
        api("org.apache.spark:spark-yarn_$scalaVersion:${project.ext["spark.version"]}")
    }

    api(enforcedPlatform("org.jetbrains.kotlin:kotlin-bom:$kotlinVersion"))
    api(enforcedPlatform("org.junit:junit-bom:${project.ext["junit.version"]}"))
    api(enforcedPlatform("org.springframework.boot:spring-boot-dependencies:${project.ext["spring-boot.version"]}"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components.getByName("javaPlatform"))
        }
    }
}
