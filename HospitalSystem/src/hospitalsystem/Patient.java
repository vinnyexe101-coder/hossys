import java.util.ArrayList;
import java.util.List;

public class Patient extends HospitalEntity implements Person {
    String name, gender, contact;
    int age;
    Doctor doctor;
    List<Nurse> nurses = new ArrayList<>();

    public Patient(String name, int age, String gender, String contact) {
        this.id = HospitalSystem.generatePatientID();
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
        this.status = "New";
    }

    public void admit() {
        status = "Admitted";
        System.out.println("\nPatient " + name + " admitted. ID: " + id);
    }

    public void discharge() {
        status = "Discharged";
        System.out.println("\nPatient " + name + " has been discharged.");
    }

    @Override
    public void showInfo() {
        System.out.println(name + " (" + (gender.equalsIgnoreCase("M") ? "Male" : "Female") + ", " + age + " yrs)");
    }
}

