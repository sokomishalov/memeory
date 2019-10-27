import org.springframework.boot.gradle.tasks.bundling.BootJar

val developmentOnly: Configuration by configurations.creating
configurations.runtimeClasspath.get().extendsFrom(developmentOnly)

dependencies {
    implementation(project(":core"))
    implementation(project(":db"))
    implementation(project(":providers"))
    implementation(project(":telegram"))

    compile(kotlin("stdlib-jdk8"))
    implementation("com.github.sokomishalov.commons:commons-spring:1.0.24")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-security")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    implementation("io.springfox:springfox-swagger2:3.0.0-SNAPSHOT")
    implementation("io.springfox:springfox-spring-webflux:3.0.0-SNAPSHOT")
    implementation("io.springfox:springfox-swagger-ui:3.0.0-SNAPSHOT")

    implementation("io.netty:netty-transport-native-epoll:4.1.42.Final")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = true
jar.enabled = false