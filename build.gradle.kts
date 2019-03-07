import edu.sc.seis.launch4j.Launch4jPlugin
import edu.sc.seis.launch4j.tasks.DefaultLaunch4jTask
import edu.sc.seis.launch4j.tasks.Launch4jExternalTask
import org.gradle.launcher.daemon.protocol.Build
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    val repos by extra { listOf("http://maven.aliyun.com/nexus/content/groups/public","https://jcenter.bintray.com/") }
    val kotlinVersion = "1.3.21"
    extra["kotlin.version"] = kotlinVersion
    repositories { for (u in repos) { maven(u) } }
    dependencies{
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
    }
}

plugins {
    application
    java
    val kotlinVersion = "1.3.21"
    kotlin("plugin.spring") version kotlinVersion
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    id("org.springframework.boot") version "2.1.3.RELEASE"
    id("edu.sc.seis.launch4j") version "2.4.4"
}

apply(plugin = "io.spring.dependency-management")

repositories {
    mavenCentral()
}

//val nativeBundle by configurations.creating

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("net.java.dev.jna:jna:4.5.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    nativeBundle("sigar:sigar:1.6.4:native")
}
//val nativeLibsDir = "$buildDir/libs/natives"
//val extractNativeBundle by tasks.creating(Sync::class) {
//    from(nativeBundle.asFileTree)
//    into(nativeLibsDir)
//}
//tasks{
//    getByName("assembleDist"){
//        dependsOn("extractNativeBundle")
//    }
//}

tasks.withType(KotlinCompile::class.java){kotlinOptions.jvmTarget = "1.8"}
tasks.withType(KotlinCompile::class.java){kotlinOptions.jvmTarget = "1.8"}





application {
    mainClassName = "com.aplus.jx.identity.reader.IdentityReaderApplicationKt"
}
application.applicationName = "IdentityReader"

apply(plugin = "edu.sc.seis.launch4j")

//val packClassPath = mutableSetOf()

val copyJre by tasks.creating(Copy::class){
    from("${projectDir}/jre")
    into("${buildDir}/launch4j/jre")
    include("**")
}

val copyLibs by tasks.creating(Copy::class){
    from("${projectDir}/lib")
    into("${buildDir}/launch4j/jl")
    include("**")
}

tasks.withType(DefaultLaunch4jTask::class.java ){
    dependsOn(":bootJar")
    dependsOn(":copyJre")
    dependsOn(":copyLibs")
    outfile = "IdentityReader.exe"
    mainClassName = "org.springframework.boot.loader.JarLauncher"
    icon = "${buildDir}\\resources\\main\\icons\\id_card.ico"
    productName = "IdentityReader"
    headerType = "console"
    bundledJrePath = "./jre"
    val outDir = Launch4jPlugin.workdir()
    jvmOptions = setOf("-Ddll_path=$outDir")
}

tasks.withType(Launch4jExternalTask::class.java ){
    launch4jCmd = "-Pl4j-debug"
}

//task("build")