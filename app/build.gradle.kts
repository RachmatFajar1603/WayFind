plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "com.dicoding.wayfind"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dicoding.wayfind"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", "\"https://story-api.dicoding.dev/v1/\"")
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
        viewBinding = true
        buildConfig = true

    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Button Bar Navigation
    implementation ("nl.joery.animatedbottombar:library:1.1.0")

    // DataStore DarkMode
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")

    // Mapbox
    implementation("com.mapbox.maps:android:11.4.1")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation ("com.mapbox.navigationcore:navigation:3.1.0")
    implementation ("com.mapbox.navigationcore:copilot:3.1.0")
    implementation ("com.mapbox.navigationcore:ui-maps:3.1.0")
    implementation ("com.mapbox.navigationcore:voice:3.1.0")
    implementation ("com.mapbox.navigationcore:tripdata:3.1.0")
    implementation ("com.mapbox.navigationcore:android:3.1.0")
    implementation ("com.mapbox.navigationcore:ui-components:3.1.0")
    implementation ("com.mapbox.navigationcore:navigation:3.0.2")
    implementation ("com.mapbox.navigationcore:ui-components:3.0.2")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    // Google Maps api
//    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.5.31"))
//    implementation("com.google.android.libraries.places:places:3.5.0")
//    implementation("com.google.android.gms:play-services-maps:18.2.0")
//    implementation ("com.google.maps.android:maps-ktx:5.0.0")
//    implementation("com.google.android.gms:play-services-location:18.2.0")

}