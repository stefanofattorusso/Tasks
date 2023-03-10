package ch.protonmail.android.protonmailtest.common

sealed class ResultData<out T> {

    data class Success<out T>(val value: T) : ResultData<T>()
    data class Error(val throwable: Throwable) : ResultData<Nothing>()
}