package challengemvvm.fundingcircle.com.fcchallengemvvm.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface ISchedulerProvider {

    fun computation(): Scheduler

    fun ui(): Scheduler
}