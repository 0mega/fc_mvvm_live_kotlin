package challengemvvm.fundingcircle.com.fcchallengemvvm.feature.datamodel

import challengemvvm.fundingcircle.com.fcchallengemvvm.model.networkmodel.AuctionsEndPoint
import io.reactivex.Observable
/**
 * Created by david.martinezgarcia on 21/04/2018.
 */
interface AuctionsListRepositoryInterface {
    fun retrieveAuctions() : Observable<AuctionsEndPoint>
}
