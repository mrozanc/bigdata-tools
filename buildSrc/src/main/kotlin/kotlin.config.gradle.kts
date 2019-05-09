import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
}

plugins.withId("kotlin") {
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    val javadocJar = tasks.create<Jar>("javadocJar") {
        val dokkaTask = tasks.getByName("dokka", DokkaTask::class)
        dependsOn(dokkaTask)
        archiveClassifier.set("javadoc")
        from(dokkaTask.outputDirectory)
    }

    artifacts {
        add("archives", tasks.kotlinSourcesJar.get().archiveFile)
        add("archives", javadocJar.archiveFile)
    }
}
