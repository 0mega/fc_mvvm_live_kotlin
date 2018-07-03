package challengemvvm.fundingcircle.com.fcchallengemvvm.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProvider// Prevent direct instantiation.
private constructor() : ISchedulerProvider {

    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    companion object {

        var INSTANCE: SchedulerProvider = SchedulerProvider()
    }
}