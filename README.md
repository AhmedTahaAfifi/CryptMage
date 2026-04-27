# CryptMage 🛡️✨
**Vault. Cipher. Guard.**

CryptMage is a high-security, local-first password manager built for Android. It leverages industry-standard cryptography and modern Android architectural patterns to ensure user data remains private, secure, and accessible only to the owner.

## 🚀 Key Features
- **Zero-Knowledge Security:** All data is encrypted locally. The encryption key is derived from the user's master password and is never stored on the device.
- **Full Database Encryption:** Uses **SQLCipher** to encrypt the entire Room database with AES-256.
- **Cloud Sync:** Secure backup and restore using **Google Drive (App Data Folder)**. Backups are encrypted before upload, ensuring Google never sees your passwords.
- **Biometric Unlock:** Quick and secure access using Fingerprint or Face Unlock, with master password fallback.
- **Hardware-Backed Protection:** Secure management of cryptographic salts using the **Android KeyStore System**.
- **Modern UI:** Built entirely with **Jetpack Compose** following Material 3 guidelines.
- **Auto-Locking:** Intelligent session management that locks the vault after a period of inactivity or when the app enters the background.
- **Privacy Guard:** Prevents screenshots and hides sensitive data in the "Recent Apps" switcher.

## 🏗️ Technical Stack
- **Language:** Kotlin
- **UI:** Jetpack Compose (100%)
- **Architecture:** MVVM + Clean Architecture + Single Activity
- **Dependency Injection:** Koin
- **Persistence:** Room Database + SQLCipher
- **Authentication:** Android Biometric API + Credential Manager
- **Cloud Integration:** Google Drive API (REST)
- **Asynchronous Logic:** Coroutines & Flow
- **Key Derivation:** PBKDF2 (via BouncyCastle)
- **UI Scaling:** SDP/SSP for responsive layouts.

## 🔒 Security Model
CryptMage implements a multi-layered security approach:

1. **Master Key Derivation:** Uses **PBKDF2WithHmacSHA256** with a unique salt to derive a 256-bit AES key from the user's password.
2. **Salt Protection:** The salt is encrypted using a hardware-backed AES key generated via `KeyGenParameterSpec` in the Android KeyStore before being saved to storage.
3. **Biometric Encryption:** When enabled, the master password is encrypted using a biometric-bound key from the KeyStore. This allows biometric login without storing the master password in plain text.
4. **Cloud Security:** Database backups uploaded to Google Drive remain encrypted with your master key. Even if your Google account is compromised, your vault remains unreadable without the master password.
5. **Memory Safety:** Sensitive keys exist in memory as `ByteArray`s only when needed and are zeroed out or cleared as soon as the session is locked.

## 🗺️ Architecture Overview
The project follows **Google's official architecture guidance**:

- **UI Layer:** Composable screens observing `StateFlow` from ViewModels.
- **Domain Layer:** Business logic encapsulated in Use Cases and Repository interfaces.
- **Data Layer:** Repository implementations handling data sources (Room, SharedPrefs, Google Drive).

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
│   ├── requests/     # Standardized Coroutine wrapper logic
│   └── usecases/     # Reusable business logic units
├── ui/
│   ├── activities/   # Single Activity Entry point
│   ├── component/    # Reusable Stateless Composables
│   ├── navGraph/     # Type-safe Navigation setup
│   └── screens/      # Feature-specific UI (Login, Home, CloudSync)
└── utils/            # Cryptography and Helper utilities
```

## 🛠️ Installation
1. Clone the repository.
2. Open in Android Studio.
3. Sync Project with Gradle Files.
4. Ensure you have a valid `google-services.json` if testing Google Drive integration in your own Firebase/Google Cloud project.
5. Run on a physical device or emulator (API 29+).

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
