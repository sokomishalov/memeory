import org.springframework.boot.gradle.tasks.bundling.BootJar

sourceSets["main"].java.srcDir(file("build/generated/source/kapt/main"))

dependencies {
    implementation(project(":core"))

    compile(kotlin("stdlib-jdk8"))
    implementation("com.github.sokomishalov.commons:commons-spring:1.0.24")

    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")

    compile("org.mapstruct:mapstruct:1.3.1.Final")
    kapt("org.mapstruct:mapstruct-processor:1.3.1.Final")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true