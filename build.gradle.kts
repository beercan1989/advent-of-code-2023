plugins {
    kotlin("jvm") version "1.9.21"
}

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    // Don't have any yet
}

// Here so what when './gradlew wrapper --gradle-version $LATEST_VERSION' is run it always uses the all distro.
tasks.withType<Wrapper>().configureEach {
    distributionType = Wrapper.DistributionType.ALL
}
