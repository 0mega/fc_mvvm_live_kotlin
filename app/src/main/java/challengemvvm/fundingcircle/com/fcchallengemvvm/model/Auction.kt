package challengemvvm.fundingcircle.com.fcchallengemvvm.model

import com.google.gson.annotations.SerializedName

/**
 * Created by david.martinezgarcia on 21/04/2018.
 */
data class Auction(
        val id: String,
        val title: String,
        val rate: Float,
        @SerializedName("risk_band") val riskBand: String) {
}