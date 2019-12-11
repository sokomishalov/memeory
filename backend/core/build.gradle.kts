import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    api("com.github.sokomishalov.commons:commons-spring:1.0.29")
    api("org.springframework:spring-webflux")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true