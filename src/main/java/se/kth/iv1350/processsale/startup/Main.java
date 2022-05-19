package se.kth.iv1350.processsale.startup;

import se.kth.iv1350.processsale.controller.Controller;
import se.kth.iv1350.processsale.integration.Printer;
import se.kth.iv1350.processsale.integration.SystemCreator;
import se.kth.iv1350.processsale.view.View;

/**
 * All startup of the application is performed by this class.
 */
public class Main {

    /**
     * Initiates the application.
     *
     * @param args Command line parameters are not taken.
     *
     */
    public static void main(String[] args) {

        Printer printer = new Printer();
        SystemCreator systemCreator = new SystemCreator();
        Controller controller = new Controller(systemCreator, printer);
        new View(controller).sampleExecution();

    }

}
