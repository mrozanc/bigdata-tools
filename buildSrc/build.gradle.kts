plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    jcenter()
    gradlePluginPortal()
}

dependencies {
    implementation(kotlin(module = "gradle-plugin", version = "1.3.41"))
    implementation("io.freefair.gradle:lombok-plugin:3.8.4")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:0.9.18")
}
