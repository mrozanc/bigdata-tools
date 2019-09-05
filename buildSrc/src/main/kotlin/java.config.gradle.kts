import io.freefair.gradle.plugins.lombok.LombokExtension
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("java-library")
    id("io.freefair.lombok")
    id("jacoco")
    id("org.jetbrains.dokka")
}

plugins.withId("java-library") {
    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<JavaCompile> {
        options.compilerArgs.add("-Xlint:deprecation")
        options.encoding = "UTF-8"
        options.isFork = true
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        doFirst {
            systemProperty("testDir", "$buildDir/tmp/test")
        }
    }

    val sourceJar = tasks.register<Jar>("sourceJar") {
        dependsOn(tasks.getByName("classes"))
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    artifacts {
        archives(sourceJar.get().archiveFile)
    }
}

plugins.withId("io.freefair.lombok") {
    configure<LombokExtension> {
        version.set(project.ext["lombok.version"] as String)
    }
}

plugins.withId("org.jetbrains.dokka") {
    val javadocJar = tasks.register<Jar>("javadocJar") {
        val dokkaTask = tasks.getByName("dokka", DokkaTask::class)
        dependsOn(dokkaTask)
        archiveClassifier.set("javadoc")
        from(dokkaTask.outputDirectory)
    }

    artifacts {
        archives(javadocJar.get().archiveFile)
    }
}

plugins.withId("jacoco") {
    tasks.jacocoTestReport {
        reports {
            xml.isEnabled = true
        }
    }
}
