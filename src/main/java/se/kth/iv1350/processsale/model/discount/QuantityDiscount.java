package se.kth.iv1350.processsale.model.discount;

import java.util.ArrayList;

import se.kth.iv1350.processsale.dto.ItemDTO;
import se.kth.iv1350.processsale.model.Amount;

/**
 *
 * A discount type which makes the cheapest item free if there are more than 4
 * items bought in the sale.
 */
public class QuantityDiscount implements Discount {

    /**
     * Calculates discount.
     *
     * @param totalCost Total cost of the sale.
     * @param itemsRegistredInSale The items registred in the sale,
     * @return Total cost after discount.
     */
    @Override
    public Amount calculateDiscount(Amount totalCost, ArrayList<ItemDTO> itemsRegistredInSale) {
        int lowestPrice = itemsRegistredInSale.get(0).getPrice().getAmount();
        for (ItemDTO item : itemsRegistredInSale) {
            if (item.getPrice().getAmount() < lowestPrice) {
                lowestPrice = item.getPrice().getAmount();
            }
        }

        Amount priceAfterDiscount = new Amount(totalCost.getAmount() - lowestPrice);
        return priceAfterDiscount;
    }

}
