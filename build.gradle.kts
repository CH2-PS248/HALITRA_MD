// Top-level build file where you can add configuration options common to all sub-projects/modules.
//plugins {
//    id("com.android.application") version "8.1.1" apply false
//    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
//}

buildscript {
    // Top-level variables used for versioning

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.47")
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.5")
        classpath("de.undercouch:gradle-download-task:4.1.2")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}


tasks {
    val clean by registering (Delete::class) {
        delete(buildDir)
    }
}