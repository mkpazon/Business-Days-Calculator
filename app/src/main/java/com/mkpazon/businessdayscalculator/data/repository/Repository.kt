package com.mkpazon.businessdayscalculator.data.repository

import com.mkpazon.businessdayscalculator.data.model.Holiday
import com.mkpazon.businessdayscalculator.test.Mocks
import kotlinx.coroutines.*

class Repository {


    suspend fun getNSWHolidaysAsync(): Deferred<List<Holiday>> {
        return coroutineScope {
            async(Dispatchers.Default) {
                // TODO remove fake delay eventually
                delay(800)
                Mocks.getNSWHolidays()
            }
        }
    }
}