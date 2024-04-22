plugins {
    id("java")
}

group = "lol.koblizek"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:2.0.13")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.23.1")

    implementation("com.formdev:flatlaf-extras:3.4.1")
    implementation("com.github.weisj:jsvg:1.4.0")
    implementation("com.formdev:flatlaf:3.4.1")
    implementation("com.formdev:flatlaf-intellij-themes:3.4.1")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}