import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":core"))
    compile(kotlin("stdlib-jdk8"))
    implementation("com.github.sokomishalov.commons:commons-spring:1.0.24")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true