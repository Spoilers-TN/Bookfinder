# Bookfinder-Kotlin
📚 Ktor and MongoDB based app for a book exchange platform 📚
---
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/ca8a2340c844450cbb7aff0551045c21)](https://www.codacy.com/gh/Spoilers-TN/Bookfinder-Kotlin/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Spoilers-TN/Bookfinder-Kotlin&amp;utm_campaign=Badge_Grade) ![GitHub](https://img.shields.io/github/license/Spoilers-TN/Bookfinder-Kotlin?color=green) ![Database](https://img.shields.io/badge/Database-MongoDB-Green) ![Language](https://img.shields.io/badge/Language-Kotlin-purple) [![Documentation](https://img.shields.io/badge/Documentation-Dokka-purple)](https://bookfinder-docs.spoilers.tn.it/)
---

## Requirements
Place in /src/main/resources an application.properties file with the following informations:
* my.signKey = googlePublicKey
* my.encryptKey = googlePrivateKey
* my.clientDb = MongoDB Connection string
* my.database = MongoDB Database name
* my.cookieSecure = {true | false }
* my.domain = Host domain
* my.urlProvider = Callback provider

## Customize
* Change the port in `src/main/kotlin/it/tn/it/Bookfinder.kt`

## Run
* Run the server with `./gradlew run`
* Run the tests with `./gradlew test`
* Generate the documentation with `./gradlew dokka`
* Generate the jar with `./gradlew shadowJar`
* Run the jar with `java -jar build/libs/Bookfinder-Kotlin-all.jar`

## Documentation
The documentation is available at [https://bookfinder-docs.spoilers.tn.it/](https://bookfinder-docs.spoilers.tn.it/)

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

## Acknowledgments
* [Ktor](https://ktor.io/)
* [MongoDB](https://www.mongodb.com/)
* [Kotlin](https://kotlinlang.org/)
