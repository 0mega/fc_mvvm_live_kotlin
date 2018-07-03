package challengemvvm.fundingcircle.com.fcchallengemvvm.feature.datamodel

import challengemvvm.fundingcircle.com.fcchallengemvvm.model.networkmodel.AuctionsEndPoint
import challengemvvm.fundingcircle.com.fcchallengemvvm.network.FundingCircleClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by david.martinezgarcia on 21/04/2018.
 */
class AuctionsListRepository : AuctionsListRepositoryInterface {

    override fun retrieveAuctions(callback: AuctionsRepositoryCallback): Observable<AuctionsEndPoint> {
         val observable = FundingCircleClient.getInstance()
                .getAuctions()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())

         observable.subscribe { result ->
            System.out.println("Before reasingning result")
            var result :AuctionsEndPoint = result

            System.out.println("Showing the first item")
            System.out.println(result.items.get(0).title)
            callback.onAuctionsLoaded(result)
         }

        return observable

    }
}