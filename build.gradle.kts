import com.android.build.gradle.internal.tasks.factory.dependsOn
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.tasks.FatFrameworkTask

plugins {
    kotlin("multiplatform") version "1.3.30"
    id("kotlinx-serialization") version "1.3.30"
    id("multiplatform-dependency") version "0.0.1"
    id("com.diffplug.gradle.spotless") version "3.23.0"
    //    id("org.jetbrains.kotlin.native.cocoapods") version "1.3.30"
    `maven-publish`
}

apply("android-config.gradle")

val lib_version: String by project
val lib_group: String by project
val lib_artifact_id: String by project
val framework_name: String by project

val includeiOS: String by project
val includeAndroid: String by project

group = lib_group
version = lib_version

buildscript {
    repositories {
        maven { url = uri("http://kotlin.bintray.com/kotlin-eap") }
        maven { url = uri("http://kotlin.bintray.com/kotlin-dev") }
        maven { url = uri("https://dl.bintray.com/jetbrains/kotlin-native-dependencies") }
        maven { url = uri("https://kotlin.bintray.com/kotlinx") }
        maven { url = uri("https://kotlin.bintray.com/ktor") }

        google()
        jcenter()
        mavenCentral()
        mavenLocal()
    }

    dependencies {
        classpath("com.vhrdina.multiplatform:multiplatform-dependency:0.0.1")
        classpath("com.android.tools.build:gradle:3.4.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.30")
        classpath("org.jetbrains.kotlin:kotlin-native-gradle-plugin:1.3.30")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.3.30")
    }
}

allprojects {
    repositories {
        maven { url = uri("https://kotlin.bintray.com/kotlinx") }
        maven { url = uri("http://kotlin.bintray.com/kotlin-eap") }
        maven { url = uri("http://kotlin.bintray.com/kotlin-dev") }
        maven { url = uri("https://dl.bintray.com/kotlin/ktor") }

        google()
        jcenter()
        mavenCentral()
        mavenLocal()
    }
}


