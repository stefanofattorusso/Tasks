package ch.protonmail.android.protonmailtest.commonandroid

import ch.protonmail.android.crypto.CryptoLib
import java.text.SimpleDateFormat
import java.util.*

fun String?.decrypt(cryptoLib: CryptoLib): String {
    if (this == null) return ""
    return cryptoLib.decrypt(this).getOrDefault("")
}

fun String?.encrypt(cryptoLib: CryptoLib): String {
    if (this == null) return ""
    return cryptoLib.encrypt(this).getOrDefault("")
}

fun String.parseTaskDate(): Date {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    return try {
        dateFormatter.parse(this)
    } catch (e: Exception) {
        Date()
    }
}
