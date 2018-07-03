package challengemvvm.fundingcircle.com.fcchallengemvvm.network

import challengemvvm.fundingcircle.com.fcchallengemvvm.BuildConfig.API_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by david.martinezgarcia on 21/04/2018.
 */
class FundingCircleClient {

    companion object {
        private var sFundingCircleService = initRetrofit().create(FundingCircleService::class.java)

        private fun initRetrofit(): Retrofit {
            return Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(API_URL)
                    .build()
        }

        fun getInstance(): FundingCircleService {
            return sFundingCircleService
        }
    }
}