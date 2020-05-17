import controller.MainController;
import controller.account.user.BuyerController;
import mockit.Injectable;
import mockit.Tested;
import model.account.Buyer;
import model.product.Discount;

public class BuyerControllerTest {

    @Tested
    BuyerController buyerController;
    MainController mainController;

    @Injectable
    Buyer buyer;
    Discount discount;


}
