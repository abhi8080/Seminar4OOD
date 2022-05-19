package se.kth.iv1350.processsale.model.discount;

import java.util.ArrayList;

import se.kth.iv1350.processsale.dto.ItemDTO;
import se.kth.iv1350.processsale.model.Amount;

/**
 *
 * A class providing a discount calculation algorithm should implement this
 * interface.
 */
public interface Discount {

    Amount calculateDiscount(Amount totalCost, ArrayList<ItemDTO> itemsRegistredInSale);
}
