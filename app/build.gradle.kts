plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.ksp)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.messengerapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.messengerapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    protobuf{
        protoc {
            artifact = "com.google.protobuf:protoc:3.25.0"
        }
        generateProtoTasks {
            all().forEach {  task ->
                task.builtins {
                    register("java") {
                        option("lite")
                    }

                    register("kotlin") {
                        option("lite")
                    }
                }
            }
        }
    }
}

dependencies {
    //dagger2
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)

    //firebase-auth
    implementation(libs.firebase.auth)
    //firebase-firestore
    implementation(libs.firebase.firestore)
    //firebase-storage
    implementation(libs.firebase.storage)

    //lifecycle-viewModel-compose
    implementation(libs.androidx.lifecycle.viewmodel)

    //navigation
    implementation(libs.navigation.compose)

    //coil
    implementation(libs.coil.compose)

    //room
    implementation(libs.room)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    //dataStore, dataStoreProto
    implementation(libs.dataStore)
    implementation(libs.dataStoreProto)
    implementation(libs.protobufLite)

    //ktor
    implementation(libs.ktor.core)
    implementation(libs.ktor.cio)

    implementation(libs.ktor.okhttp)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.websockets)

    implementation(libs.ktor.logging)
    implementation(libs.ktor.content.negotiation)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.serialization)
    //okhttp
    implementation(libs.okhttp.interceptor.logging)
    implementation(libs.okhttp)
    //timber
    implementation(libs.timber)

    //accompanist-ui-controller
    implementation(libs.accompanist.system.ui.controller)




    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}