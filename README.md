# ilearning-platform
A scalable e-learning Android application built with Jetpack Compose, MVVM, Clean Architecture, Hilt for dependency injection, and Room for local data storage, supporting efficient course management and offline access..

## 1. Architecture

I chose **MVVM (Model–View–ViewModel)** because it separates UI, business logic, and data handling, making the application easier to maintain, test, and scale. I used **Jetpack Compose** for the UI, a Repository pattern for data management, and **Hilt** for dependency injection.

## 2. Offline Support

Course data is stored locally in a Room database. The application loads the initial course data from a local JSON file and inserts it into Room. The UI retrieves course lists and details from the database, allowing users to access course information without an internet connection.

## 3. Security

In a production application, authentication tokens should be stored securely using **Android Keystore-backed encrypted storage**, such as EncryptedSharedPreferences where appropriate. Tokens should never be stored in plain-text SharedPreferences or hardcoded in the application.

## 4. Scale
To support 1 million users and hundreds of courses, I would improve the Android application by:

Pagination: Load courses in smaller batches instead of loading all courses at once.
Room Database: Use indexed queries and efficient data access for faster local retrieval.
Performance: Optimize Jetpack Compose recompositions and use lazy layouts to display large course lists efficiently.
Caching: Cache API responses and images to reduce network usage and improve loading speed.
Background Processing: Use Kotlin Coroutines and WorkManager for asynchronous data loading and reliable background synchronization.

## 5. Second Platform — iOS/macOS

I would develop the same application for iOS/macOS using **Swift and SwiftUI**, following the MVVM architecture. I would reuse the backend APIs and JSON data format, implement secure token storage using **Keychain**, and provide equivalent course browsing, course details, and offline support using local storage.

