import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":core"))

    compile(kotlin("stdlib-jdk8"))
    implementation("com.github.sokomishalov.commons:commons-spring:1.0.24")

    implementation("javax.xml.bind:jaxb-api:2.2.11")

    implementation("com.github.igor-suhorukov:instagramscraper:2.2")

    testImplementation("junit:junit:4.12")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true