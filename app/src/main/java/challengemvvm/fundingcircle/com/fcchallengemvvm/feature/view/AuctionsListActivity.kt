package challengemvvm.fundingcircle.com.fcchallengemvvm.feature.view

import android.arch.lifecycle.Observer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import challengemvvm.fundingcircle.com.fcchallengemvvm.Application
import challengemvvm.fundingcircle.com.fcchallengemvvm.R
import challengemvvm.fundingcircle.com.fcchallengemvvm.feature.adapter.AuctionsListAdapter
import challengemvvm.fundingcircle.com.fcchallengemvvm.feature.viewmodel.AuctionsListViewModel
import challengemvvm.fundingcircle.com.fcchallengemvvm.model.Auction
import challengemvvm.fundingcircle.com.fcchallengemvvm.service.GsonFactory
import kotlinx.android.synthetic.main.content_main.*

class AuctionsListActivity : AppCompatActivity(), AuctionsListAdapter.AuctionClickListener {

    companion object {
        const val STATE_AUCTIONS = "state.auctions.list"
    }

    private lateinit var mProgressBar: ProgressBar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAuctions: List<Auction>

    private lateinit var mViewModel: AuctionsListViewModel
    private lateinit var adapter: AuctionsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        mRecyclerView = recycler_view
        mProgressBar = progress_bar

        mViewModel = getViewModel()

        setupListAdapter()

    }

    override fun onResume() {
        super.onResume()
        bind()
    }

    private fun setupListAdapter() {
        adapter = AuctionsListAdapter(this, this)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = adapter
    }

    private fun bind() {
        // Create the observer which updates the UI.
        val listAuctionObserver : Observer<List<Auction>> = Observer { it ->
            adapter.setAuctions(it ?: listOf())
            adapter.notifyDataSetChanged()
        }

        val loadingIndicatorObserver : Observer<Boolean> = Observer { it ->
            setLoadingIndicatorVisibility(it ?: true)
        }

        mViewModel.apply {
            openAuctionDetailEvent.observe(this@AuctionsListActivity, Observer<Auction> { auction ->
                if (auction != null) {
                    openAuctionDetails(auction)
                }
            })

            openAuctionDetailEventRiskBandA.observe(this@AuctionsListActivity, Observer<Auction> { auction ->
                if (auction != null) {
                    openAuctionDetailsRiskBandA(auction)
                }
            })

            openAuctionDetailEventRiskBandB.observe(this@AuctionsListActivity, Observer<Auction> { auction ->
                if (auction != null) {
                    openAuctionDetailsRiskBandB(auction)
                }
            })

            openAuctionDetailEventRiskBandC.observe(this@AuctionsListActivity, Observer<Auction> { auction ->
                if (auction != null) {
                    openAuctionDetailsRiskBandC(auction)
                }
            })

            openAuctionDetailEventRiskBandD.observe(this@AuctionsListActivity, Observer<Auction> { auction ->
                if (auction != null) {
                    openAuctionDetailsRiskBandD(auction)
                }
            })

            openAuctionDetailEventRiskBandE.observe(this@AuctionsListActivity, Observer<Auction> { auction ->
                if (auction != null) {
                    openAuctionDetailsRiskBandE(auction)
                }
            })
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        val liveAuctions = mViewModel.liveAuctions()
        val loadingIndicator = mViewModel.loadingIndicator
        liveAuctions.observe(this, listAuctionObserver)
        loadingIndicator.observe(this, loadingIndicatorObserver)

        mViewModel.retrieveAuctions()
    }

    fun setLoadingIndicatorVisibility(isProgresBarVisible : Boolean) {
        mProgressBar.visibility = if(isProgresBarVisible) VISIBLE else GONE
    }

    override fun onAuctionClicked(auction: Auction) {
        //mViewModel.openAuctionDetailEvent.value = auction
        mViewModel.decideAuctionDetailToOpen(auction)
    }

    fun openAuctionDetails(auction : Auction) {
        Log.d("LOG AuctionListActivity", "Detail Normal")
        val dialogFragment = EraDialogFragment.newInstance(auction.title, auction.rate, auction.riskBand)
        dialogFragment.show(supportFragmentManager, "Dialog")
    }

    fun openAuctionDetailsRiskBandA(auction : Auction) {
        Log.d("LOG AuctionListActivity", "Detail RiskBand A")
        val dialogFragment = EraDialogFragment.newInstance(auction.title, auction.rate, auction.riskBand)
        dialogFragment.show(supportFragmentManager, "Dialog")
    }
    fun openAuctionDetailsRiskBandB(auction : Auction) {
        Log.d("LOG AuctionListActivity", "Detail RiskBand B")
        val dialogFragment = EraDialogFragment.newInstance(auction.title, auction.rate, auction.riskBand)
        dialogFragment.show(supportFragmentManager, "Dialog")
    }
    fun openAuctionDetailsRiskBandC(auction : Auction) {
        Log.d("LOG AuctionListActivity", "Detail RiskBand C")
        val dialogFragment = EraDialogFragment.newInstance(auction.title, auction.rate, auction.riskBand)
        dialogFragment.show(supportFragmentManager, "Dialog")
    }
    fun openAuctionDetailsRiskBandD(auction : Auction) {
        Log.d("LOG AuctionListActivity", "Detail RiskBand D")
        val dialogFragment = EraDialogFragment.newInstance(auction.title, auction.rate, auction.riskBand)
        dialogFragment.show(supportFragmentManager, "Dialog")
    }
    fun openAuctionDetailsRiskBandE(auction : Auction) {
        Log.d("LOG AuctionListActivity", "Detail RiskBand E")
        val dialogFragment = EraDialogFragment.newInstance(auction.title, auction.rate, auction.riskBand)
        dialogFragment.show(supportFragmentManager, "Dialog")
    }

    private fun getViewModel(): AuctionsListViewModel {
        return (application as Application).getViewModel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (this::mAuctions.isInitialized) {
            outState.putString(STATE_AUCTIONS, GsonFactory.getInstance().toJson(mAuctions))
        }
        super.onSaveInstanceState(outState)
    }
}
