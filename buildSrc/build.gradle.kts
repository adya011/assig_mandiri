/*
plugins {
    id 'java-library'
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}*/

plugins {
    `kotlin-dsl`
}
// Required since Gradle 4.10+.
repositories {
    mavenCentral()
}