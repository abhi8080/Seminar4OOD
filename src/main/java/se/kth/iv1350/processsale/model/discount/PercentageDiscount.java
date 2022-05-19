package se.kth.iv1350.processsale.model.discount;

import java.util.ArrayList;

import se.kth.iv1350.processsale.dto.ItemDTO;
import se.kth.iv1350.processsale.model.Amount;

/**
 * A discount type which gives 10% off the total cost.
 */
public class PercentageDiscount implements Discount {

    /**
     * Calculates discount.
     *
     * @param totalCost Total cost of the sale.
     * @param itemsRegistredInSale The items registred in the sale. 
     * @return Total cost after discount.
     */
    @Override
    public Amount calculateDiscount(Amount totalCost, ArrayList<ItemDTO> itemsRegistredInSale) {

        int discount = (int) (totalCost.getAmount() * 0.10);

        return new Amount(totalCost.getAmount() - discount);
    }
}
