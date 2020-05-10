package GoBear;

import com.google.common.collect.Ordering;
import com.monkey.api.Assertion;
import com.monkey.api.MonkeyLogger;
import com.monkey.api.enumeration.LogLevel;
import gobear.ResultPage;
import org.testng.annotations.Test;
import gobear.GoBearPage;

import java.util.ArrayList;
import java.util.List;

public class TS_GoBear {

    @Test(description = "This is test case 1 for Test")
    public void TC_Test_01_Verify_ResultPage_DataDisplay() {

        GoBearPage.getPage()
                // Step 1: Go to https://www.gobear.com/ph?x_session_type=UAT
                .gotoGoBear()
                // Step 2: Open Result page by selecting "Travel" from "Insurance"
                .selectProductTab("Insurance").selectSubProductTab("Travel").clickShowResult();

        ResultPage.getPage()
                // VP: There are at least 3 cards in result page
                .verifyMinimumNumberOfCardShouldBe(3)
                // VP: Filter, Sort, Detail panel display in left menu
                .verifyMenuFilterDisplays().verifyMenuSortDisplays().verifyMenuDetailDisplays();
    }

    @Test(description = "This is test case 2 for Test")
    public void TC_Test_02_Verify_ResultPage_LeftMenu_Functional() throws InterruptedException {

        int numberOfCardOnGrid = 0;

        GoBearPage.getPage()
                // Step 1: Go to https://www.gobear.com/ph?x_session_type=UAT
                .gotoGoBear()
                // Step 2: Open Result page by selecting "Travel" from "Insurance"
                .selectProductTab("Insurance").selectSubProductTab("Travel").clickShowResult();

        // ----- FILTER -----
        MonkeyLogger.log(this.getClass().getName(),"Verify 'Promotion' in Filter", LogLevel.INFO);
        for(String promotionTerm : "Promos only,Show all".split(",")) {
            numberOfCardOnGrid = ResultPage.getPage().getNumberOfCurrentCard();
            ResultPage.getPage()
                    // Step 3: Select Promotion item from list
                    .selectFilerPromotion(promotionTerm)
                    //VP: Verify the Card grid is affected by changing filter Promotion
                    .verifyTheNumberOfCardIsChanged(numberOfCardOnGrid);
        }

        MonkeyLogger.log(this.getClass().getName(),"Verify 'Insurers' in Filter", LogLevel.INFO);
        numberOfCardOnGrid = ResultPage.getPage().getNumberOfCurrentCard();
        ResultPage.getPage()
                // Step 4: Select "FPG Insurance"
                .selectFilterInsurer("FPG Insurance")
                // VP: Verify the Card grid is affected by changing filter Insurers
                .verifyTheNumberOfCardIsChanged(numberOfCardOnGrid);
        numberOfCardOnGrid = ResultPage.getPage().getNumberOfCurrentCard();
        ResultPage.getPage()
                // Step 5: Un-select "FPG Insurance"
                .selectFilterInsurer("FPG Insurance")
                // VP: Verify the Card grid is affected by changing filter Insurers
                .verifyTheNumberOfCardIsChanged(numberOfCardOnGrid);

        // ----- SORT -----
        MonkeyLogger.log(this.getClass().getName(),"Verify Sort Function", LogLevel.INFO);
        ResultPage.getPage()
                // Step 6: Select "Price: Low to high"
                .selectSort("Price: Low to high");
                // VP: Verify the Card is Sort Low to high by Price
        Assertion.validateTrue(Ordering.natural().isOrdered(ResultPage.getPage().getAllPrices()),
                "Verify the Card is sorted Low to high by Price");
        ResultPage.getPage()
                // Step 7: Select "Price: High to low"
                .selectSort("Price: High to low");
                // VP: Verify the Card is Sort Low to high by Price
        Assertion.validateTrue(Ordering.natural().reverse().isOrdered(ResultPage.getPage().getAllPrices()),
                "Verify the Card is sorted High to Low by Price");

        //----- DETAIL -----
        MonkeyLogger.log(this.getClass().getName(),"Verify 'Policy' in Detail", LogLevel.INFO);
        for(String policyTerm : "annual trip,single trip".split(",")) {
            numberOfCardOnGrid = ResultPage.getPage().getNumberOfCurrentCard();
            ResultPage.getPage()
                    // Step 8: Select Policy item from list
                    .selectDetailPolicyType(policyTerm)
                    //VP: Verify the Card grid is affected by changing Policy
                    .verifyTheNumberOfCardIsChanged(numberOfCardOnGrid);
        }

        MonkeyLogger.log(this.getClass().getName(),"Verify 'Who's going' in Detail", LogLevel.INFO);
        for(String passengerTerm : "my family,just me".split(",")) {
            numberOfCardOnGrid = ResultPage.getPage().getNumberOfCurrentCard();
            ResultPage.getPage()
                    // Step 8: Select Number of passenger item from list
                    .selectDetailNoOfPassenger(passengerTerm)
                    //VP: Verify the Card grid is affected by changing Number of Passenger
                    .verifyTheNumberOfCardIsChanged(numberOfCardOnGrid);
        }

        MonkeyLogger.log(this.getClass().getName(),"Verify 'Destination' in Detail", LogLevel.INFO);
        for(String country : "Philippines,Hong Kong".split(",")) {
            numberOfCardOnGrid = ResultPage.getPage().getNumberOfCurrentCard();
            ResultPage.getPage()
                    // Step 8: Select Number of passenger item from list
                    .selectDestination(country)
                    //VP: Verify the Card grid is affected by changing Number of Passenger
                    .verifyTheNumberOfCardIsChanged(numberOfCardOnGrid);
        }

        MonkeyLogger.log(this.getClass().getName(),"Verify 'Start Date' in Detail", LogLevel.INFO);
        List<String> currentCards = ResultPage.getPage().getAllCardName();
        List<String> cardsListAfterChanges = new ArrayList<String>();
        ResultPage.getPage()
                // Step 9: Select Start Date
                .selectStartDate();
        cardsListAfterChanges = ResultPage.getPage().getAllCardName();
                // VP: Verify Card list is changed after changing start day
        Assertion.validateTrue(currentCards != cardsListAfterChanges, "Verify Card List is change after changing start day");

        MonkeyLogger.log(this.getClass().getName(),"Verify 'End Date' in Detail", LogLevel.INFO);
        currentCards = ResultPage.getPage().getAllCardName();
        ResultPage.getPage()
                // Step 10: Select Start Date
                .selectEndDate();
        cardsListAfterChanges = ResultPage.getPage().getAllCardName();
                // VP: Verify Card list is changed after changing start day
        Assertion.validateTrue(currentCards != cardsListAfterChanges, "Verify Card List is change after changing end day");

    }

