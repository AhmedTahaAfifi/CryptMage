# CryptMage 🛡️✨
**Vault. Cipher. Guard.**

CryptMage is a high-security, local-first password manager built for Android. It leverages industry-standard cryptography and modern Android architectural patterns to ensure user data remains private, secure, and accessible only to the owner.

![Banner](https://github.com/yourusername/CryptMage/raw/main/screenshots/banner.png) <!-- Optional: Add a screenshot here -->

## 🚀 Key Features
- **Zero-Knowledge Security:** All data is encrypted locally. The encryption key is derived from the user's master password and is never stored on the device.
- **Full Database Encryption:** Uses **SQLCipher** to encrypt the entire Room database with AES-256.
- **Hardware-Backed Protection:** Secure management of cryptographic salts using the **Android KeyStore System**.
- **Modern UI:** Built entirely with **Jetpack Compose** following Material 3 guidelines.
- **Auto-Locking:** Intelligent session management that locks the vault when the app enters the background.
- **Privacy Guard:** Prevents screenshots and hides sensitive data in the "Recent Apps" switcher using `FLAG_SECURE`.

## 🏗️ Technical Stack
- **Language:** Kotlin
- **UI:** Jetpack Compose (100%)
- **Architecture:** MVVM + Clean Architecture + Single Activity
- **Dependency Injection:** Koin
- **Persistence:** Room Database + SQLCipher
- **Asynchronous Logic:** Coroutines & Flow
- **Key Derivation:** PBKDF2 (via BouncyCastle)
- **UI Scaling:** SDP/SSP for responsive layouts across all screen sizes.

## 🔒 Security Model
CryptMage implements a multi-layered security approach:

1. **Master Key Derivation:** Uses **PBKDF2WithHmacSHA256** with a unique salt to derive a 256-bit AES key from the user's password.
2. **Salt Protection:** The salt is encrypted using a hardware-backed AES key generated via `KeyGenParameterSpec` in the Android KeyStore before being saved to storage.
3. **Memory Safety:** The derived encryption key exists in memory as a `ByteArray` only during the database initialization phase and is zeroed out immediately after use to prevent memory dump attacks.
4. **Lazily-Loaded Vault:** Database operations are restricted until the user provides the correct master key to unlock the SQLCipher open helper.

## 🗺️ Architecture Overview
The project follows **Google's official architecture guidance** for modern Android development:

- **UI Layer:** Composable screens observing `StateFlow` from ViewModels. Implements Unidirectional Data Flow (UDF).
- **Domain Layer:** Business logic encapsulated in `Managers` and `Requests` handlers to keep ViewModels clean and testable.
- **Data Layer:** Repository pattern mediating between the UI and the encrypted Room database.

```text
app/
├── data/
│   ├── dao/          # Data Access Objects
│   ├── database/     # Room Database configuration
│   ├── di/           # Koin Modules (Dependency Injection)
│   ├── models/       # Database Entities
│   └── repository/   # Repository Pattern implementation
├── domain/
│   ├── exception/    # Custom App Exceptions
│   └── requests/     # Standardized Coroutine wrapper logic
├── ui/
│   ├── activities/   # Single Activity Entry point
│   ├── component/    # Reusable Stateless Composables
│   ├── navGraph/     # Type-safe Navigation setup
│   └── screens/      # Feature-specific UI (Login, Home, Generator)
└── utils/            # Cryptography and Helper utilities
```

## 🛠️ Installation
1. Clone the repository.
2. Open in Android Studio.
3. Sync Project with Gradle Files.
4. Run on a physical device or emulator (API 29+).

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
