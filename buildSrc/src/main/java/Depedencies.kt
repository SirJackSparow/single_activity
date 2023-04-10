import Version.compose_nav_hilt
import Version.jupiter_test
import Version.navigation_compose_version
import Version.retrofit_version
import Version.room_version
import Version.version_hilt

object Depedencies {
    val hilt_version = "com.google.dagger:hilt-android:${version_hilt}"
    val hilt_version_kapt = "com.google.dagger:hilt-android-compiler:${version_hilt}"
    val hilt_navigation_compose = "androidx.hilt:hilt-navigation-compose:${compose_nav_hilt}"
    val retrofit2 = "com.squareup.retrofit2:retrofit:${retrofit_version}"
    val retrofit2_converter_gson = "com.squareup.retrofit2:converter-gson:${retrofit_version}"
    val jupiter_library = "org.junit.jupiter:junit-jupiter:${jupiter_test}"
    val mockK_version =  "io.mockk:mockk:${Version.mockK_version}"
    val mockK_android =  "io.mockk:mockk-android:${Version.mockK_version}"
    val mockK_agent = "io.mockk:mockk-agent:${Version.mockK_version}"
    val room_library = "androidx.room:room-runtime:${room_version}"
    val room_compiler = "androidx.room:room-compiler:$room_version"
    val room_kapt_compiler = "androidx.room:room-compiler:$room_version"
    val room_ktx = "androidx.room:room-ktx:$room_version"
    val navigation_compose = "androidx.navigation:navigation-compose:$navigation_compose_version"
}

object Version {
    const val version_hilt = "2.45"
    const val compose_nav_hilt = "1.0.0"
    const val retrofit_version = "2.9.0"
    const val mockK_version = "1.13.3"
    const val jupiter_test = "5.9.1"
    const val room_version = "2.5.1"
    const val navigation_compose_version = "2.5.1"
}