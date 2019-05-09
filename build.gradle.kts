allprojects {
    group = "fr.rozanc.bigdata"
    version = "1.0-SNAPSHOT"

    ext {
        set("kotlinVersion", "1.3.31")
    }
    
    repositories {
        jcenter()
    }
}

tasks.withType<Wrapper> {
    gradleVersion = "5.4.1"
    distributionType = Wrapper.DistributionType.ALL
}