    @Test(description = "This is test case 3 for Test")
    public void TC_Test_03_Verify_ResultPage_LeftMenu_Functional_With_Combination() throws InterruptedException {

        String insurerValue = "Prudential Guarantee";
        String sortValue = "Price: Low to high";
        String policyTypeValue = "annual trip";
        String numOfPassengerValue ="2 persons";
        String destinationValue = "Asia";

        GoBearPage.getPage()
                // Step 1: Go to https://www.gobear.com/ph?x_session_type=UAT
                .gotoGoBear()
                // Step 2: Open Result page by selecting "Travel" from "Insurance"
                .selectProductTab("Insurance").selectSubProductTab("Travel").clickShowResult();


        MonkeyLogger.log(this.getClass().getName(),"Verify Left Menu Function", LogLevel.INFO);
        ResultPage.getPage()
                // Step 3: Select "Price: Low to high"
                .selectSort(sortValue)
                .selectDetailPolicyType(policyTypeValue)
                .selectDetailNoOfPassenger(numOfPassengerValue)
                .selectDestination(destinationValue)
                .selectStartDate()
                .selectFilterInsurer(insurerValue)
                // VP: Verify All Cards are in the same brand
                .verifyAllCardsAreInSameBrand(insurerValue);
                // VP: Verify the Card is sorted Low to high by Price
        Assertion.validateTrue(Ordering.natural().isOrdered(ResultPage.getPage().getAllPrices()),
                "Verify the Card is sorted Low to high by Price");


    }
}
