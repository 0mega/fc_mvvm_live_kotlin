package challengemvvm.fundingcircle.com.fcchallengemvvm.service

import android.content.Context
import android.support.annotation.ColorRes
import challengemvvm.fundingcircle.com.fcchallengemvvm.R
import java.text.DecimalFormat

/**
 * Created by david.martinezgarcia on 21/04/2018.
 */
class AuctionUtils() {

    companion object {
        @ColorRes val COLOR_GREEN = R.color.green
        @ColorRes val COLOR_YELLOW = R.color.yellow
        @ColorRes val COLOR_PURPLE = R.color.purple
        @ColorRes val COLOR_ORANGE = R.color.orange
        @ColorRes val COLOR_RED = R.color.red

        private val BAND_A_PLUS = "A+"
        private val BAND_A = "A"
        private val BAND_B = "B"
        private val BAND_C = "C"
        private val BAND_C_MINUS = "C-"

        private val FEE = 0.01f
        private val BA = 20

        fun getColorByRiskBand(riskBand : String) : Int {
            var colorInt : Int = COLOR_RED
            when(riskBand) {
                BAND_A_PLUS -> colorInt = COLOR_GREEN
                BAND_A -> colorInt = COLOR_YELLOW
                BAND_B -> colorInt = COLOR_PURPLE
                BAND_C -> colorInt = COLOR_ORANGE
                BAND_C_MINUS -> colorInt = COLOR_RED
            }
            return colorInt
        }

        fun getEstimatedReturnAmount(context: Context, ar: Float, riskBand: String) : Float {
            return roundTwoDecimals(context,(1 + ar - getEbd(riskBand) - FEE) * BA)
        }

        private fun getEbd(riskBand : String) : Float{
            var ebd : Float =  0.01f
            when(riskBand) {
                BAND_A_PLUS ->  ebd = 0.01f
                BAND_A ->  ebd = 0.02f
                BAND_B ->  ebd = 0.03f
                BAND_C ->  ebd = 0.04f
                BAND_C_MINUS ->  ebd = 0.05f
            }
            return ebd
        }

        private fun roundTwoDecimals(context: Context, float: Float): Float {
            val twoDForm = DecimalFormat(context.resources.getString(R.string.decimal_format))
            return java.lang.Float.valueOf(twoDForm.format(float))
        }
    }
}