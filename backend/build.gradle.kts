import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.JavaVersion.VERSION_1_8
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES

plugins {
    base

    id("org.springframework.boot") version "2.2.1.RELEASE" apply false

    val kotlinVersion = "1.3.50"
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion apply false
    kotlin("plugin.spring") version kotlinVersion apply false

    // gradle dependencyUpdates -Drevision=release
    id("com.github.ben-manes.versions") version "0.27.0"
}

apply {
    plugin<IdeaPlugin>()
}

subprojects {
    group = "ru.sokomishalov.memeory"

    repositories {
        mavenCentral()
        mavenLocal()
        jcenter()
        maven { url = uri("http://repo.spring.io/snapshot") }
        maven { url = uri("http://repo.spring.io/milestone") }
        maven { url = uri("http://oss.jfrog.org/artifactory/oss-snapshot-local") }
        maven { url = uri("http://jitpack.io") }
    }

    apply {
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.kapt")
        plugin("org.jetbrains.kotlin.plugin.spring")
    }

    the<DependencyManagementExtension>().apply {
        imports {
            mavenBom(BOM_COORDINATES)
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = VERSION_1_8.toString()
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    task("copyToLib") {
        doLast {
            copy {
                into("$buildDir/libs")
                from(configurations.compile)
            }
        }
    }

    task("stage") {
        dependsOn.add("build")
        dependsOn.add("copyToLib")
    }
}

dependencies {
    subprojects.forEach {
        archives(it)
    }
}

repositories {
    mavenCentral()
}