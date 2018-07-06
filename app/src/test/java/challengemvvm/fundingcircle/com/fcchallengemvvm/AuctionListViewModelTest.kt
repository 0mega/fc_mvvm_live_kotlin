package challengemvvm.fundingcircle.com.fcchallengemvvm

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import challengemvvm.fundingcircle.com.fcchallengemvvm.feature.datamodel.AuctionsListRepositoryInterface
import challengemvvm.fundingcircle.com.fcchallengemvvm.feature.viewmodel.AuctionsListViewModel
import challengemvvm.fundingcircle.com.fcchallengemvvm.model.Auction
import challengemvvm.fundingcircle.com.fcchallengemvvm.model.networkmodel.AuctionsEndPoint
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class AuctionListViewModelTest {
    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: AuctionsListViewModel
    @Mock
    private lateinit var auctionsListRepository: AuctionsListRepositoryInterface

    @Mock
    private var applicationMock = mock<Application>()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = AuctionsListViewModel(applicationMock, auctionsListRepository)
    }

    @Test
    fun shouldRetrieveAuctionsList() {
        val auctions : ArrayList<Auction> = ArrayList()
        val auction1 = Auction("1", "Sainsbury", 0.19f, "A+")
        val auction2 = Auction("2", "Tesco", 0.21f, "C")
        auctions.add(auction1)
        auctions.add(auction2)
        val auctionsEndPoint = AuctionsEndPoint(auctions)

        Mockito.`when`(auctionsListRepository.retrieveAuctions()).thenReturn(Observable.just(auctionsEndPoint))

        viewModel.retrieveAuctions()

        val observer = mock<Observer<Boolean>>()
        viewModel.loadingIndicator.observeForever(observer)
        viewModel.retrieveAuctions()

        val inOrder = Mockito.inOrder(observer)
        inOrder.verify(observer).onChanged(false)
        inOrder.verify(observer).onChanged(true)
        inOrder.verify(observer).onChanged(false)

        assert(viewModel.auctionList.value == auctions)

    }

    @Test
    fun shouldOpenRiskBandAWhenAuctionRiskBandA() {
        val auction = Auction("1", "Sainsbury", 0.19f, "A")
        viewModel.decideAuctionDetailToOpen(auction)
        assert(viewModel.openAuctionDetailEventRiskBandA.value == auction)
    }

    @Test
    fun shouldOpenRiskBandDefaultWhenAuctionRiskBandCMinus() {
        val auction = Auction("1", "Sainsbury", 0.19f, "C-")
        viewModel.decideAuctionDetailToOpen(auction)
        assert(viewModel.openAuctionDetailEvent.value == auction)
    }

}