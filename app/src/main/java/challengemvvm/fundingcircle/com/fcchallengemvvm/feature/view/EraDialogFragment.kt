package challengemvvm.fundingcircle.com.fcchallengemvvm.feature.view

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import challengemvvm.fundingcircle.com.fcchallengemvvm.R
import challengemvvm.fundingcircle.com.fcchallengemvvm.service.AuctionUtils
import kotlinx.android.synthetic.main.dialog_era.view.*

/**
 * Created by david.martinezgarcia on 22/04/2018.
 */
class EraDialogFragment : DialogFragment() {

    companion object {

        private val EXTRA_AUCTION_BAND = "extra.auction.band"
        private val EXTRA_AUCTION_RATE = "extra.auction.rate"
        private val EXTRA_AUCTION_TITLE = "extra.auction.title"

        private var riskBand: String = ""
        private var title: String = ""
        private var rate: Float = 0f

        fun newInstance(title : String, auctionRate : Float, riskBand : String): EraDialogFragment {
            val args = Bundle()
            val fragment = EraDialogFragment()
            args.putString(EXTRA_AUCTION_BAND, riskBand)
            args.putString(EXTRA_AUCTION_TITLE, title)
            args.putFloat(EXTRA_AUCTION_RATE, auctionRate)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        riskBand = arguments!!.getString(EXTRA_AUCTION_BAND)
        rate = arguments!!.getFloat(EXTRA_AUCTION_RATE)
        title = arguments!!.getString(EXTRA_AUCTION_TITLE)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnKeyListener(DialogInterface.OnKeyListener { dialogInterface, keyCode, keyEvent ->
            if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
                dismiss()
                return@OnKeyListener true
            }
            false
        })
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_era, container)

        val titleText = view.auction_title
        val eraText = view.auction_era
        val closeCross = view.close_cross
        closeCross.setOnClickListener(View.OnClickListener {
            it -> dismiss()
        })

        titleText.text = title
        eraText.text = String.format(context!!.getString(R.string.era), AuctionUtils.getEstimatedReturnAmount(context!!, rate, riskBand))

        return view
    }
}