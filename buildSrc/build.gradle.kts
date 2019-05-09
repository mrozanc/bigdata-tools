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
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.31")
    implementation("io.freefair.gradle:lombok-plugin:3.3.0")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:0.9.18")
}
