import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    api(project(":core"))
    api(project(":db"))
    api(project(":providers"))
    api(project(":telegram"))
    api(project(":heroku-keepalive"))

    api("org.springframework.boot:spring-boot-starter-actuator")
    api("org.springframework.boot:spring-boot-starter-webflux")
    api("org.springframework.boot:spring-boot-starter-security")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    api("io.springfox:springfox-boot-starter:3.0.0")

    implementation("io.netty:netty-transport-native-epoll:4.1.45.Final")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.11.2")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = true
jar.enabled = false