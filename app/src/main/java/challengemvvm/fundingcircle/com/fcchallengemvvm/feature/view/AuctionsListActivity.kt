package challengemvvm.fundingcircle.com.fcchallengemvvm.feature.view

import android.arch.lifecycle.Observer

import android.databinding.ObservableList
import android.databinding.ViewDataBinding
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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.content_main.*
import java.util.ArrayList
import android.databinding.adapters.TextViewBindingAdapter.setText
import android.support.annotation.Nullable


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

    override fun onPause() {
        super.onPause()
        unBind()
    }

    private fun setupListAdapter() {
        if (mViewModel != null) {
            adapter = AuctionsListAdapter(this, this)
            mRecyclerView.layoutManager = LinearLayoutManager(this)
            mRecyclerView.adapter = adapter
        } else {
            Log.w("TAG", "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun bind() {
        // Create the observer which updates the UI.
        val listAuctionObserver: Observer<List<Auction>> = Observer { it ->
            adapter.setAuctions(it ?: listOf())
            adapter.notifyDataSetChanged()
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        val liveAuctions = mViewModel.liveAuctions()
        liveAuctions.observe(this, listAuctionObserver)

        mViewModel.retrieveAuctions()
    }

    private fun unBind() {

    }

    fun setLoadingIndicatorVisibility(isProgresBarVisible : Boolean) {
        if(isProgresBarVisible) {
            showProgress()
        } else{
            hideProgress()
        }
    }

    fun showProgress() {
        mProgressBar.visibility = VISIBLE
    }

     fun hideProgress() {
        mProgressBar.visibility = GONE
    }

    fun showAuctions(auctions: List<Auction>) {
        mAuctions = auctions
        val adapter = AuctionsListAdapter(this, this)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = adapter
        adapter.setAuctions(auctions)
    }

    override fun onAuctionClicked(auction: Auction) {
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
