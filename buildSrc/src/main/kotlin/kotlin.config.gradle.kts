import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java.config")
    kotlin("jvm")
}

plugins.withId("kotlin") {
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

//    artifacts {
//        add("archives", tasks.kotlinSourcesJar.get().archiveFile)
//    }
}
