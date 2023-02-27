plugins {
    @Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
    alias(libs.plugins.kotlin.jvm)
    kotlin("plugin.allopen") version "1.7.22"
    id("io.quarkus")
    kotlin("plugin.serialization") version "1.7.22"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.7.22"
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-rest-client-reactive")
    implementation("io.quarkus:quarkus-resteasy-reactive")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-config-yaml")
    implementation("io.quarkus:quarkus-liquibase")
    implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("io.quarkus:quarkus-resteasy-reactive-kotlin-serialization")
    implementation("io.quarkus:quarkus-rest-client-reactive-kotlin-serialization")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-hibernate-reactive-panache-kotlin")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("io.quarkus:quarkus-reactive-pg-client")

    implementation(libs.kotlinCoroutines)



    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("io.rest-assured:kotlin-extensions")
    testImplementation("io.quarkus:quarkus-panache-mock")
    testImplementation(libs.wiremockJre)
    testImplementation(libs.wireMockKotlin)
    testImplementation(libs.kluent)
    testImplementation(libs.mockk)
    testImplementation(libs.mockkQuarkus)
    testImplementation(libs.testContainerTestcontainers)
    testImplementation(libs.testContainerJunitJupiter)
    testImplementation(libs.testContainerPostgres)
    testImplementation(libs.kotlinCoroutinesTest)
}

group = "de.ma.tw.api"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")

}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    kotlinOptions.javaParameters = true
}


configure<org.jetbrains.kotlin.noarg.gradle.NoArgExtension> {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")

}
