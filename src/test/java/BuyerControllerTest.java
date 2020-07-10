import client.controller.MainController;
import client.controller.account.user.BuyerController;
import mockit.Injectable;
import mockit.Tested;
import client.model.account.Buyer;
import client.model.product.Discount;

public class BuyerControllerTest {

    @Tested
    BuyerController buyerController;
    MainController mainController;

    @Injectable
    Buyer buyer;
    Discount discount;


}
