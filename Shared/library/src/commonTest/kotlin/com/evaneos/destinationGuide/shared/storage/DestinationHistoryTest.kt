package com.evaneos.destinationGuide.shared.storage

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.evaneos.destinationGuide.shared.models.Destination
import com.evaneos.destinationGuide.shared.models.DestinationDetails
import com.evaneos.destinationGuide.shared.services.DestinationHistoryService
import com.evaneos.destinationGuide.shared.services.DestinationHistoryServiceImpl
import com.evaneos.destinationGuide.shared.services.destinationDetailsStub
import com.evaneos.destinationGuide.shared.services.destinationsStub
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DestinationHistoryTest {

    val historyService: DestinationHistoryService = DestinationHistoryServiceImpl()

    @ExperimentalCoroutinesApi
    @BeforeTest
    fun beforeTest() {
        historyService.clear()
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @ExperimentalCoroutinesApi
    @AfterTest
    fun afterTest() {
        Dispatchers.resetMain()
    }

    @Test
    fun testOrderInsertAndObservation() = runTest {
        historyService.current.test {
            // insert 3 destinations
            historyService.addToHistory(destinationDetailsStub.elementAt(4))
            historyService.addToHistory(destinationDetailsStub.elementAt(1))
            historyService.addToHistory(destinationDetailsStub.elementAt(6))
            // at first the history must be empty
            assertTrue(awaitItem().isEmpty(), "The history must be empty at first")
            // check if the LIFO insertion is respected
            assertEquals(destinationDetailsStub.elementAt(4).id, awaitItem().first().id)
            assertEquals(destinationDetailsStub.elementAt(1).id, awaitItem().first().id)
            assertEquals(destinationDetailsStub.elementAt(6).id, awaitItem().first().id)
        }
    }
}