kotlin {

    if (includeAndroid == "true") {
        val android = android("android") {
            publishLibraryVariants("release", "debug")
        }

        val jvm = jvm("jvm")
    }

    if (includeiOS == "true") {
        // ios device
        val iosArm32 = iosArm32("iosArm32")

        // ios device
        val iosArm64 = iosArm64("iosArm64")
        //ios simulator
        val iosX64 = iosX64("iosX64")

        configure(listOf(iosArm32, iosArm64, iosX64)) {
            binaries.framework {
                baseName = framework_name
                val isSimulator = targetName == "iosX64"

                if (isSimulator) {
                    embedBitcode(Framework.BitcodeEmbeddingMode.DISABLE)
                }
            }
        }

        val fullFat = arrayOf(iosArm32, iosArm64, iosX64)
        val deviceSimulator64 = arrayOf(iosArm64, iosX64)
        val deviceOnly = arrayOf(iosArm32, iosArm64)

        createFatFramework(FatFrameworkConfig.FullDebug(frameworkName = framework_name, frameworks = fullFat))
        createFatFramework(FatFrameworkConfig.FullRelease(frameworkName = framework_name, frameworks = fullFat))
        createFatFramework(
            FatFrameworkConfig.DeviceSimulator64Debug(
                frameworkName = framework_name,
                frameworks = deviceSimulator64
            )
        )
        createFatFramework(
            FatFrameworkConfig.DeviceSimulator64Release(
                frameworkName = framework_name,
                frameworks = deviceSimulator64
            )
        )
        createFatFramework(FatFrameworkConfig.Device32x64Debug(frameworkName = framework_name, frameworks = deviceOnly))
        createFatFramework(
            FatFrameworkConfig.Device32x64Release(
                frameworkName = framework_name,
                frameworks = deviceOnly
            )
        )
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.Common.kotlin_stdlib)
                implementation(Dependencies.Common.coroutines_core)
                implementation(Dependencies.Common.coroutines_core_common)
                implementation(Dependencies.Common.serialization_runtime)
                implementation(Dependencies.Common.ktor_client_json)
                implementation(Dependencies.Common.ktor_client_mock)
                implementation("com.vhrdina.multiplatform:network:1.0.0")
                implementation("com.vhrdina.multiplatform:util:1.0.0")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(Dependencies.Common.Test.koltin_test)
                implementation(Dependencies.Common.Test.annotations)
            }
        }

        if (includeAndroid == "true") {
            val jvmMain by getting {
                dependsOn(commonMain)
                dependencies {
                    implementation(Dependencies.Android.kotlin_stdlib)
                    implementation(Dependencies.Android.coroutines_core)
                    implementation(Dependencies.Android.serialization)
                    implementation(Dependencies.Android.ktor_client_android)
                    implementation(Dependencies.Android.ktor_client_json)
                    implementation(Dependencies.Android.ktor_client_logging_jvm)
                    implementation(Dependencies.Android.ktor_client_mock_jvm)
                    implementation(Dependencies.Android.ktor_client_okhttp)
                }
            }

            val androidMain by getting {
                dependsOn(commonMain)
                dependencies {
                    implementation(Dependencies.Android.kotlin_stdlib)
                    implementation(Dependencies.Android.coroutines_android)
                    implementation(Dependencies.Android.coroutines_core)
                    implementation(Dependencies.Android.ktor_client_android)
                    implementation(Dependencies.Android.ktor_client_json)
                    implementation(Dependencies.Android.ktor_client_logging_jvm)
                    implementation(Dependencies.Android.ktor_client_mock_jvm)
                    implementation(Dependencies.Android.ktor_client_okhttp)
                    implementation("androidx.annotation:annotation:1.0.1")
                }
            }

            val androidTest by getting {
                dependencies {
                    implementation(Dependencies.Android.Test.kotlin_test)
                    implementation(Dependencies.Android.Test.junit)
                }
            }
        }

        if (includeiOS == "true") {
            val iosArm64Main by getting {
                dependsOn(commonMain)
                dependencies {
                    implementation(Dependencies.iOS.coroutines_core)
                    implementation(Dependencies.iOS.serialization_native)
                    implementation(Dependencies.iOS.ktor_client_core)
                    implementation(Dependencies.iOS.ktor_client_ios)
                    implementation(Dependencies.iOS.ktor_client_json)
                    implementation(Dependencies.iOS.ktor_client_logging)
                    implementation(Dependencies.iOS.ktor_client_mock)
                }
            }

            val iosArm64Test by getting {
            }

            val iosX64Main by getting {
                dependsOn(iosArm64Main)
            }

            val iosX64Test by getting {
            }

            val iosArm32Main by getting {
                dependsOn(iosArm64Main)
            }

            val iosArm32Test by getting {
            }

        }

    }

    if (includeiOS == "true") {
        tasks.build.dependsOn("fatFrameworkFullDebug")
        tasks.build.dependsOn("fatFrameworkFullRelease")
        tasks.build.dependsOn("fatFrameworkDeviceSimulator64Debug")
        tasks.build.dependsOn("fatFrameworkDeviceSimulator64Release")
        tasks.build.dependsOn("fatFrameworkDevice32x64Debug")
        tasks.build.dependsOn("fatFrameworkDevice32x64Release")
    }

//        cocoapods {
//            summary = "Einstore SDK"
//            homepage = "https://github.com/einstore"
//
//            pod("einstore-sdk", "0.0.1")
//        }

}

fun createFatFramework(config: FatFrameworkConfig) {
    tasks.create(config.taskName, FatFrameworkTask::class) {
        baseName = framework_name
        destinationDir = buildDir.resolve(config.getPath())
        from(
            config.frameworks.map { it.binaries.getFramework(config.buildType) }
        )
    }
}

spotless {
    kotlin {
        target("**/*.kt")
        ktlint("0.31.0").userData(mapOf("indent_size" to "2"))
        trimTrailingWhitespace()
        endWithNewline()
    }
}