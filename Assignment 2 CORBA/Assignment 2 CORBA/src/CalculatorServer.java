import org.omg.CORBA.*;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

import CalculatorApp.*;
import CalculatorApp.CalculatorPOA;
import CalculatorApp.CalculatorPOA;

class CalculatorImpl extends CalculatorPOA {
    private ORB orb;

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }
 
    // implement calculator methods
    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        return a / b;
    }

    public void shutdown() {
        orb.shutdown(false);
    }
}

public class CalculatorServer {
    public static void main(String args[]) {
        try {
            // create and initialize the ORB
            ORB orb = ORB.init(args, null);

            // get reference to rootpoa & activate the POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // create servant and register it with the ORB
            CalculatorImpl calculatorImpl = new CalculatorImpl();
            calculatorImpl.setORB(orb);

            // get object reference from the servant
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(calculatorImpl);
            Calculator href = CalculatorHelper.narrow(ref);

            // get the root naming context
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // bind the Object Reference in Naming
            String name = "Calculator";
            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, href);

            System.out.println("CalculatorServer running...");

            // wait for invocations from clients
            orb.run();
            System.out.println("CalculatorServer running...");

        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }

        System.out.println("CalculatorServer exiting...");
    }
}
