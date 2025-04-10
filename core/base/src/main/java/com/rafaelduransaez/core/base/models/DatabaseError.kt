package com.rafaelduransaez.core.base.models

enum class DatabaseError: JasticFailure {
    /**
     * Error when the database is not available.
     */
    DATABASE_NOT_AVAILABLE,

    /**
     * Error when the database is corrupted.
     */
    DATABASE_CORRUPTED,

    /**
     * Error when there is a timeout while accessing the database.
     */
    DATABASE_TIMEOUT,

    /**
     * Error when there is a syntax error in the SQL query.
     */
    SQL_SYNTAX_ERROR,

    /**
     * Error when there is a constraint violation in the database.
     */
    CONSTRAINT_VIOLATION,

    /**
     * Error when there is an unknown error related to the database.
     */
    UNKNOWN
}