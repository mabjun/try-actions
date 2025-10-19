import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.worker8.android_lint_reporter")
}

android_lint_reporter {
    lintFilePath = "./build/reports/lint-results-developDebug.xml"
//    detektFilePath = ""
    githubOwner = "worker8"
    githubRepositoryName = "AndroidLintReporter"
    showLog = true // optional - default to false, show extra information, will slow things down
}

android {
    namespace = "com.example.myapplication"
    //noinspection GradleDependency
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 28
        //noinspection OldTargetApi
        targetSdk = 35
        versionCode = 1
        versionName = "2.0.0" // コメントくらいでは変わらない？

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
        debug {
            signingConfig = null
        }
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

    flavorDimensions += listOf("default")
    productFlavors {
        create("production") {
            dimension = "default"
            signingConfig = signingConfigs.getByName("production")
            buildConfigField("String", "URL_PORTAL_SITE", "\"https://production.mabware.com\"")
        }
        create("staging") {
            dimension = "default"
            applicationIdSuffix =   "staging"
            versionNameSuffix = "staging"
            signingConfig = signingConfigs.getByName("staging")
            buildConfigField("String", "URL_PORTAL_SITE", "\"https://staging.mabware.com\"")
        }
        create("sandbox") {
            dimension = "default"
            applicationIdSuffix =   "sandbox"
            versionNameSuffix = "sandbox"
            signingConfig = signingConfigs.getByName("sandbox")
            buildConfigField("String", "URL_PORTAL_SITE", "\"https://sandbox.mabware.com\"")
        }
        create("develop") {
            dimension = "default"
            applicationIdSuffix =   "develop"
            versionNameSuffix = "develop"
            signingConfig = signingConfigs.getByName("develop")
            buildConfigField("String", "URL_PORTAL_SITE", "\"https://develop.mabware.com\"")
        }
        create("local") {
            dimension = "default"
            applicationIdSuffix = "local"
            versionNameSuffix = "local"
            signingConfig = signingConfigs.getByName("develop")
            buildConfigField(
                "String",
                "URL_PORTAL_SITE",
                "\"${getLocalProperty("URL_PORTAL_SITE")}\""
            )

        }
    }

    lint {
        sarifReport = true
    }
}

private fun getLocalProperty(name: String): String {
    val localPropertiesFile = rootProject.file("local.properties")
    return if (localPropertiesFile.exists()) {
        Properties().apply {
            load(localPropertiesFile.inputStream())
        }.getProperty(name)
    } else {
        ""
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

// Github Actions への versionName の受け渡しに使用。
tasks.register("printVersionName") {
    println(android.defaultConfig.versionName)
}
