package acetoys.pageobejcts;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

import static acetoys.session.UserSession.increaseItemsInBasketForSession;
import static acetoys.session.UserSession.increaseSessionBasketTotal;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
public class Products {

    private static final FeederBuilder<Object> productFeeder =
            jsonFile("data/productDetails.json").random();
    public static ChainBuilder loadProductDetailsPage =
            feed(productFeeder)
                    .exec(
                    http("Load products details page - Product : #{name}")
                            .get("/product/#{slug}")
                            .check(css("#ProductDescription").isEL("#{description}"))
            );

    public static ChainBuilder addProductToCart =
            exec(increaseItemsInBasketForSession)
                    .exec(
                    http("Add product to the cart - Product ID:#{name}")
                            .get("/cart/add/#{id}")
                            .check(substring("You have <span>#{itemsInBasket}</span> products in your Basket."))
            ).exec(increaseSessionBasketTotal);

}
