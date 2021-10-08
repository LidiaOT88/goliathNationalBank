package com.example.goliathnationalbank.other

object Constants {
    const val BASE_URL = "http://quiet-stone-2094.herokuapp.com/"
    const val DATABASE_NAME = "gnb_database"

    object RateConstants {
        const val EUR = "EUR"
        const val USD = "USD"
        const val CAD = "CAD"
        const val AUD = "AUD"
    }

    object ErrorMessage {
        const val ERROR_GETTING_DATA = "Imposible recuperar los datos."
        const val ERROR_NO_DATA = "No hay datos almacenado."
    }
}