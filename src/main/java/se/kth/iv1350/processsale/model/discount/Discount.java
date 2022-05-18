package se.kth.iv1350.processsale.model.discount;

import java.util.ArrayList;

import se.kth.iv1350.processsale.dto.ItemDTO;
import se.kth.iv1350.processsale.model.Amount;

public interface Discount {

    Amount priceAfterDiscount(Amount totalCost, ArrayList<ItemDTO> itemsRegistredInSale);
}
