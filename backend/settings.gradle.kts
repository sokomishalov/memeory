include("api", "core", "db", "providers", "telegram")

pluginManagement {
    repositories {
        maven { url = uri("http://repo.spring.io/snapshot") }
        maven { url = uri("http://repo.spring.io/milestone") }
        maven { url = uri("http://repo.spring.io/plugins-release") }
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.springframework.boot") {
                useModule("org.springframework.boot:spring-boot-gradle-plugin:${requested.version}")
            }
        }
    }
}