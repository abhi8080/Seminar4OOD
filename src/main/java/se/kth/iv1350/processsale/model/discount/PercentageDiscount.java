package se.kth.iv1350.processsale.model.discount;

import java.util.ArrayList;

import se.kth.iv1350.processsale.dto.ItemDTO;
import se.kth.iv1350.processsale.model.Amount;

public class PercentageDiscount implements Discount {

    @Override
    public Amount priceAfterDiscount(Amount totalCost, ArrayList<ItemDTO> itemsRegistredInSale) {

        int discount = (int) (totalCost.getAmount() * 0.10);

        return new Amount(totalCost.getAmount() - discount);
    }
}