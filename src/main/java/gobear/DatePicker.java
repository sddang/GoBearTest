package gobear;

import com.monkey.api.MonkeyLogger;
import com.monkey.api.Wait;
import com.monkey.api.annotation.WebLocator;
import com.monkey.api.enumeration.LogLevel;
import com.monkey.api.enumeration.Selector;
import com.monkey.api.page.MonkeyWebElement;
import com.monkey.api.page.MonkeyWebPage;
import com.monkey.api.web.workstation.Navigator;
import org.openqa.selenium.By;

import java.util.Calendar;
import java.util.TimeZone;

public class DatePicker extends MonkeyWebPage {

    /***** Capture Elements *****/
    @WebLocator(identifier = ".datepicker .datepicker-days table", selector = Selector.cssSelector)
    MonkeyWebElement tbDatePicker;

    /***** Implementing *****/


    /**
     * Get Current Day
     * @return
     */
    public int getCurrentDay(){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Select Day in DatePicker
     * @param day
     * @return
     */
    public DatePicker selectDate(int day){
        String dateCell = ".//tr/td[not(contains(@class,'disabled day'))][text()='" + day + "']";
        tbDatePicker.getWebElement().findElement(By.xpath(dateCell)).click();
        Wait.waitForPageToLoad(2);
        return this;
    }



}
