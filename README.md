# Introduction 
This project was created just to demonstrate my coding skills.

# Getting Started
1.	Use Android Studio 3.5+
2.	In case of problems with Kotlin DSL:
    - Build > Clean + Gradle Sync
    - File > Invalidate cache and restart

# How to run the tests and generate coverage report
1.  Run an Android AVD with API 24+
2.  Open terminal
2.  Run ./coverage.sh
3.  It will disable animations, run tests, generate full jacoco coverage report and print report file path.

# Libraries
In this project, these open-source libraries were used:
- Coroutines: It is a kotlin native library that allows to run asynchronous tasks in a simple way
- Glide: Library that allows to load remote images
- Mockk: Allows to mock kotlin classes/objects/extensions
- Koin: Dependency injection library


# Other notes
- I kept keystore.properties on git just to make it easy to share, but it is not recommended in a real product. 