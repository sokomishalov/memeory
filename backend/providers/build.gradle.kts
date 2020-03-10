import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    api(project(":core"))

    api("com.github.sokomishalov.skraper:skrapers:0.2.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true