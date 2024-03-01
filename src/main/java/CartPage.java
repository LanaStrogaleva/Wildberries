import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
public class CartPage {
    private final WebDriver webDriver;
    private ArrayList<Good> goodList;

    public CartPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.goodList = new ArrayList<>();
    }

    public ArrayList<Good> getGoodList() {
        return goodList;
    }


    //Наименование товара в корзине
    private By goodName = By.className("good-info__good-name");

    //Цена товара в корзине

    private By goodPrice = By.xpath(".//*[contains(@class, 'list-item__price-wallet')][1]");
    //private By goodPrice = By.xpath(".//*[@class = 'list-item__price']/div[@data-link=\"{formatMoneyAnim priceSumWithCouponAndDiscount}\"][1]");

    private By goodQuantity = By.xpath(".//*[@class = 'count__input-number']/input");

    // Общее количество товаров
    private By totalGoodQuantity = By.xpath(".//*[contains(@class, 'basket-order__b-top')]/div/span[1]");

    // Общая сумма
    private By totalSum = By.xpath(".//*[contains(@class, 'basket-order__b-top')]/div/span[2]");


    // Получить наименование товара
    public String getGoodName(int index) {
        return webDriver.findElements(goodName).get(index).getText();
    }

    // Получить цену товара
    public int getGoodPrice(int index) throws InterruptedException {
        WebElement el = webDriver.findElements(goodPrice).get(index);
        Thread.sleep(1000);
        return Integer.parseInt(el.getText().substring(0, el.getText().length() - 2).replace(" ", ""));
    }

    // получить количество товара
    public int getGoodQuantity(int index) {
        return Integer.parseInt(webDriver.findElements(goodQuantity).get(index).getAttribute("value"));
    }

    public ArrayList<Good> addGoodsList(int quantity) throws InterruptedException {
        System.out.println(quantity);
        for (int i = quantity - 1; i >= 0; i--) {
            this.goodList.add(new Good(this.getGoodName(i), this.getGoodPrice(i), this.getGoodQuantity(i)));
        }
        return goodList;
    }

    // Получить общее количество товаров в корзине
    public int getTotalGoodQuantity() {
        return Integer.parseInt(webDriver.findElement(totalGoodQuantity).getText().substring(8, 9));
    }

    // Получить общую цену товаров со скидкой

    public int getTotalSum() throws InterruptedException {
        Thread.sleep(1000);
        String strTotalSum = webDriver.findElement(totalSum).getText();
        return Integer.parseInt(strTotalSum.substring(0, strTotalSum.length() - 2).replace(" ", ""));
    }
}
