plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.example.data"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.test:core-ktx:1.4.0")
    testImplementation("junit:junit:4.12")
    val composeVersionUi = rootProject.extra["compose_ui_version"]
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    //testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //retrofit
    implementation(Depedencies.retrofit2)
    implementation(Depedencies.retrofit2_converter_gson)

    //room
    implementation(Depedencies.room_library)
    annotationProcessor(Depedencies.room_compiler)
    kapt(Depedencies.room_kapt_compiler)
    implementation(Depedencies.room_ktx)
    implementation("androidx.compose.ui:ui:$composeVersionUi")

    //hilt
    implementation(Depedencies.hilt_version)
    kapt(Depedencies.hilt_version_kapt)
    
    //test
    testImplementation (Depedencies.mockK_version)
    testImplementation (Depedencies.mockK_android)
    testImplementation (Depedencies.mockK_agent)
    testImplementation(Depedencies.jupiter_library)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    testImplementation ("org.robolectric:robolectric:4.9")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation ("com.squareup.okhttp3:mockwebserver:3.8.1")
}