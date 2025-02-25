
plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.17.4" // IntelliJ plugin gradle
}

group = "org.altzet"
version = "1.0.6"

repositories {
    mavenCentral()
}

// IntelliJ plugin
intellij {

    version.set("2024.1")
    type.set("IC") // IC = Community, IU = Ultimate


    plugins.set(listOf())

    // Avtomatik ravishda since-build va until-build qiymatlarini yangilash
    updateSinceUntilBuild.set(true)
}


dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

// IntelliJ pluginni ishga tushirish uchun (sandbox rejimida)
tasks.runIde {

}

// Pluginni ZIP ko‘rinishida build qilish uchun
tasks.buildPlugin {

}
