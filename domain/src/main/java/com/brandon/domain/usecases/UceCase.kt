package com.brandon.domain.usecases

abstract class UseCase<in Param, out Result> {
    abstract suspend fun execute(params: Param): Result
}