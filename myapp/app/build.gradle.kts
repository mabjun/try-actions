plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("production") {
            val productionStoreFile: String by project
            val productionStorePassword: String by project
            val productionKeyAlias: String by project
            val productionKeyPassword: String by project
            storeFile = file(productionStoreFile)
            storePassword = productionStorePassword
            keyAlias = productionKeyAlias
            keyPassword = productionKeyPassword
        }
        create("staging") {
            val stagingStoreFile: String by project
            val stagingStorePassword: String by project
            val stagingKeyAlias: String by project
            val stagingKeyPassword: String by project
            storeFile = file(stagingStoreFile)
            storePassword = stagingStorePassword
            keyAlias = stagingKeyAlias
            keyPassword = stagingKeyPassword
        }
        create("sandbox") {
            val sandboxStoreFile: String by project
            val sandboxStorePassword: String by project
            val sandboxKeyAlias: String by project
            val sandboxKeyPassword: String by project
            storeFile = file(sandboxStoreFile)
            storePassword = sandboxStorePassword
            keyAlias = sandboxKeyAlias
            keyPassword = sandboxKeyPassword
        }
        create("develop") {
            val developStoreFile: String by project
            val developStorePassword: String by project
            val developKeyAlias: String by project
            val developKeyPassword: String by project
            storeFile = file(developStoreFile)
            storePassword = developStorePassword
            keyAlias = developKeyAlias
            keyPassword = developKeyPassword
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
        viewBinding = true
    }

    flavorDimensions += listOf("default")
    productFlavors {
        create("production") {
            dimension = "default"
            signingConfig = signingConfigs.getByName("production")
        }
        create("staging") {
            dimension = "default"
            applicationIdSuffix = "staging"
            versionNameSuffix = "staging"
            signingConfig = signingConfigs.getByName("staging")
        }
        create("sandbox") {
            dimension = "default"
            applicationIdSuffix = "sandbox"
            versionNameSuffix = "sandbox"
            signingConfig = signingConfigs.getByName("sandbox")
        }
        create("develop") {
            dimension = "default"
            applicationIdSuffix = "develop"
            versionNameSuffix = "develop"
            signingConfig = signingConfigs.getByName("develop")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

tasks.register("printVersionName") {
    println(android.defaultConfig.versionName)
}
