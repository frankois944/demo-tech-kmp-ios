package com.evaneos.destinationGuide.shared.services

// Convert a Swift Class to Kotlin Class

import com.evaneos.destinationGuide.shared.models.Destination
import com.evaneos.destinationGuide.shared.models.DestinationDetails
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

public enum class DestinationFetchingServiceError {
    DestinationNotFound;
}

// replace Swift Result with Kotlin sealed class
public sealed class GetDestinationsResult {
    public class Success(public val destinations: Set<Destination>) :
        GetDestinationsResult()

    public class Failure(public val error: DestinationFetchingServiceError) :
        GetDestinationsResult()
}

// replace Swift Result with Kotlin sealed class
public sealed class GetDestinationDetailsResult {
    public class Success(public val destinationDetails: DestinationDetails) :
        GetDestinationDetailsResult()

    public class Failure(public val error: DestinationFetchingServiceError) :
        GetDestinationDetailsResult()
}

public interface DestinationFetchingService {
    public fun getDestinations(completion: (GetDestinationsResult) -> Unit)
    public fun getDestinationDetails(
        destinationId: String,
        completion: (GetDestinationDetailsResult) -> Unit
    )
}

public class DestinationFetchingServiceImpl : DestinationFetchingService {
    override fun getDestinations(completion: (GetDestinationsResult) -> Unit) {
        val extraSeconds = Random.nextLong(1L, 4L)
        GlobalScope.launch {
            delay(extraSeconds)
            completion(GetDestinationsResult.Success(destinationsStub))
        }
    }

    override fun getDestinationDetails(
        destinationId: String,
        completion: (GetDestinationDetailsResult) -> Unit
    ) {
        val extraSeconds = Random.nextLong(1L, 4L)
        GlobalScope.launch {
            delay(extraSeconds)
            val destinationDetail = destinationDetailsStub.firstOrNull {
                it.id == destinationId
            }
            completion(destinationDetail?.let {
                GetDestinationDetailsResult.Success(it)
            }
                ?: GetDestinationDetailsResult.Failure(DestinationFetchingServiceError.DestinationNotFound)
            )
        }
    }
}

internal val destinationsStub: Set<Destination> = setOf(
    Destination(
        id = "217",
        name = "Barbade",
        picture = "https://static1.evcdn.net/images/reduction/1027399_w-800_h-800_q-70_m-crop.jpg",
        tag = "Incontournable",
        rating = 5
    ),
    Destination(
        id = "50",
        name = "Arménie",
        picture = "https://static1.evcdn.net/images/reduction/1544481_w-800_h-800_q-70_m-crop.jpg",
        tag = "Incontournable",
        rating = 4
    ),
    Destination(
        id = "6",
        name = "Allemagne",
        picture = "https://static1.evcdn.net/images/reduction/1027397_w-800_h-800_q-70_m-crop.jpg",
        tag = "Incontournable",
        rating = 5
    ),
    Destination(
        id = "306",
        name = "Bali",
        picture = "https://static1.evcdn.net/images/reduction/1581674_w-800_h-800_q-70_m-crop.jpg",
        tag = "Incontournable",
        rating = 4
    ),
    Destination(
        id = "13",
        name = "Autriche",
        picture = "https://static1.evcdn.net/images/reduction/354894_w-800_h-800_q-70_m-crop.jpg",
        tag = "Incontournable",
        rating = 5
    ),
    Destination(
        id = "147",
        name = "Antilles",
        picture = "https://static1.evcdn.net/images/reduction/397848_w-800_h-800_q-70_m-crop.jpg",
        tag = "Incontournable",
        rating = 4
    ),
    Destination(
        id = "373",
        name = "Basse-Californie",
        picture = "https://static1.evcdn.net/images/reduction/1596154_w-800_h-800_q-70_m-crop.jpg",
        tag = "Incontournable",
        rating = 4
    ),
    Destination(
        id = "73",
        name = "Afrique du Sud",
        picture = "https://static1.evcdn.net/images/reduction/1506493_w-800_h-800_q-70_m-crop.jpg",
        tag = "Incontournable",
        rating = 5
    ),
    Destination(
        id = "98",
        name = "Australie",
        picture = "https://static1.evcdn.net/images/reduction/635304_w-800_h-800_q-70_m-crop.jpg",
        tag = "Incontournable",
        rating = 4
    ),
    Destination(
        id = "426",
        name = "Amazonie Brésilienne",
        picture = "https://static1.evcdn.net/images/reduction/1595441_w-800_h-800_q-70_m-crop.jpg",
        tag = "Incontournable",
        rating = 4
    ),
    Destination(
        id = "377",
        name = "Bajio",
        picture = "https://static1.evcdn.net/images/reduction/1596170_w-800_h-800_q-70_m-crop.jpg",
        tag = "Incontournable",
        rating = 4
    ),
    Destination(
        id = "74",
        name = "Azerbaïdjan",
        picture = "https://static1.evcdn.net/images/reduction/611704_w-800_h-800_q-70_m-crop.jpg",
        tag = "Incontournable",
        rating = 5
    ),
    Destination(
        id = "115",
        name = "Antarctique",
        picture = "https://static1.evcdn.net/images/reduction/210925_w-800_h-800_q-70_m-crop.jpg",
        tag = "Incontournable",
        rating = 4
    ),
    Destination(
        id = "110",
        name = "Bangladesh",
        picture = "https://static1.evcdn.net/images/reduction/356979_w-800_h-800_q-70_m-crop.jpg",
        tag = "Incontournable",
        rating = 4
    ),
    Destination(
        id = "29",
        name = "Algérie",
        picture = "https://static1.evcdn.net/images/reduction/1230836_w-800_h-800_q-70_m-crop.jpg",
        tag = "Incontournable",
        rating = 5
    ),
    Destination(
        id = "75",
        name = "Argentine",
        picture = "https://static1.evcdn.net/images/reduction/904030_w-800_h-800_q-70_m-crop.jpg",
        tag = "Incontournable",
        rating = 5
    ),
    Destination(
        id = "173",
        name = "Açores",
        picture = "https://static1.evcdn.net/images/reduction/356685_w-800_h-800_q-70_m-crop.jpg",
        tag = "Incontournable",
        rating = 4
    ),
    Destination(
        id = "170",
        name = "Albanie",
        picture = "https://static1.evcdn.net/images/reduction/413980_w-800_h-800_q-70_m-crop.jpg",
        tag = "Incontournable",
        rating = 4
    ),
    Destination(
        id = "287",
        name = "Angleterre",
        picture = "https://static1.evcdn.net/images/reduction/609757_w-800_h-800_q-70_m-crop.jpg",
        tag = "Incontournable",
        rating = 4
    ),
    Destination(
        id = "107",
        name = "Bahamas",
        picture = "https://static1.evcdn.net/images/reduction/39034_w-800_h-800_q-70_m-crop.jpg",
        tag = "Incontournable",
        rating = 5
    )
)

