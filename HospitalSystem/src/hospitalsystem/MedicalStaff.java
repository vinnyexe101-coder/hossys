public abstract class MedicalStaff extends HospitalEntity implements Person, ServiceProvider {
    protected String name;

    public MedicalStaff(String name) {
        this.name = name;
    }

    @Override
    public abstract void provideService(Patient patient);

    @Override
    public abstract void showInfo();

    public String getName() { return name; }
}
