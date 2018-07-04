package challengemvvm.fundingcircle.com.fcchallengemvvm.feature.datamodel

import challengemvvm.fundingcircle.com.fcchallengemvvm.model.networkmodel.AuctionsEndPoint

interface AuctionsRepositoryCallback {
    fun onAuctionsLoaded(auctions: AuctionsEndPoint)
    fun onError()
}