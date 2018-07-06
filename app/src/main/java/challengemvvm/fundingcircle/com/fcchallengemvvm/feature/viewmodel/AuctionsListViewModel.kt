package challengemvvm.fundingcircle.com.fcchallengemvvm.feature.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import challengemvvm.fundingcircle.com.fcchallengemvvm.feature.datamodel.AuctionsListRepositoryInterface
import challengemvvm.fundingcircle.com.fcchallengemvvm.model.Auction
import challengemvvm.fundingcircle.com.fcchallengemvvm.service.SingleLiveEvent

class AuctionsListViewModel(context: Application, val mDataModel : AuctionsListRepositoryInterface) : AndroidViewModel(context) {

    val auctionList : MutableLiveData<List<Auction>> = MutableLiveData()
    val loadingIndicator : MutableLiveData<Boolean> = MutableLiveData()

    fun liveAuctions(): LiveData<List<Auction>> = auctionList

    internal val openAuctionDetailEvent = SingleLiveEvent <Auction>()

    internal val openAuctionDetailEventRiskBandA = SingleLiveEvent <Auction>()
    internal val openAuctionDetailEventRiskBandB = SingleLiveEvent <Auction>()
    internal val openAuctionDetailEventRiskBandC = SingleLiveEvent <Auction>()
    internal val openAuctionDetailEventRiskBandD = SingleLiveEvent <Auction>()
    internal val openAuctionDetailEventRiskBandE = SingleLiveEvent <Auction>()

    fun retrieveAuctions() {
        loadingIndicator.value = true
        mDataModel.retrieveAuctions()
                .doOnError {
                    Log.e("Error", "failed fetching auctions")
                }
                .subscribe() { auctions ->
                    auctionList.value = auctions.items
                    loadingIndicator.value = false
        }
    }

    fun decideAuctionDetailToOpen(auction : Auction) {
        when(auction.riskBand) {
            "A" -> openAuctionDetailEventRiskBandA.value = auction
            "B" -> openAuctionDetailEventRiskBandB.value = auction
            "C" -> openAuctionDetailEventRiskBandC.value = auction
            "D" -> openAuctionDetailEventRiskBandD.value = auction
            "E" -> openAuctionDetailEventRiskBandE.value = auction
            else -> {
                openAuctionDetailEvent.value = auction
            }
        }
    }

}