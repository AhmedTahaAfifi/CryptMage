package com.example.cryptmage.domain.exception

import com.example.cryptmage.R

sealed class AppException(open val messageId: Int = 0): Exception()

class VaultEmptyException: AppException()


/***************************************
 * Auth Exceptions
 **************************************/
class InvalidMasterPasswordException : AppException(R.string.error_invalid_password)
class SaltMissingException : AppException(R.string.error_salt_missing)
class DatabaseNotUnlockedException : AppException(R.string.error_database_not_unlocked)
class InvalidCredentialTypeException : AppException(R.string.error_invalid_credential_type)

class SomeThingWentWrongException(override val messageId: Int = R.string.something_went_wrong): AppException()