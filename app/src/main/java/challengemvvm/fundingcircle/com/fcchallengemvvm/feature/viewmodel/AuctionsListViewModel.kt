package challengemvvm.fundingcircle.com.fcchallengemvvm.feature.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import challengemvvm.fundingcircle.com.fcchallengemvvm.feature.datamodel.AuctionsListRepositoryInterface
import challengemvvm.fundingcircle.com.fcchallengemvvm.feature.datamodel.AuctionsRepositoryCallback
import challengemvvm.fundingcircle.com.fcchallengemvvm.model.Auction
import challengemvvm.fundingcircle.com.fcchallengemvvm.model.networkmodel.AuctionsEndPoint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function3

class AuctionsListViewModel(context: Application, val mDataModel : AuctionsListRepositoryInterface) : AndroidViewModel(context) {

    val auctionList : MutableLiveData<List<Auction>> = MutableLiveData()
    val loadingIndicator : MutableLiveData<Boolean> = MutableLiveData()

    fun liveAuctions(): LiveData<List<Auction>> = auctionList

    fun retrieveAuctions() {
        loadingIndicator.value = true
        mDataModel.retrieveAuctions(object : AuctionsRepositoryCallback {
            override fun onAuctionsLoaded(auctions: AuctionsEndPoint) {
                auctionList.value = auctions.items
                auctionList.postValue(auctions.items)
                loadingIndicator.value = false
            }

            override fun onError() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }

}