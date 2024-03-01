import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
public class MainPage {
    private ArrayList<Good> goodList;
    private static final String URL = "https://www.wildberries.ru/";

    private final WebDriver webDriver;

    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.goodList = new ArrayList<>();
    }

    public ArrayList<Good> getGoodList() {
        return goodList;
    }

    // список карточек товаров на главной странице
    private By goodsList = By.className("product-card__wrapper");

    // Кнопка "В корзину"
    private By cartButton = By.xpath(".//*[contains(@class, 'product-card__add-basket')]");

    // Таблица размеров карточки (если имеется)
    private By sizeList = By.xpath("//*[contains(@class, 'sizes-list__button')]");


    // Корзина
    private By headerCartButton = By.xpath(".//*[contains(@class, 'navbar-pc__icon--basket')]");

    // Блок карточки с наименованием товара
    private By goodName = By.xpath(".//*[@class= 'product-card__name']");

    // Блок карточки с ценой товара
    private By goodPrice = By.xpath(".//ins[contains(@class, 'price__lower-price')]");

    // Открыть главную страницу
    public void open() {
        webDriver.get(URL);
    }

    public MainPage addSomeGoodsToCart(int quantity) {
        for (int i = 0; i < quantity; i++) {
            Actions action = new Actions(webDriver);
            WebElement element = webDriver.findElements(goodsList).get(i);
            action.moveToElement(element).perform();
            webDriver.findElements(cartButton).get(i).click();
            if (!webDriver.findElements(By.className("sizes-list__size")).isEmpty()) {
                WebElement el = webDriver.findElements(sizeList).get(0);
                action.moveToElement(el).perform();
                el.click();
            }
            this.goodList.add(new Good(this.getGoodName(i), this.getGoodPrice(i), 1));
        }
        return this;
    }

    // Кликнуть на иконку Корзины в правом верхнем углу

    public void clickHeaderCartButton() {
        webDriver.findElement(headerCartButton).click();
    }

    // Получить наименование товара из карточки товара
    public String getGoodName(int index) {
        return webDriver.findElements(goodName).get(index).getText().substring(2);
    }

    // Получить цену товара из карточки товара
    public int getGoodPrice(int index) {
        String strPrice = webDriver.findElements(goodPrice).get(index).getText();
        return Integer.parseInt(strPrice.substring(0, strPrice.length() - 2).replace(" ", ""));
    }

    // Получить общую сумму товаров
    public int getGoodsSum() {
        int totalSum = 0;
        for (Good good : this.goodList
        ) {
            totalSum += good.getPrice();
        }
        return totalSum;
    }

    // Получить общee количество товаров
    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (Good good : this.goodList
        ) {
            totalQuantity += good.getQuantity();
        }
        return totalQuantity;
    }
}
