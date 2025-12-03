public class Nurse extends MedicalStaff {
    String ward;

    public Nurse(String name, String ward) {
        super(name);
        this.ward = ward;
        this.status = "Active";
    }

    @Override
    public void provideService(Patient patient) {
        System.out.println(name + " is facilitating care for " + patient.name + " in " + ward);
    }

    @Override
    public void showInfo() {
        System.out.println(name + " (Ward: " + ward + ")");
    }

    public String getWard() { return ward; }
}
