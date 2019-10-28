import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    api(project(":core"))
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true