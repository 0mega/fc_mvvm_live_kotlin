package challengemvvm.fundingcircle.com.fcchallengemvvm.feature.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import challengemvvm.fundingcircle.com.fcchallengemvvm.feature.datamodel.AuctionsListRepositoryInterface
import challengemvvm.fundingcircle.com.fcchallengemvvm.feature.datamodel.AuctionsRepositoryCallback
import challengemvvm.fundingcircle.com.fcchallengemvvm.model.Auction
import challengemvvm.fundingcircle.com.fcchallengemvvm.model.networkmodel.AuctionsEndPoint
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class AuctionsListViewModel(context: Application, val mDataModel : AuctionsListRepositoryInterface) : AndroidViewModel(context) {

    private val mLoadingIndicatorSubject: BehaviorSubject<Boolean> = BehaviorSubject.create()

    val auctionList : MutableLiveData<List<Auction>> = MutableLiveData()

    fun liveAuctions(): LiveData<List<Auction>> = auctionList

    fun retrieveAuctions() {
        mDataModel.retrieveAuctions(object : AuctionsRepositoryCallback {
            override fun onAuctionsLoaded(auctions: AuctionsEndPoint) {
                auctionList.value = auctions.items
                System.out.println("Has observers: ${auctionList.hasActiveObservers()}")
                auctionList.postValue(auctions.items)
            }

            override fun onError() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    fun getLoadingIndicatorVisibility(): Observable<Boolean> {
        return mLoadingIndicatorSubject
    }
}