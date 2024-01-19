plugins {
    alias(libs.plugins.jvm)
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation(libs.junit.jupiter.engine)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(libs.guava)
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.9.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-RC2")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("hare.AppKt")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
