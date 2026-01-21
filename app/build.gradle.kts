plugins {
    kotlin("android")
    id("com.android.application")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.theapache64.klokk"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.theapache64.klokk"
        minSdk = 24
        targetSdk = 35
        // [latest version - i promise!]
        versionCode = 14
        // [latest version - i promise!]
        versionName = "v1.1.4"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.activity:activity-compose:1.9.0")
}
