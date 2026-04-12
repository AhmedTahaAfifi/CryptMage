package com.example.cryptmage.domain.exception

import com.example.cryptmage.R

sealed class AppException(open val messageId: Int = 0): Exception()

class VaultEmptyException: AppException()


/***************************************
 * Auth Exceptions
 **************************************/

class InvalidPasswordException(override val messageId: Int = R.string.error_invalid_password): AppException()

class SomeThingWentWrongException(override val messageId: Int = R.string.something_went_wrong): AppException()