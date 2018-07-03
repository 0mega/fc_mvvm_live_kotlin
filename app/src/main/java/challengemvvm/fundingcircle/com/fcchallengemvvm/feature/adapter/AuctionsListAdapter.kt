package challengemvvm.fundingcircle.com.fcchallengemvvm.feature.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import challengemvvm.fundingcircle.com.fcchallengemvvm.R
import challengemvvm.fundingcircle.com.fcchallengemvvm.model.Auction
import challengemvvm.fundingcircle.com.fcchallengemvvm.service.AuctionUtils
import kotlinx.android.synthetic.main.list_item_auction.view.*
import java.util.*

/**
 * Created by david.martinezgarcia on 21/04/2018.
 */
class AuctionsListAdapter(private val mContext: Context, private val mListener: AuctionClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private val ITEM_TYPE_TITLE = 0
        private val ITEM_TYPE_AUCTION = 1
    }

    private var itemList: MutableList <BaseDataItem> = ArrayList()
    private var auctionsList: MutableList<AuctionDataItem> = ArrayList()

    init {
        itemList.add(TitleDataItem(mContext.getString(R.string.list_of_auctions), R.drawable.fc_icon))
    }

    interface AuctionClickListener {
        fun onAuctionClicked(auction: Auction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var viewHolder: RecyclerView.ViewHolder
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        when (viewType) {
            ITEM_TYPE_TITLE -> {
                viewHolder = TitleViewHolder(inflater.inflate(R.layout.list_item_auctions_title, parent, false))
            }
            ITEM_TYPE_AUCTION -> {
                viewHolder = AuctionViewHolder(mContext, mListener, inflater.inflate(R.layout.list_item_auction, parent, false))
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ITEM_TYPE_TITLE -> {
                val titleViewHolder = holder as TitleViewHolder
                titleViewHolder.setup(itemList[position] as TitleDataItem)
            }
            ITEM_TYPE_AUCTION -> {
                val auctionViewHolder = holder as AuctionViewHolder
                auctionViewHolder.setup(itemList[position] as AuctionDataItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            itemList[position] is TitleDataItem -> ITEM_TYPE_TITLE
            else -> ITEM_TYPE_AUCTION
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setAuctions(auctions: List<Auction>) {
        auctionsList.clear()
        auctions.forEach { it ->
            auctionsList.add(AuctionDataItem(it))
        }

        itemList.addAll(auctionsList)
    }

    class TitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val titleTextView = view as TextView

        fun setup(item: TitleDataItem) {
            titleTextView.setCompoundDrawablesWithIntrinsicBounds(item.resId, 0, 0, 0)
            titleTextView.text = item.title
        }
    }

    class AuctionViewHolder(private val context : Context, private val listener: AuctionClickListener, view: View) : RecyclerView.ViewHolder(view) {

        private var titleTextView: TextView = view.auction_item_title
        private var riskBandTextView: TextView = view.risk_band
        private var rateTextView: TextView = view.auction_rate
        private var image : ImageView = view.auction_item_image
        private lateinit var auction: Auction

        init {
            itemView.setOnClickListener {
                listener.onAuctionClicked(auction)
            }
        }

        fun setup(item: AuctionDataItem) {
            auction = item.auction
            titleTextView.text = auction.title
            rateTextView.text = String.format(context.getString(R.string.rate), auction.rate)
            riskBandTextView.text =  String.format(context.getString(R.string.risk_band) ,auction.riskBand)
            image.setBackgroundColor(ContextCompat.getColor(context, AuctionUtils.getColorByRiskBand(auction.riskBand)))
        }
    }

    open class BaseDataItem

    class AuctionDataItem(val auction: Auction) : BaseDataItem()

    class TitleDataItem(val title: String, val resId: Int) : BaseDataItem()
}