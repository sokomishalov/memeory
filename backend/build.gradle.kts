import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.0.M3"
    id("io.spring.dependency-management") version "1.0.7.RELEASE"
    id("net.linguica.maven-settings") version "0.5"
    kotlin("jvm") version "1.3.31"
    kotlin("plugin.spring") version "1.3.31"
    kotlin("kapt") version "1.3.31"
}

group = "ru.sokomishalov"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11

val developmentOnly by configurations.creating
configurations.runtimeClasspath.get().extendsFrom(developmentOnly)

repositories {
    mavenCentral()
    jcenter()
    maven { url = uri("http://repo.spring.io/snapshot") }
    maven { url = uri("http://repo.spring.io/milestone") }
    maven { url = uri("http://oss.jfrog.org/artifactory/oss-snapshot-local") }
}

sourceSets["main"].java.srcDir(file("build/generated/source/kapt/main"))

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.9.0")
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("org.mapstruct:mapstruct:1.3.0.Final")
    kapt("org.mapstruct:mapstruct-processor:1.3.0.Final")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("io.springfox:springfox-swagger2:3.0.0-SNAPSHOT")
    implementation("io.springfox:springfox-spring-webflux:3.0.0-SNAPSHOT")
    implementation("io.springfox:springfox-swagger-ui:3.0.0-SNAPSHOT")

    implementation("io.projectreactor:reactor-core:3.3.0.M1")
    implementation("io.projectreactor:reactor-tools:1.0.0.M1")
    implementation("io.projectreactor:reactor-kotlin-extensions:1.0.0.M2")
    implementation("io.projectreactor.addons:reactor-extra:3.3.0.M1")
    implementation("io.projectreactor.netty:reactor-netty:0.8.8.RELEASE")

    implementation("org.apache.commons:commons-lang3:3.9")
    implementation("com.google.guava:guava:28.0-jre")


    developmentOnly("org.springframework.boot:spring-boot-devtools")
    
    
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(group = "junit", module = "junit")
    }
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("io.projectreactor.tools:blockhound-junit-platform:1.0.0.M3")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}
