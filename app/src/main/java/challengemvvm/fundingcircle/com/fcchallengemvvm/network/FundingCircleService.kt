package challengemvvm.fundingcircle.com.fcchallengemvvm.network

import challengemvvm.fundingcircle.com.fcchallengemvvm.model.networkmodel.AuctionsEndPoint
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by david.martinezgarcia on 21/04/2018.
 */
interface FundingCircleService {
    @GET("auctions")
    fun getAuctions(): Observable<AuctionsEndPoint>
}