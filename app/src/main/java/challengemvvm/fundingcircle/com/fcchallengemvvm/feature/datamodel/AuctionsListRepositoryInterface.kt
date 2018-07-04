package challengemvvm.fundingcircle.com.fcchallengemvvm.feature.datamodel

/**
 * Created by david.martinezgarcia on 21/04/2018.
 */
interface AuctionsListRepositoryInterface {
    fun retrieveAuctions(callback: AuctionsRepositoryCallback)
}
