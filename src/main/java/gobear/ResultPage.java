package gobear;

import com.monkey.api.Assertion;
import com.monkey.api.JavaScriptExecutor;
import com.monkey.api.Wait;
import com.monkey.api.annotation.WebLocator;
import com.monkey.api.enumeration.Selector;
import com.monkey.api.page.MonkeyWebElement;
import com.monkey.api.page.MonkeyWebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ResultPage extends MonkeyWebPage {

    /***** Capture Elements *****/
    @WebLocator(identifier = "div.grid-row:not([style*='none'])", selector = Selector.cssSelector)
    MonkeyWebElement resultCardContainer;

    /**
     * Left Menu
     **/
    // Filter
    @WebLocator(identifier = ".filter-detail", selector = Selector.cssSelector)
    MonkeyWebElement panelMenuFilter;

    @WebLocator(identifier = "collapseSeemoreBtn", selector = Selector.id)
    MonkeyWebElement btnSeeMore;

    // Sort
    @WebLocator(identifier = ".sort-detail", selector = Selector.cssSelector)
    MonkeyWebElement panelMenuSort;

    // Detail
    @WebLocator(identifier = ".edit-detail", selector = Selector.cssSelector)
    MonkeyWebElement panelMenuDetail;

    @WebLocator(identifier = ".edit-detail button", selector = Selector.cssSelector)
    MonkeyWebElement btnShowCountries;

    @WebLocator(identifier = ".edit-detail button ~ .dropdown-menu > ul", selector = Selector.cssSelector)
    MonkeyWebElement panelCountry;

    @WebLocator(identifier = ".edit-detail .travel-date-field .date-from input", selector = Selector.cssSelector)
    MonkeyWebElement txtStartDate;

    @WebLocator(identifier = ".edit-detail .travel-date-field .date-to input", selector = Selector.cssSelector)
    MonkeyWebElement txtEndDate;

    /***** Implementing *****/

    static final String FILTER_INSURERS = "Insurers";
    static final String FILTER_PROMOTIONS = "Promotions";
    static final String DETAIL_POLICY_TYPES = "Policy type";
    static final String DETAIL_WHO_GOING = "going";

    private static ResultPage resultInstance;

    /**
     * Constructor
     */
    public ResultPage(){
        setNumberOfPreviousCards(getNumberOfCurrentCard());
    }

    /**
     * Init Page
     * @return
     */
    public static ResultPage getPage(){
        if(resultInstance == null){
            resultInstance = new ResultPage();
        }
        return resultInstance;
    }

    private int numberOfPreviousCards;

    /**
     * Get number of cards before change
     * @return int
     */
    public int getNumberOfPreviousCards() {
        return numberOfPreviousCards;
    }

    /**
     * Set number of cards before change
      * @param numberOfPreviousCards int
     */
    public void setNumberOfPreviousCards(int numberOfPreviousCards) {
        this.numberOfPreviousCards = numberOfPreviousCards;
    }

    /**
     * Return the sub element locator of left menu
     * @param name - String
     * @return
     */
    private String getMenuItemLocatorByName(String name) {
        return "//label[contains(text(),'" + name + "')]/..";
    }

    /**
     * Return the sub element group locator of left menu
     * @param groupName - String
     * @return
     */
    private String getMenuGroupItemLocatorByName(String groupName) {
        return ".//label[contains(text(),'" + groupName + "')]/..";
    }

    /**
     * Return the input element such as CheckBox/Radio
     * @param element - WebElement
     * @return
     */
    private WebElement getInputSelectItem(WebElement element) {
        return element.findElement(By.xpath(".//input"));
    }

    /**
     * Return all card element
     * @return
     */
    public List<WebElement> getListOfCards(){
        return resultCardContainer.getWebElement().findElements(By.cssSelector("div.card-full"));
    }

    /**
     * Return all price of current displayed cards
     * @return List<Integer>
     */
    public List<Integer> getAllPrices(){
        List<Integer> priceList = new ArrayList<Integer>();
        for(WebElement card : getListOfCards()){
            priceList.add(Integer.parseInt(card.findElement(By.cssSelector(".policy-price span")).getText().replace(",","")));
        }
        return priceList;
    }

    /**
     * Return all current cards name
     * @return List<String>
     */
    public List<String> getAllCardName(){
        List<String> nameList = new ArrayList();
        for(WebElement card : getListOfCards()){
            nameList.add(card.findElement(By.cssSelector(".card-title span")).getText().trim());
        }
        return nameList;
    }

    /**
     * Return all current brand of displayed cards
     * @return List<String>
     */
    public List<String> getAllCardBrandName(){
        List<String> nameList = new ArrayList();
        for(WebElement card : getListOfCards()){
            nameList.add(card.findElement(By.cssSelector(".card-brand .name")).getText().trim());
        }
        return nameList;
    }

    /**
     * Get total current cards
     * @return
     */
    public int getNumberOfCurrentCard() {
        return getListOfCards().size();
    }

    /**
     * Wait for the card grid load when changing menu
     * @param waitTime
     * @return
     * @throws InterruptedException
     */
    public ResultPage waitForCardListChange(int waitTime) throws InterruptedException {
        for(int i = 0; i < waitTime && (getNumberOfCurrentCard() == getNumberOfPreviousCards()); i++){
            Thread.sleep(1000);
        }
        setNumberOfPreviousCards(getListOfCards().size());
        return this;
    }

    /**
     * Verify Card List has at least <number> of card
     * @param expected - int
     * @return
     */
    public ResultPage verifyMinimumNumberOfCardShouldBe(int expected) {
        Assertion.validateTrue(getListOfCards().size() >= expected, "Verify Card List has at least [" + expected + "] item");
        return this;
    }

    /**
     * Verify the card grid is changes
     * @param expected - int
     * @return
     */
    public ResultPage verifyTheNumberOfCardIsChanged(int expected){
        Assertion.validateTrue(getNumberOfCurrentCard() != expected,"Verify the card grid is changes");
        return this;
    }

    /**
     * Verify All Current Cards are in the same brand
     * @param expectedBrand - String
     * @return
     */
    public ResultPage verifyAllCardsAreInSameBrand(String expectedBrand) {
        Boolean check = false;
        for(String brand : getAllCardBrandName()){
            check = brand.equals(expectedBrand);
            if(!check){
                break;
            }
        }
        Assertion.validateTrue(check, "Verify All Current Cards are in the same brand");
        return resultInstance;
    }

    /**
     * Verify 'Filter' displays in Left menu
     * @return
     */
    public ResultPage verifyMenuFilterDisplays() {
        Assertion.validateElementIsPresent(panelMenuFilter, "Verify 'Filter' displays in Left menu");
        return this;
    }

    /**
     * Verify 'Sort' displays in Left menu
     * @return
     */
    public ResultPage verifyMenuSortDisplays() {
        Assertion.validateElementIsPresent(panelMenuSort, "Verify 'Sort' displays in Left menu");
        return this;
    }

    /**
     * Verify 'Detail' displays in Left menu
     * @return
     */
    public ResultPage verifyMenuDetailDisplays() {
        Assertion.validateElementIsPresent(panelMenuDetail, "Verify 'Detail' displays in Left menu");
        return this;
    }

    /**
     * Toggle show See More in menu Filter
     * @param showMore True = See More; False = See Less
     * @return
     */
    public ResultPage toggleSeeMore(Boolean showMore) {
        Boolean currentState = Boolean.valueOf(btnSeeMore.getWebElement().getAttribute("aria-expanded"));
        if (!showMore.equals(currentState)) {
            btnSeeMore.click();
        }
        return this;
    }

    /**
     * Select Filter Promotion
     * @param value - String
     * @return
     * @throws InterruptedException
     */
    public ResultPage selectFilerPromotion(String value) throws InterruptedException {
        String itemLocator = getMenuGroupItemLocatorByName(FILTER_PROMOTIONS) + getMenuItemLocatorByName(value);
        panelMenuFilter.getWebElement().findElement(By.xpath(itemLocator)).click();
        waitForCardListChange(2);
        return this;
    }

    /**
     * Select Filter Insurers
     * @param values - String
     * @return
     * @throws InterruptedException
     */
    public ResultPage selectFilterInsurer(String values) throws InterruptedException {
        for (String item : values.split(",")) {
            String itemLocator = getMenuGroupItemLocatorByName(FILTER_INSURERS) + getMenuItemLocatorByName(item);
            panelMenuFilter.getWebElement().findElement(By.xpath(itemLocator)).click();
            waitForCardListChange(2);
        }
        return this;
    }

    /**
     * Select Sort from Left Menu
     * @param value
     * @return
     */
    public ResultPage selectSort(String value) {
        panelMenuSort.getWebElement().findElement(By.xpath("." + getMenuItemLocatorByName(value))).click();
        Wait.waitForPageToLoad(2);
        return this;
    }

    /**
     * Select Policy in Detail panel
     * @param value - String
     * @return
     * @throws InterruptedException
     */
    public ResultPage selectDetailPolicyType(String value) throws InterruptedException{
        String itemLocator = getMenuGroupItemLocatorByName(DETAIL_POLICY_TYPES) + getMenuItemLocatorByName(value);
        panelMenuDetail.getWebElement().findElement(By.xpath(itemLocator)).click();
        waitForCardListChange(2);
        return this;
    }

    /**
     * Select Number of Passenger
     * @param value - String
     * @return
     * @throws InterruptedException
     */
    public ResultPage selectDetailNoOfPassenger(String value) throws InterruptedException {
        String itemLocator = getMenuGroupItemLocatorByName(DETAIL_WHO_GOING) + getMenuItemLocatorByName(value);
        panelMenuDetail.getWebElement().findElement(By.xpath(itemLocator)).click();
        waitForCardListChange(2);
        return this;
    }

    /**
     * Open select Country dropdown
     * @return
     */
    public ResultPage openCountryList() {
        String currentState = btnShowCountries.getWebElement().getAttribute("class");
        if (!currentState.contains("open")) {
            btnShowCountries.click();
        }
        return this;
    }

    /**
     * Select country from dropdown
     * @param country
     * @return
     * @throws InterruptedException
     */
    public ResultPage selectDestination(String country) throws InterruptedException {
        openCountryList();
        JavaScriptExecutor.excuteJS(
                panelCountry.getWebElement().findElement(By.xpath(".//li/a/span[text()='" + country + "']"))
                ,"arguments[0].click();");
        waitForCardListChange(3);
        return this;
    }

    /**
     * Select Start day in DatePicker
     * @return
     */
    public ResultPage selectStartDate(){
        txtStartDate.click();
        DatePicker datePicker = new DatePicker();
        datePicker.selectDate(datePicker.getCurrentDay());
        return this;
    }

    /**
     * Select End day in DatePicker
     * @return
     */
    public ResultPage selectEndDate(){
        txtEndDate.click();
        DatePicker datePicker = new DatePicker();
        datePicker.selectDate(datePicker.getCurrentDay() + 2);
        return this;
    }
}
