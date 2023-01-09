package com.example.plugins

import com.example.Database
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import io.ktor.server.application.*

fun Application.configureDatabase(): Database {
    val driver: SqlDriver = NativeSqliteDriver(Database.Schema, "impressive.db")
    return Database(driver)
}
