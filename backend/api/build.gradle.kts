import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    api(project(":core"))
    api(project(":db"))
    api(project(":providers"))
    api(project(":telegram"))
    api(project(":heroku-keepalive"))

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-security")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    implementation("io.springfox:springfox-swagger2:3.0.0-SNAPSHOT")
    implementation("io.springfox:springfox-spring-webflux:3.0.0-SNAPSHOT")
    implementation("io.springfox:springfox-swagger-ui:3.0.0-SNAPSHOT")

    implementation("io.netty:netty-transport-native-epoll:4.1.45.Final")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.10.2")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = true
jar.enabled = false