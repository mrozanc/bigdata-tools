plugins {
    id("com.gradle.build-scan") version "2.3"
}

allprojects {
    group = "fr.rozanc.bigdata"
    version = file("$rootDir/version.txt").readText()
    
    repositories {
        jcenter()
    }
}

tasks.withType<Wrapper> {
    gradleVersion = "5.6.1"
    distributionType = Wrapper.DistributionType.ALL
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}
