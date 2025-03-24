import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.gradle.api.tasks.Exec

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            freeCompilerArgs += listOf("-Xbinary=bundleId=com.grouph.poster")
            isStatic = true
        }
    }

    sourceSets {


        androidMain {
            dependencies {
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)
            }
        }
        commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.runtime.compose)
            }
        }
    }
}

android {
    compileSdk = 35
    namespace = "org.group_h.poster"

    defaultConfig {
        applicationId = "org.group_h.poster"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.ui.tooling.preview.android)
    implementation(libs.androidx.navigation.runtime.android)
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.activity:activity-compose:1.10.0")
    implementation("androidx.navigation:navigation-compose:2.9.0-alpha05")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.22")
    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.navigation:navigation-compose:2.5.3")//added for navigation throughout pages
}

// INTERNAL TOOL, for ryan and maybe iga since she also uses mac

// to build on ios just open the xcodeproj in xcode and build for the simulator that way,
// i originally had the feature of running simulator through andriod studio but something broke

// i keep spelling android wrong i dont care

// a pain, i dont know what happened but it USED to work on my machine, this was a month ago and
// i dont know what happened in the mean time (updated macos and xcode) but andriod studio was no
// longer creating a run config for the ios app, so the two following tasks are used to solve

// lots of gpt, research, and 3 hours of my time
tasks.register<Exec>("buildIosApp") {
    // use the command line xcodebuild tool to build the ios xcodeproj file
    // make sure it is build for the ios simulator, not production devices
    // - funnily enough these are very different, its near impossible to run pure ios binarys on the
    //   macos "ios" simulator, and yes simulator not emulator
    // build it clean too, no codesigning because i dont know i was getting errors upon error
    commandLine(
        "xcodebuild",
        "-project", "../iosApp/iosApp.xcodeproj",
        "-scheme", "iosApp",
        "-destination", "platform=iOS Simulator,OS=17.0,name=iPhone 15",
        "clean",
        "build",
        "CODE_SIGNING_ALLOWED=NO",
        "CODE_SIGNING_REQUIRED=NO"
    )
}

// task to run the ios app, depends on building it first
tasks.register("runIosApp") {
    dependsOn("buildIosApp")
    doLast {
        // use this command to boot the simulator
        exec {
            commandLine("xcrun", "simctl", "boot", "iPhone 15")
            isIgnoreExitValue = true
        }
        // once booted, install the build .app on the simulator using the following command
        // idk why the path is named this but it just is
        // as you can see the user is ryan so it will only work on my system but sure
        exec {
            commandLine(
                "xcrun", "simctl", "install", "booted",
                "/Users/ryan/Library/Developer/Xcode/DerivedData/iosApp-gottotdlqsrgdybmkxawihnismci/Build/Products/Debug-iphonesimulator/poster.app"
            )
        }
        // then launch app once booted on the simulator, and YES the bundleid is different on ios due
        // to apple not allowing _ in bundle id's
        // this also causes issues but sure
        exec {
            commandLine("xcrun", "simctl", "launch", "booted", "org.grouph.poster.poster")
        }

        // bring simulator to foreground
        // AppleScript uses english language like syntax apparently
        exec {
            commandLine("osascript", "-e", "tell application \"Simulator\" to activate")
        }
    }
}

