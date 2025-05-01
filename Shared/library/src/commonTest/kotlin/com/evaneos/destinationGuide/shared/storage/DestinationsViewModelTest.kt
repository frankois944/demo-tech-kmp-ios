package com.evaneos.destinationGuide.shared.storage

import app.cash.turbine.turbineScope
import com.evaneos.destinationGuide.shared.features.destinations.DestinationsViewModel
import com.evaneos.destinationGuide.shared.services.DestinationFetchingService
import com.evaneos.destinationGuide.shared.services.DestinationFetchingServiceImpl
import com.evaneos.destinationGuide.shared.services.DestinationHistoryService
import com.evaneos.destinationGuide.shared.services.DestinationHistoryServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DestinationsViewModelTest {
    private val historyService: DestinationHistoryService = DestinationHistoryServiceImpl()
    private val destinationFetchingService: DestinationFetchingService = DestinationFetchingServiceImpl()

    @ExperimentalCoroutinesApi
    @BeforeTest
    fun beforeTest() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @ExperimentalCoroutinesApi
    @AfterTest
    fun afterTest() {
        Dispatchers.resetMain()
    }

    @Test
    fun testViewModel() = runTest {
        val viewModel = DestinationsViewModel(destinationFetchingService, historyService)

        turbineScope {
            val destinations = viewModel.destinations.testIn(backgroundScope)
            val error = viewModel.error.testIn(backgroundScope)
            val history = viewModel.history.testIn(backgroundScope)

            // check initial values
            destinations.awaitEvent() // wait next event
            assertEquals(null, error.awaitItem(), "no error expected")
            assertTrue(history.awaitItem().isEmpty(), "no history expected")

            // load the list of destinations
            val loadedItems = destinations.awaitItem()
            assertTrue(loadedItems.isNotEmpty(), "the list of destination must not be empty")

            // select a valid destination
            val valid = viewModel.onSelectDestination(loadedItems.elementAt(0).id)
            assertEquals(history.awaitItem().elementAt(0).id, valid.id)
            assertEquals(loadedItems.elementAt(0).id, valid.id, "The destination detail must be valid")

            // select an invalid destination
            var expectedError: Exception? = null
            try {
                viewModel.onSelectDestination(loadedItems.elementAt(1).id)
            } catch (e: Exception) {
                expectedError = e
            } finally {
                history.expectNoEvents() // the history must not be updated
                assertEquals("DestinationNotFound", expectedError?.message, "The destination detail must not be found")
            }
        }
    }
}