import com.android.build.gradle.internal.utils.isKspPluginApplied
import com.google.devtools.ksp.gradle.model.Ksp

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotandroid)
    alias(libs.plugins.hiltApplication)
    alias(libs.plugins.hiltApplication1)

}

android {
    namespace = "com.currencyconverter"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.currencyconverter"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
    ndkVersion = "26.1.10909125"
    externalNativeBuild {
        ndkBuild {
            path = file("src/main/jni/Android.mk")
        }

    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.converter.scalars)

    implementation(libs.hilt.android)
    annotationProcessor(libs.hilt.compiler)


    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}