package com.rafaelduransaez.core.database.util

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteDatabaseLockedException
import android.database.sqlite.SQLiteDiskIOException
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteFullException
import android.provider.ContactsContract.Data
import com.rafaelduransaez.core.base.models.DatabaseError
import com.rafaelduransaez.core.base.models.JasticResult
import com.rafaelduransaez.core.base.models.JasticResult.Companion.failure
import com.rafaelduransaez.core.base.models.JasticResult.Companion.success
import com.rafaelduransaez.core.coroutines.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

suspend fun <T> safeDbCall(
    @IODispatcher dispatcher: CoroutineDispatcher = Dispatchers.IO,
    dbCall: suspend () -> T
): JasticResult<T, DatabaseError> {
    return withContext(dispatcher) {
        try {
            success(dbCall())
        } catch (e: SQLiteConstraintException) {
            failure(DatabaseError.CONSTRAINT_VIOLATION)
        } catch (e: SQLiteDatabaseCorruptException) {
            failure(DatabaseError.DATABASE_CORRUPTED)
        } catch (e: SQLiteDatabaseLockedException) {
            failure(DatabaseError.DATABASE_NOT_AVAILABLE)
        } catch (e: SQLiteDiskIOException) {
            failure(DatabaseError.DATABASE_NOT_AVAILABLE)
        } catch (e: SQLiteFullException) {
            failure(DatabaseError.DATABASE_NOT_AVAILABLE)
        } catch (e: SQLiteException) {
            if (e.message?.contains("syntax", ignoreCase = true) == true) {
                failure(DatabaseError.SQL_SYNTAX_ERROR)
            } else {
                failure(DatabaseError.UNKNOWN)
            }
        } catch (e: TimeoutCancellationException) {
            failure(DatabaseError.DATABASE_TIMEOUT)
        } catch (e: Exception) {
            failure(DatabaseError.UNKNOWN)
        }
    }
}

fun <T> safeDbFlowCall(
    dbFlowCall: () -> Flow<T>
): Flow<JasticResult<T, DatabaseError>> {
    return dbFlowCall()
        .catch { throwable ->
            when (throwable) {
                is SQLiteConstraintException -> failure(DatabaseError.CONSTRAINT_VIOLATION)
                is SQLiteDatabaseCorruptException -> failure(DatabaseError.DATABASE_CORRUPTED)
                is SQLiteDatabaseLockedException -> failure(DatabaseError.DATABASE_NOT_AVAILABLE)
                is SQLiteDiskIOException -> failure(DatabaseError.DATABASE_NOT_AVAILABLE)
                is SQLiteFullException -> failure(DatabaseError.DATABASE_NOT_AVAILABLE)
                is SQLiteException -> {
                    if (throwable.message?.contains("syntax", ignoreCase = true) == true) {
                        failure(DatabaseError.SQL_SYNTAX_ERROR)
                    } else {
                        failure(DatabaseError.UNKNOWN)
                    }
                }

                is TimeoutCancellationException -> failure(DatabaseError.DATABASE_TIMEOUT)
                else -> failure(DatabaseError.UNKNOWN)
            }
        }
        .map { success(it) }
}
