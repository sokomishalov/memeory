import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    api(project(":core"))
    api(project(":db"))
    implementation("org.telegram:telegrambots-spring-boot-starter:4.8.1")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true