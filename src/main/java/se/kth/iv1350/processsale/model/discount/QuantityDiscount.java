package se.kth.iv1350.processsale.model.discount;

import java.util.ArrayList;

import se.kth.iv1350.processsale.dto.ItemDTO;
import se.kth.iv1350.processsale.model.Amount;

public class QuantityDiscount implements Discount {

    @Override
    public Amount priceAfterDiscount(Amount totalCost, ArrayList<ItemDTO> itemsRegistredInSale) {
        int lowestPrice = itemsRegistredInSale.get(0).getPrice().getAmount();
        for(ItemDTO item: itemsRegistredInSale)
            if(item.getPrice().getAmount() < lowestPrice)
                lowestPrice = item.getPrice().getAmount();

        Amount priceAfterDiscount = new Amount(totalCost.getAmount() - lowestPrice);
        return priceAfterDiscount;
    }
    
}
