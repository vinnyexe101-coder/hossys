import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Billing extends HospitalEntity implements Billable {
    List<String> services = new ArrayList<>();
    Map<String, Double> medicines = new LinkedHashMap<>();
    double totalAmount = 0;
    String patientID;

    public Billing(String patientID) {
        this.id = HospitalSystem.generateBillID();
        this.patientID = patientID;
        this.status = "Open";
    }

    public void addService(String service, double price) {
        services.add(service + " (₱" + price + ")");
        totalAmount += price;
    }

    public void addMedicine(String medDisplayName, int qty, double price) {
        double cost = qty * price;
        medicines.put(medDisplayName + " x" + qty, cost);
        totalAmount += cost;
    }

    @Override
    public void generateBill() {
        System.out.println("\n=== BILL ===");
        System.out.println("Bill ID: " + id);
        System.out.println("Patient ID: " + patientID);
        System.out.println("\nServices:");
        if (services.isEmpty()) System.out.println("- None");
        for (String s : services) System.out.println("- " + s);
        System.out.println("\nMedicines:");
        if (medicines.isEmpty()) System.out.println("- None");
        for (String m : medicines.keySet())
            System.out.println("- " + m + ": ₱" + medicines.get(m));
        System.out.println("\nTOTAL: ₱" + totalAmount);
    }

    @Override
    public void applyPayment(double paid) {
        if (paid >= totalAmount) {
            System.out.println("Payment accepted! Change: ₱" + (paid - totalAmount));
            status = "Closed";
        } else {
            System.out.println("Insufficient payment! Remaining Balance: ₱" + (totalAmount - paid));
            status = "Partial";
        }
    }
}
