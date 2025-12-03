public class Doctor extends MedicalStaff {
    String specialty;

    public Doctor(String name, String specialty) {
        super(name);
        this.specialty = specialty;
        this.status = "Active";
    }

    @Override
    public void provideService(Patient patient) {
        System.out.println("Dr. " + name + " (" + specialty + ") is checking patient " + patient.name);
    }

    @Override
    public void showInfo() {
        System.out.println(name + " (" + specialty + ")");
    }

    public String getSpecialty() { return specialty; }
}
