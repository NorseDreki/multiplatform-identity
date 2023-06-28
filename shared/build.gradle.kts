plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("com.google.devtools.ksp") version "1.8.21-1.0.11"
    kotlin("plugin.serialization") version "1.8.21"
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()


    //jvm()
    //macosX64()
    js(IR) {
        //binaries.executable()
        //browser {
        //}
        //generateTypeScriptDefinitions()
    }
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
        }
    }
    
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation("com.auth0.android:auth0:2.9.3")
                implementation("net.openid:appauth:0.11.1")
            }
        }
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
                implementation("dev.gitlive:firebase-auth:1.8.0")
                implementation("com.russhwolf:multiplatform-settings:1.0.0")
                implementation("com.russhwolf:multiplatform-settings-no-arg:1.0.0")
                //implementation("com.russhwolf:multiplatform-settings-datastore:1.0.0")
                implementation("com.russhwolf:multiplatform-settings-coroutines:1.0.0")
                implementation("com.russhwolf:multiplatform-settings-serialization:1.0.0")
                implementation("com.liftric:cognito-idp:2.8.3")
                implementation("io.github.aakira:napier:2.6.1")
                implementation("io.insert-koin:koin-core:3.4.2")
                implementation("io.insert-koin:koin-core-coroutines:3.4.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")

                implementation("io.kotest:kotest-assertions-core:5.6.2")
                implementation("io.mockative:mockative:1.4.1")
                //implementation("io.mockk:mockk-common:1.12.5")
                implementation("com.russhwolf:multiplatform-settings-test:1.0.0")
                implementation("io.insert-koin:koin-test:3.4.1")
            }
        }
    }
}

dependencies {
    configurations
        .filter { it.name.startsWith("ksp") && it.name.contains("Test") }
        .forEach {
            add(it.name, "io.mockative:mockative-processor:1.4.1")
        }
}

android {
    namespace = "com.norsedreki.multiplatform.identity"
    compileSdk = 33
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

}