import io.freefair.gradle.plugins.lombok.LombokExtension

plugins {
    id("java-library")
    id("io.freefair.lombok") version "3.3.0"
}

plugins.withId("java-library") {
    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<JavaCompile> {
        this.options.encoding = "UTF-8"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    val sourceJar = tasks.create<Jar>("sourceJar") {
        dependsOn(tasks.getByName("classes"))
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    val javadocJar = tasks.create<Jar>("javadocJar") {
        dependsOn(tasks.getByName("javadoc"))
        archiveClassifier.set("javadoc")
        from((tasks.getByName("javadoc") as Javadoc).destinationDir)
    }

    artifacts {
        add("archives", sourceJar.archiveFile)
        add("archives", javadocJar.archiveFile)
    }
}

plugins.withId("io.freefair.lombok") {
    configure<LombokExtension> {
        version.set("1.18.8")
    }
}
