package com.example.cryptmage.domain.exception

sealed class AppException(open val messageId: Int? = null): Exception()

class VaultEmptyException: AppException()

class SomeThingWentWrongException(override val messageId: Int? = null): AppException()