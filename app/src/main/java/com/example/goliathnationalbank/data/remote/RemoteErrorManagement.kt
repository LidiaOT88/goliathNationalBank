package com.example.goliathnationalbank.data.remote

object RemoteErrorManagement {

    inline fun <reified T : Any> wrap(block: () -> T): T {
        return try {
            block()

        } catch (e: Throwable) {
            throw Exception(e.message)
        }
    }

}