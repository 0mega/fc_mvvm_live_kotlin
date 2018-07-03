package challengemvvm.fundingcircle.com.fcchallengemvvm

import android.app.Application
import challengemvvm.fundingcircle.com.fcchallengemvvm.feature.datamodel.AuctionsListRepository
import challengemvvm.fundingcircle.com.fcchallengemvvm.feature.datamodel.AuctionsListRepositoryInterface
import challengemvvm.fundingcircle.com.fcchallengemvvm.feature.viewmodel.AuctionsListViewModel
import challengemvvm.fundingcircle.com.fcchallengemvvm.schedulers.ISchedulerProvider
import challengemvvm.fundingcircle.com.fcchallengemvvm.schedulers.SchedulerProvider

class Application : Application() {

    val dataModel: AuctionsListRepositoryInterface = AuctionsListRepository()

    val schedulerProvider: ISchedulerProvider
        get() = SchedulerProvider.INSTANCE
    
    fun getViewModel() : AuctionsListViewModel {
        return  AuctionsListViewModel(this, dataModel/*, schedulerProvider*/)
    }

}