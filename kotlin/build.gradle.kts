plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.5"
	id("io.spring.dependency-management") version "1.1.7"
	id("nu.studer.jooq") version "10.1.1" // plugin jOOQ
	id("org.flywaydb.flyway") version "9.22.3" // plugin Flyway
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "Kotlin project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.8.1")

    //flyWay dependency
	implementation("org.flywaydb:flyway-core:10.13.0")
	implementation("org.flywaydb:flyway-database-postgresql:10.13.0")

	runtimeOnly("org.postgresql:postgresql")

	// jOOQ codegen (nếu muốn generate từ DB schema)
	jooqGenerator("org.postgresql:postgresql")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

//tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//	dependsOn("generateJooq")
//}

jooq {
	version.set("3.19.18") // phiên bản jOOQ
	configurations {
		create("main") {
			generateSchemaSourceOnCompilation.set(true)
			jooqConfiguration.apply {
				jdbc.apply {
					driver = "org.postgresql.Driver"
					url = "jdbc:postgresql://localhost:54321/kotlin"
					user = "postgres"
					password = "postgres"
				}
				generator.apply {
					name = "org.jooq.codegen.KotlinGenerator"
					database.apply {
						inputSchema = "public"
					}
					generate.apply {
						isDaos = true
						isPojos = true
						isImmutablePojos = false
						isFluentSetters = true
					}
					target.apply {
						packageName = "com.example.kotlin"
						directory = "build/generated-src/jooq/main"
					}
				}
			}
		}
	}
}

flyway {
	url = "jdbc:postgresql://localhost:54321/kotlin"
	user = "postgres"
	password = "postgres"
	cleanDisabled = false
}


kotlin {
	sourceSets {
		main {
			kotlin.srcDir("build/generated-src/jooq/main")
		}
	}
}
