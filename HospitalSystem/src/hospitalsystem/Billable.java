public interface Billable {
    void generateBill();
    void applyPayment(double paid);
}
