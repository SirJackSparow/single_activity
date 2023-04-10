plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.example.fendy"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = "com.example.fendy"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val composeVersionUi = rootProject.extra["compose_ui_version"]
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.compose.ui:ui:$composeVersionUi")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersionUi")
    implementation("androidx.compose.material:material:1.2.0")
    //testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersionUi")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersionUi")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersionUi")

    //hilt
    implementation(Depedencies.hilt_version)
    kapt(Depedencies.hilt_version_kapt)
    implementation(Depedencies.hilt_navigation_compose)
    implementation(Depedencies.navigation_compose)

    implementation(project(":libraries:shared"))
    implementation(project(":libraries:navigation"))
    implementation(project(":features:list"))
    implementation(project(":features:detail"))


}