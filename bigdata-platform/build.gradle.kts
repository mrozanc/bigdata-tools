plugins {
    id("java-platform")
    id("maven-publish")
}

ext {
    set("sparkVersion", "2.4.2")
    set("junitVersion", "5.4.2")
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(enforcedPlatform("org.jetbrains.kotlin:kotlin-bom:${extra["kotlinVersion"]}"))
    api(enforcedPlatform("org.junit:junit-bom:${extra["junitVersion"]}"))

    constraints {
        api("org.apache.spark:spark-catalyst_2.12:${extra["sparkVersion"]}")
        api("org.apache.spark:spark-core_2.12:${extra["sparkVersion"]}")
        api("org.apache.spark:spark-sql_2.12:${extra["sparkVersion"]}")
        api("org.apache.spark:spark-streaming_2.12:${extra["sparkVersion"]}")
        api("org.apache.spark:spark-yarn_2.12:${extra["sparkVersion"]}")

        api(project(":spark-tools"))
        api(project(":spark-tools-kotlin"))
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components.getByName("javaPlatform"))
        }
    }
}
