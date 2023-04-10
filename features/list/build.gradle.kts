plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.example.list"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    val composeVersionUi = rootProject.extra["compose_ui_version"]
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    //implementation("com.google.android.material:material:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //hilt
    implementation(Depedencies.hilt_version)
    kapt(Depedencies.hilt_version_kapt)
    implementation(Depedencies.hilt_navigation_compose)
    implementation(Depedencies.navigation_compose)

    implementation("androidx.compose.ui:ui:$composeVersionUi")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersionUi")
    implementation("androidx.compose.material3:material3:1.1.0-beta01")
    implementation("io.coil-kt:coil-compose:2.3.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersionUi")

    //test
    testImplementation(Depedencies.mockK_version)
    testImplementation(Depedencies.mockK_android)
    testImplementation(Depedencies.mockK_agent)
    //testImplementation(Depedencies.jupiter_library)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")

    implementation(project(":libraries:shared"))
    implementation(project(":libraries:navigation"))
}