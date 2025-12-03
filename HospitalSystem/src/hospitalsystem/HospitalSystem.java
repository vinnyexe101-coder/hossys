import java.util.*;

public class HospitalSystem {

    static int patientCounter = 1;
    static int billCounter = 1;

    public static String generatePatientID() {
        return String.format("P%02d", patientCounter++);
    }

    public static String generateBillID() {
        return String.format("B%02d", billCounter++);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Setup doctors, nurses, pharmacy, disease map
        List<Doctor> doctors = setupDoctors();
        List<Nurse> nurses = setupNurses();
        Pharmacy pharmacy = new Pharmacy();
        Map<Integer, DiseaseInfo> diseaseMap = setupDiseases();

        // === Fill Up ===
        Patient patient = admitPatient(sc);

        // === Doctor Decision ===
        System.out.print("\nDo you need a doctor? (yes/no): ");
        boolean wantDoctor = sc.nextLine().trim().equalsIgnoreCase("yes");

        Billing bill = new Billing(patient.getId());

        if (wantDoctor) {
            handleDoctorFlow(sc, patient, doctors, nurses, pharmacy, diseaseMap, bill);
        } else {
            handlePharmacyFlow(sc, patient, pharmacy, bill);
        }

        // === Services Decision ===
        handleServicesFlow(sc, bill);

        // === Billing & Discharge ===
        handleBillingAndDischarge(sc, bill, patient);

        sc.close();
    }

    // ------------------- Helper Methods -------------------

    private static Patient admitPatient(Scanner sc) {
        System.out.println("=== PATIENT ADMISSION ===");
        System.out.print("Enter patient name: ");
        String name = sc.nextLine().trim();

        int age;
        while (true) {
            System.out.print("Enter age: ");
            try {
                age = Integer.parseInt(sc.nextLine().trim());
                if (age >= 0) break;
                System.out.println("Age cannot be negative.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number.");
            }
        }

        String gender;
        while (true) {
            System.out.print("Enter gender (M/F): ");
            gender = sc.nextLine().trim().toUpperCase();
            if (gender.equals("M") || gender.equals("F")) break;
            System.out.println("Invalid input.");
        }

        System.out.print("Enter contact: ");
        String contact = sc.nextLine().trim();

        Patient patient = new Patient(name, age, gender, contact);
        patient.admit();
        return patient;
    }

    private static void handleDoctorFlow(Scanner sc, Patient patient, List<Doctor> doctors,
                                         List<Nurse> nurses, Pharmacy pharmacy,
                                         Map<Integer, DiseaseInfo> diseaseMap, Billing bill) {
        System.out.println("\n--- Disease / Reason for visit ---");
        for (Map.Entry<Integer, DiseaseInfo> e : diseaseMap.entrySet()) {
            System.out.println(e.getKey() + ". " + e.getValue().diseaseName);
        }

        int choice;
        while (true) {
            System.out.print("Choose disease number (or 0 for General): ");
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
                if (choice == 0) choice = 1;
                if (diseaseMap.containsKey(choice)) break;
            } catch (NumberFormatException ignored) {}
            System.out.println("Invalid choice.");
        }

        DiseaseInfo disease = diseaseMap.get(choice);
        patient.doctor = doctors.get(disease.doctorIndex);
        patient.doctor.provideService(patient);

        patient.nurses.clear();
        for (int ni : disease.nurseIndices) {
            if (ni >= 0 && ni < nurses.size()) patient.nurses.add(nurses.get(ni));
        }
        for (Nurse n : patient.nurses) n.provideService(patient);

        System.out.println("Ward: " + disease.ward);

        // Prescription step
        if (!disease.recommendedMedsAndDose.isEmpty()) {
            System.out.print("Add recommended medicines to bill? (yes/no): ");
            if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
                for (Map.Entry<String, String> med : disease.recommendedMedsAndDose.entrySet()) {
                    double price = pharmacy.getPrice(med.getKey());
                    bill.addMedicine(med.getKey() + " (" + med.getValue() + ")", 1, price);
                }
            }
        }

        bill.addService("Doctor Consultation (" + patient.doctor.getSpecialty() + ")", 500);
    }

    private static void handlePharmacyFlow(Scanner sc, Patient patient, Pharmacy pharmacy, Billing bill) {
        System.out.print("\nDo you want to buy medicines? (yes/no): ");
        if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
            pharmacy.provideService(patient);
            pharmacy.showMedicinesAlphabetical();
            System.out.print("Enter medicine number: ");
            int choice = Integer.parseInt(sc.nextLine().trim());
            String med = pharmacy.getMedicineByIndex(choice);
            double price = pharmacy.getPrice(med);
            bill.addMedicine(med, 1, price);
        }
    }

    private static void handleServicesFlow(Scanner sc, Billing bill) {
        System.out.print("\nDo you want hospital services? (yes/no): ");
        if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
            System.out.println("1. X-Ray ₱800\n2. Blood Test ₱300\n3. ECG ₱700\n4. Ultrasound ₱900");
            System.out.print("Choose service number: ");
            int choice = Integer.parseInt(sc.nextLine().trim());
            switch (choice) {
                case 1: bill.addService("X-Ray", 800); break;
                case 2: bill.addService("Blood Test", 300); break;
                case 3: bill.addService("ECG", 700); break;
                case 4: bill.addService("Ultrasound", 900); break;
            }
        }
    }

    private static void handleBillingAndDischarge(Scanner sc, Billing bill, Patient patient) {
        bill.generateBill();
        System.out.print("Enter payment: ₱");
        double payment = Double.parseDouble(sc.nextLine().trim());
        bill.applyPayment(payment);
        patient.discharge();
    }

    // ------------------- Setup Methods -------------------
    private static List<Doctor> setupDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor("Dr. Reyes (General)", "General Medicine"));
        doctors.add(new Doctor("Dr. Santos", "Cardiology"));
        // ... add rest
        return doctors;
    }

    private static List<Nurse> setupNurses() {
        List<Nurse> nurses = new ArrayList<>();
        nurses.add(new Nurse("Nurse Joy", "Ward A"));
        nurses.add(new Nurse("Nurse Mary", "Ward B"));
        // ... add rest
        return nurses;
    }

    private static Map<Integer, DiseaseInfo> setupDiseases() {
        Map<Integer, DiseaseInfo> map = new LinkedHashMap<>();
        DiseaseInfo d1 = new DiseaseInfo("Consultation", 0, new int[]{0,1}, "Outpatient");
        d1.addRecommendedMed("Biogesic – Paracetamol 500 mg", "500 mg every 4-6 hrs");
        map.put(1, d1);
        // ... add rest
        return map;
    }
}
