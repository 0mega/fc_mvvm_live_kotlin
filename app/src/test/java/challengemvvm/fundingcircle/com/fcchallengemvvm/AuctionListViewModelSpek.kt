package challengemvvm.fundingcircle.com.fcchallengemvvm

import android.app.Application
import android.arch.core.executor.testing.InstantTaskExecutorRule
import challengemvvm.fundingcircle.com.fcchallengemvvm.feature.datamodel.AuctionsListRepository
import challengemvvm.fundingcircle.com.fcchallengemvvm.feature.datamodel.AuctionsListRepositoryInterface
import challengemvvm.fundingcircle.com.fcchallengemvvm.feature.viewmodel.AuctionsListViewModel
import challengemvvm.fundingcircle.com.fcchallengemvvm.model.Auction
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Rule
import org.junit.Test
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.Mock



@RunWith(JUnitPlatform::class)
class Test {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var auctionsListRepository: AuctionsListRepositoryInterface

    @Mock
    private var applicationMock = mock<Application>()

    inner class AuctionsViewModelSpek : Spek({

        describe("decideAuctionDetailToOpen") {

            val viewModel = AuctionsListViewModel(applicationMock, auctionsListRepository)
            val auction = Auction("1", "Sainsbury", 0.19f, "A+")

            context("when auction risk band is A") {
                beforeEachTest {
                    auction.riskBand = "A"
                }

                it("navigates to risk band A") {

                    viewModel.decideAuctionDetailToOpen(auction)
                    assert(viewModel.openAuctionDetailEventRiskBandA.value == auction)
                }
            }
        }
    })
}

