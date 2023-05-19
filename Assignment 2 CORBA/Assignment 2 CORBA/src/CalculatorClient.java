import CalculatorApp.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;

import java.util.Scanner;

//$ orbd -ORBInitialPort 1050
//$ tnameserv -ORBInitialPort 1050

public class CalculatorClient {
    public static void main(String args[]) {
        try {
            // Initialize the ORB
            ORB orb = ORB.init(args, null);

            // Get a reference to the root naming context
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // Resolve the object reference in naming
            NameComponent path[] = ncRef.to_name("Calculator");
            Calculator calculator = CalculatorHelper.narrow(ncRef.resolve(path));

            // Get inputs from the user
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter num1: ");
            double a = scanner.nextDouble();
            System.out.print("Enter num2: ");
            double b = scanner.nextDouble();

            // Ask which operation to perform
            System.out.println("Choose operation : ");
            System.out.println("1) Addition");
            System.out.println("2) Subtraction");
            System.out.println("3) Multiplication : ");
            System.out.println("4) Division : ");
            int operation = scanner.nextInt();

            // Invoke the methods on the object based on the selected operation
            double result = 0;
            if (operation == 1) {
                result = calculator.add(a, b);
            } else if (operation == 2) {
                result = calculator.subtract(a, b);
            }
            else if (operation == 3) {
                result = calculator.multiply(a, b);
            }
            else if (operation == 4) {
                result = calculator.divide(a, b);
            }
            else{
                System.out.println("Invalid option");
                return;
            }

            // Print the result
            System.out.println("Result = " + result);

        } catch (Exception e) {
            System.err.println("Error: " + e);
            e.printStackTrace(System.out);
        }
    }
}
