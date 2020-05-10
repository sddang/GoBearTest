package gobear;

import com.monkey.api.MonkeyLogger;
import com.monkey.api.annotation.WebLocator;
import com.monkey.api.enumeration.LogLevel;
import com.monkey.api.enumeration.Selector;
import com.monkey.api.page.MonkeyWebElement;
import com.monkey.api.page.MonkeyWebPage;
import com.monkey.api.web.workstation.Navigator;
import org.openqa.selenium.By;

public class GoBearPage extends MonkeyWebPage {

    /***** Capture Elements *****/
    @WebLocator(identifier = ".//ul[@class='nav nav-tabs nav-justified']/li", selector = Selector.xpath)
    MonkeyWebElement tabProducts;

    @WebLocator(identifier = ".//button[@class='btn btn-primary btn-block text-uppercase btn-form-submit']", selector = Selector.xpath)
    MonkeyWebElement btnShowMyResults;

    /***** Implementing *****/
    static String selectedProductTab = "";
    private static GoBearPage goBearPageInstance;

    public GoBearPage(){}

    /**
     * Init Page
     * @return
     */
    public static GoBearPage getPage(){
        if(goBearPageInstance == null){
            goBearPageInstance = new GoBearPage();
        }
        return goBearPageInstance;
    }

    /**
     * Open Gobear Page
     *
     * @return
     */
    public GoBearPage gotoGoBear() {
        Navigator.openUrl("https://www.gobear.com/ph?x_session_type=UAT");
        return this;
    }

    /**
     * Select Product Tab at Search Page
     * @param tabName String - Tab name should be "Insurance", "Credit cards" and "Loan"
     * @return
     */
    public GoBearPage selectProductTab(String tabName){
        tabProducts.getWebElement().findElement(By.xpath(".//a[@aria-controls='" + tabName+ "']")).click();
        selectedProductTab = tabName;
        return this;
    }

    /**
     * This method is used to select sub tab name which are under product tab.
     * *Note* use this method after selectProductTab
     * @param tabName String
     * @return
     */
    public GoBearPage selectSubProductTab(String tabName){
        if(!selectedProductTab.equals("")) {
            MonkeyWebElement tabPanel = new MonkeyWebElement("div#" + selectedProductTab + " a[aria-controls='" + tabName + "']", Selector.cssSelector);
            tabPanel.click();
        }else{
            MonkeyLogger.log(this.getClass().getName(),"Please select Product Tab before selecting sub tab", LogLevel.INFO);
        }
        return this;
    }

    /**
     * Click show result to go to result page
     * @return
     */
    public GoBearPage clickShowResult(){
        btnShowMyResults.click();
        return this;
    }

}
