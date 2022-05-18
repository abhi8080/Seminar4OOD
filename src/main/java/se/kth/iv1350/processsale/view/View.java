package se.kth.iv1350.processsale.view;

import se.kth.iv1350.processsale.controller.Controller;
import se.kth.iv1350.processsale.controller.OperationFailedException;
import se.kth.iv1350.processsale.integration.InvalidItemIdentifierException;
import se.kth.iv1350.processsale.model.Amount;
import se.kth.iv1350.processsale.util.TotalRevenueFileOutput;

/**
 *
 * This class is a placeholder for the view and contains hard-coded calls to the
 * controlle
 */
public class View {

    private Controller controller;
    private TotalRevenueView totalRevenueView;
    private TotalRevenueFileOutput totalRevenueFileOutput;


    /**
     * Creates a new instance.
     *
     * @param controller The application's controller.
     */
    public View(Controller controller) {
        this.controller = controller;
        totalRevenueView = new TotalRevenueView();
        totalRevenueFileOutput = new TotalRevenueFileOutput();
        controller.addSaleObserver(totalRevenueView);
        controller.addSaleObserver(totalRevenueFileOutput);

    }

    /**
     * Calls all the system operations.
     */
    public void sampleExecution()  throws InvalidItemIdentifierException {
        
        try{
        controller.startSale();
        System.out.println("Start of a new sale\n");
        System.out.println("Item with identifier 934632865 is scanned");
        System.out.println(controller.scanItem(934632865));
        System.out.println("Item with identifier 121936821 is scanned");
        System.out.println(controller.scanItem(121936821));
        System.out.println("Item with identifier 531632821 is scanned");
        System.out.println(controller.scanItem(531632821));
        System.out.println("Item with identifier 171172841 is scanned");
        System.out.println(controller.scanItem(171172841));
        System.out.println("Item with identifier 121936821 is scanned");
        System.out.println(controller.scanItem(121936821));
        Amount totalCost = controller.endSale();
        System.out.println("\nSale has ended. Total cost is: " + totalCost + " SEK.");
        System.out.println("Discount request. Enter customer identification.");
        Amount totalCostAfterDiscount = controller.discountRequest("983431347893");
            if(totalCostAfterDiscount.getAmount() < totalCost.getAmount())
            {
              System.out.println("Discount Applied Successfully");
              System.out.println("The total cost now is: " + totalCostAfterDiscount + " SEK.");
            }
            else
            System.out.println("Customer is not eligible for discount");
        Amount amountPaidByCustomer = new Amount(100);
        System.out.println("Customer pays " + amountPaidByCustomer + " SEK.\n");
        System.out.println("------RECEIPT------\n");
        controller.pay(amountPaidByCustomer);
        }
        catch (InvalidItemIdentifierException exc) {
            System.out.println(exc.getMessage());
        } catch (OperationFailedException exc) {
            System.out.println("----For the user interface----");
            System.out.println("Scanning of item unsuccessful, please try again.\n\n\n");
            System.out.println("-----For the Log------");
            System.out.println(exc.getMessage());
        }

    }
}
