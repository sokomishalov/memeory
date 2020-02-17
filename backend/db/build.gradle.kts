import org.springframework.boot.gradle.tasks.bundling.BootJar

sourceSets["main"].java.srcDir(file("build/generated/source/kapt/main"))

dependencies {
    api(project(":core"))

    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")

    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("org.mapstruct:mapstruct:1.3.1.Final")
    kapt("org.mapstruct:mapstruct-processor:1.3.1.Final")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true