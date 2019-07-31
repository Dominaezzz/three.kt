import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

plugins {
    java
    kotlin("jvm") version "1.3.41" apply false
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.named<Wrapper>("wrapper") {
    distributionType = Wrapper.DistributionType.ALL
}

subprojects {

    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    }

    tasks {
        named<KotlinJvmCompile>("compileKotlin") {
            kotlinOptions.jvmTarget = "1.8"
        }
        named<KotlinJvmCompile>("compileTestKotlin") {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
}