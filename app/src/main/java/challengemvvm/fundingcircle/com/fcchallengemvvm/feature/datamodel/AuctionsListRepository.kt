package challengemvvm.fundingcircle.com.fcchallengemvvm.feature.datamodel

import challengemvvm.fundingcircle.com.fcchallengemvvm.network.FundingCircleClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by david.martinezgarcia on 21/04/2018.
 */
class AuctionsListRepository : AuctionsListRepositoryInterface {

    override fun retrieveAuctions(callback: AuctionsRepositoryCallback) {
         FundingCircleClient.getInstance()
                .getAuctions()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                     result -> callback.onAuctionsLoaded(result)
                }
    }
}