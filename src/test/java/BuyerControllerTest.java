/*import static org.junit.Assert.*;
import controller.account.user.BuyerController;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import model.account.Buyer;
import model.product.Discount;
import org.junit.Test;

public class BuyerControllerTest {

    @Tested
    BuyerController buyerController = BuyerController.getInstance();

    @Injectable
    Buyer buyer;
    Discount discount;

    @Test
    public void testPurchase() {

        new Expectations() {
            {
                try {
                    Discount.validDiscountCodeBuyer(buyer, 1234); result = true;
                    Discount.getDiscountByDiscountCode(1234); result = discount;
                    discount.getDiscountPercentage(); result = 0.1;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };
        try {
            buyerController.purchase("Canada", "0912817", "1234");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
*/