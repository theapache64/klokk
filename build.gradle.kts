@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

group = "com.theapache64"
version = "1.0.1"

kotlin {
    val xcf = XCFramework()
    val targets = listOf(macosX64(), macosArm64())
    targets.forEach { target ->
        target.binaries.framework {
            isStatic = true
            xcf.add(this)
        }
        target.binaries.executable {
            entryPoint = "com.theapache64.klokk.main"
        }
    }

    listOf(js(), wasmJs()).forEach {
        it.browser()
        it.binaries.executable()
    }

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.runtime)
            implementation(compose.material)
            implementation(compose.materialIconsExtended)
            implementation(libs.kotlinx.datetime)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlin.coroutines.swing)
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.theapache64.klokk.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Klokk"
        }
    }
}