internal var destinationDetailsStub: Set<DestinationDetails> = setOf(
    DestinationDetails(id = "217", name = "Barbade", url = "https://evaneos.fr/barbade"),
    DestinationDetails(id = "50", name = "Arménie", url = "https://evaneos.fr/armenie"),
    DestinationDetails(id = "6", name = "Allemagne", url = "https://evaneos.fr/allemagne"),
    DestinationDetails(id = "306", name = "Bali", url = "https://evaneos.fr/bali"),
    DestinationDetails(id = "13", name = "Autriche", url = "https://evaneos.fr/autriche"),
    DestinationDetails(id = "147", name = "Antilles", url = "https://evaneos.fr/antilles"),
    DestinationDetails(
        id = "373",
        name = "Basse-Californie",
        url = "https://evaneos.fr/basse-californie"
    ),
    DestinationDetails(
        id = "73",
        name = "Afrique du Sud",
        url = "https://evaneos.fr/afrique-du-sud"
    ),
    DestinationDetails(id = "98", name = "Australie", url = "https://evaneos.fr/australie"),
    DestinationDetails(
        id = "426",
        name = "Amazonie Brésilienne",
        url = "https://evaneos.fr/amazonie-bresilienne"
    ),
    DestinationDetails(id = "377", name = "Bajio", url = "https://evaneos.fr/bajio"),
    DestinationDetails(id = "74", name = "Azerbaïdjan", url = "https://evaneos.fr/azerbaidjan"),
    DestinationDetails(id = "115", name = "Antarctique", url = "https://evaneos.fr/antarctique"),
    DestinationDetails(id = "110", name = "Bangladesh", url = "https://evaneos.fr/bangladesh"),
    DestinationDetails(id = "29", name = "Algérie", url = "https://evaneos.fr/algerie"),
    DestinationDetails(id = "75", name = "Argentine", url = "https://evaneos.fr/argentine"),
    DestinationDetails(id = "173", name = "Açores", url = "https://evaneos.fr/acores"),
    DestinationDetails(id = "287", name = "Angleterre", url = "https://evaneos.fr/angleterre"),
    DestinationDetails(id = "107", name = "Bahamas", url = "https://evaneos.fr/bahamas")
)
