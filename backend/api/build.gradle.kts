import org.springframework.boot.gradle.tasks.bundling.BootJar

val developmentOnly: Configuration by configurations.creating
configurations.runtimeClasspath.get().extendsFrom(developmentOnly)

dependencies {
    api(project(":core"))
    api(project(":db"))
    api(project(":providers"))
    api(project(":telegram"))

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