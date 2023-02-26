package com.dart69.mvvm_core.errors

abstract class AppException : Throwable {
    constructor(message: String): super(message)
    constructor() : super()
}