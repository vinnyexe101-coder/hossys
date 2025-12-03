import java.util.*;

public class HospitalSystem {

    // ID Generators
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

        // --- create 15 doctors (include General)
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor("Dr. Reyes (General)", "General Medicine"));            // index 0 general
        doctors.add(new Doctor("Dr. Santos", "Cardiology"));                          // 1
        doctors.add(new Doctor("Dr. Cruz", "Pediatrics"));                            // 2
        doctors.add(new Doctor("Dr. Delos", "Pulmonology"));                          // 3
        doctors.add(new Doctor("Dr. Navarro", "Gastroenterology"));                   // 4
        doctors.add(new Doctor("Dr. Valdez", "Endocrinology"));                       // 5
        doctors.add(new Doctor("Dr. Mendoza", "Nephrology"));                         // 6
        doctors.add(new Doctor("Dr. Bautista", "Neurology"));                         // 7
        doctors.add(new Doctor("Dr. Lim", "Orthopedics"));                            // 8
        doctors.add(new Doctor("Dr. Flores", "Dermatology"));                         // 9
        doctors.add(new Doctor("Dr. Aquino", "ENT"));                                 //10
        doctors.add(new Doctor("Dr. Santos II", "Infectious Diseases"));              //11
        doctors.add(new Doctor("Dr. Ramos", "Rheumatology"));                         //12
        doctors.add(new Doctor("Dr. Ortega", "Psychiatry"));                          //13
        doctors.add(new Doctor("Dr. Tan", "Ophthalmology"));                          //14

        // --- create 9 nurses
        List<Nurse> nurses = new ArrayList<>();
        nurses.add(new Nurse("Nurse Joy", "Ward A"));   //0
        nurses.add(new Nurse("Nurse Mary", "Ward B"));  //1
        nurses.add(new Nurse("Nurse Ana", "Ward C"));   //2
        nurses.add(new Nurse("Nurse Lee", "Ward D"));   //3
        nurses.add(new Nurse("Nurse Cruz", "Ward E"));  //4
        nurses.add(new Nurse("Nurse Vega", "Ward F"));  //5
        nurses.add(new Nurse("Nurse Santos", "Ward G"));//6
        nurses.add(new Nurse("Nurse Miguel", "Ward H"));//7
        nurses.add(new Nurse("Nurse Perez", "Ward I")); //8

        Pharmacy pharmacy = new Pharmacy();

        // Build disease map (15 diseases)
        Map<Integer, DiseaseInfo> diseaseMap = new LinkedHashMap<>();

        DiseaseInfo d1 = new DiseaseInfo("Consultation", 0, new int[]{0,1}, "Outpatient");
        d1.addRecommendedMed("Biogesic – Paracetamol 500 mg", "500 mg every 4-6 hrs (if needed)");
        diseaseMap.put(1, d1);

        DiseaseInfo d2 = new DiseaseInfo("Hypertension (High Blood Pressure)", 1, new int[]{2,3}, "Cardio Ward");
        d2.addRecommendedMed("Amlodipine – 5 / 10 mg", "5-10 mg once daily");
        d2.addRecommendedMed("Aspirin Low Dose – 80 mg", "81 mg once daily (if indicated)");
        diseaseMap.put(2, d2);

        DiseaseInfo d3 = new DiseaseInfo("Pneumonia / Severe Cough", 3, new int[]{1,2}, "Respiratory Ward");
        d3.addRecommendedMed("Amoxicillin – 500 mg", "500 mg every 8 hrs (antibiotic)");
        d3.addRecommendedMed("Salbutamol – 2 mg", "as prescribed");
        diseaseMap.put(3, d3);

        DiseaseInfo d4 = new DiseaseInfo("Pediatric Fever/Illness", 2, new int[]{2,4}, "Pediatrics");
        d4.addRecommendedMed("Biogesic – Paracetamol 500 mg", "15 mg/kg (follow pediatric dosing)");
        diseaseMap.put(4, d4);

        DiseaseInfo d5 = new DiseaseInfo("Stomach Pain / GI Issues", 4, new int[]{3,5}, "Gastro Ward");
        d5.addRecommendedMed("Omeprazole – 20 mg", "20 mg once daily");
        d5.addRecommendedMed("Loperamide – 2 mg", "2 mg after each loose stool (if indicated)");
        diseaseMap.put(5, d5);

        DiseaseInfo d6 = new DiseaseInfo("Diabetes / Endocrine", 5, new int[]{5,6}, "Endocrine Ward");
        d6.addRecommendedMed("Metformin – 500 / 850 mg", "500 mg twice daily");
        d6.addRecommendedMed("Glimepiride – 2 mg", "as prescribed");
        diseaseMap.put(6, d6);

        DiseaseInfo d7 = new DiseaseInfo("Kidney Issues / UTI", 6, new int[]{6,7}, "Nephrology");
        d7.addRecommendedMed("Nitrofurantoin – 100 mg", "as prescribed");
        diseaseMap.put(7, d7);

        DiseaseInfo d8 = new DiseaseInfo("Headache / Neurological Concern", 7, new int[]{7,8}, "Neurology");
        d8.addRecommendedMed("Ponstan – Mefenamic Acid 500 mg", "500 mg as prescribed");
        diseaseMap.put(8, d8);

        DiseaseInfo d9 = new DiseaseInfo("Bone / Fracture / Ortho", 8, new int[]{8,0}, "Orthopedics");
        d9.addRecommendedMed("Medicol Advance – Ibuprofen 200/400 mg", "200-400 mg as needed");
        diseaseMap.put(9, d9);

        DiseaseInfo d10 = new DiseaseInfo("Skin Infection / Derm", 9, new int[]{0,1}, "Dermatology");
        d10.addRecommendedMed("Clotrimazole – 1% cream", "apply 2x daily");
        diseaseMap.put(10, d10);

        DiseaseInfo d11 = new DiseaseInfo("ENT (Ear/Nose/Throat)", 10, new int[]{1,2}, "ENT");
        d11.addRecommendedMed("Azithromycin – 500 mg", "as prescribed");
        diseaseMap.put(11, d11);

        DiseaseInfo d12 = new DiseaseInfo("Serious Infection", 11, new int[]{2,3}, "Infectious Diseases");
        d12.addRecommendedMed("Co-Amoxiclav – 500 mg + 125 mg", "as prescribed");
        diseaseMap.put(12, d12);

        DiseaseInfo d13 = new DiseaseInfo("Autoimmune / Rheumatologic", 12, new int[]{3,4}, "Rheumatology");
        d13.addRecommendedMed("Aspirin Low Dose – 80 mg", "as prescribed");
        diseaseMap.put(13, d13);

        DiseaseInfo d14 = new DiseaseInfo("Psychiatric Evaluation", 13, new int[]{4,5}, "Psychiatry");
        diseaseMap.put(14, d14);

        DiseaseInfo d15 = new DiseaseInfo("Eye / Vision Issue", 14, new int[]{5,6}, "Ophthalmology");
        diseaseMap.put(15, d15);

        System.out.println("=== HOSPITAL SYSTEM (Flow: Fill Up -> Doctor -> Pharmacy/Services -> Billing -> Discharge) ===");

        // 1. Patient Admission
        System.out.println("=== PATIENT ADMISSION ===");
        System.out.print("Enter patient name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter age: ");
        int age;
        while (true) {
            String ageInput = sc.nextLine().trim();
            try {
                age = Integer.parseInt(ageInput);
                if (age < 0) {
                    System.out.print("Age cannot be negative. Enter age: ");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid age. Enter a number: ");
            }
        }

        String gender;
        while (true) {
            System.out.print("Enter gender (M/F): ");
            gender = sc.nextLine().trim().toUpperCase();
            if (gender.equals("M") || gender.equals("F")) break;
            System.out.println("Invalid input. Please enter 'M' for Male or 'F' for Female.");
        }

        System.out.println("Gender set as: " + (gender.equals("M") ? "Male" : "Female"));
        System.out.print("Enter contact: ");
        String contact = sc.nextLine().trim();

        Patient patient = new Patient(name, age, gender, contact);
        patient.admit();

        // Ask whether patient wants a doctor
        boolean wantDoctor = false;
        while (true) {
            System.out.print("\nDo you need a doctor? (yes/no): ");
            String ans = sc.nextLine().trim().toLowerCase();
            if (ans.equals("yes")) { wantDoctor = true; break; }
            else if (ans.equals("no")) { wantDoctor = false; break; }
            else System.out.println("Please answer 'yes' or 'no'.");
        }

        Billing bill = new Billing(patient.getId());

        if (wantDoctor) {
            // Show disease list (1..15)
            System.out.println("\n--- Disease / Reason for visit ---");
            for (Map.Entry<Integer, DiseaseInfo> e : diseaseMap.entrySet()) {
                System.out.println(e.getKey() + ". " + e.getValue().diseaseName);
            }
            int diseaseChoice;
            while (true) {
                System.out.print("Choose disease by number (or 0 for General checkup): ");
                String in = sc.nextLine().trim();
                try {
                    diseaseChoice = Integer.parseInt(in);
                    if (diseaseChoice == 0) { diseaseChoice = 1; break; } // treat 0 as general
                    if (diseaseChoice >= 1 && diseaseChoice <= diseaseMap.size()) break;
                } catch (NumberFormatException ignored) {}
                System.out.println("Invalid choice. Enter a number from the list.");
            }

            DiseaseInfo chosen = diseaseMap.get(diseaseChoice);
            System.out.println("\nYou selected: " + chosen.diseaseName);

            // Assign doctor automatically
            patient.doctor = doctors.get(chosen.doctorIndex);
            System.out.println("Assigned Doctor: " + patient.doctor.getName() + " (" + patient.doctor.getSpecialty() + ")");
            patient.doctor.provideService(patient);

            // Assign and show nurses
            patient.nurses.clear();
            for (int ni : chosen.nurseIndices) {
                if (ni >= 0 && ni < nurses.size()) patient.nurses.add(nurses.get(ni));
            }

            System.out.print("Assigned Nurses: ");
            for (Nurse nr : patient.nurses) System.out.print(nr.getName() + " (" + nr.getWard() + ")  ");
            System.out.println();
            for (Nurse nr : patient.nurses) nr.provideService(patient);

            System.out.println("\nWard / Area: " + chosen.ward);

            // show recommended meds for disease
            if (!chosen.recommendedMedsAndDose.isEmpty()) {
                System.out.println("\nRecommended medicines for " + chosen.diseaseName + ":");
                int idx = 1;
                for (Map.Entry<String, String> medDose : chosen.recommendedMedsAndDose.entrySet()) {
                    String medName = medDose.getKey();
                    String dose = medDose.getValue();
                    double price = pharmacy.getPrice(medName);
                    System.out.printf("%d. %s — %s — ₱%.2f\n", idx, medName, dose, price);
                    idx++;
                }

                // ask whether to add recommended meds to bill
                while (true) {
                    System.out.print("\nAdd recommended medicines to bill? (yes/no): ");
                    String in = sc.nextLine().trim().toLowerCase();
                    if (in.equals("yes")) {
                        for (Map.Entry<String, String> medDose : chosen.recommendedMedsAndDose.entrySet()) {
                            String medName = medDose.getKey();
                            double price = pharmacy.getPrice(medName);
                            if (price <= 0) {
                                System.out.println("Price not available for " + medName + ". Skipping.");
                                continue;
                            }
                            int qty;
                            while (true) {
                                System.out.print("Enter quantity for \"" + medName + "\": ");
                                String qIn = sc.nextLine().trim();
                                try {
                                    qty = Integer.parseInt(qIn);
                                    if (qty <= 0) { System.out.println("Quantity must be at least 1."); continue; }
                                    break;
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid number. Try again.");
                                }
                            }
                            bill.addMedicine(medName + " (" + medDose.getValue() + ")", qty, price);
                            System.out.println(qty + " x " + medName + " added to bill.");
                        }
                        break;
                    } else if (in.equals("no")) {
                        System.out.println("Recommended medicines not added.");
                        break;
                    } else {
                        System.out.println("Please answer 'yes' or 'no'.");
                    }
                }
            }

            // add standard consultation service automatically
            bill.addService("Doctor Consultation (" + patient.doctor.getSpecialty() + ")", 500.0);
        } else {
            // No doctor: optionally go to pharmacy first
            while (true) {
                System.out.print("\nDo you want to buy medicines? (yes/no): ");
                String in = sc.nextLine().trim().toLowerCase();
                if (in.equals("yes")) {
                    pharmacy.provideService(patient); // reflect flowchart's pharmacy path
                    break;
                } else if (in.equals("no")) {
                    System.out.println("Skipping medicines and proceeding to services.");
                    break;
                } else {
                    System.out.println("Please answer 'yes' or 'no'.");
                }
            }
        }

        // --- SERVICES MENU (optional, per flowchart) ---
        System.out.println("\n--- Services (optional) ---");
        System.out.println("1. Consultation - ₱500");
        System.out.println("2. X-Ray - ₱800");
        System.out.println("3. Blood Test - ₱300");
        System.out.println("4. ECG - ₱700");
        System.out.println("5. Ultrasound - ₱900");
        String[] serviceChoices;
        while (true) {
            System.out.print("Choose services (comma separated numbers, or press Enter to skip): ");
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                serviceChoices = new String[0];
                break;
            }
            serviceChoices = input.split(",");
            boolean valid = true;
            for (String s : serviceChoices) {
                s = s.trim();
                if (!s.matches("\\d+")) { valid = false; break; }
                int num = Integer.parseInt(s);
                if (num < 1 || num > 5) { valid = false; break; }
            }
            if (valid) break;
            System.out.println("Invalid input. Please enter numbers between 1 and 5 only.\n");
        }
        for (String s : serviceChoices) {
            switch (s.trim()) {
                case "1": bill.addService("Consultation", 500); break;
                case "2": bill.addService("X-Ray", 800); break;
                case "3": bill.addService("Blood Test", 300); break;
                case "4": bill.addService("ECG", 700); break;
                case "5": bill.addService("Ultrasound", 900); break;
            }
        }

        // --- PHARMACY (manual purchase or additional meds) ---
        boolean moreMeds = true;
        while (moreMeds) {
            System.out.println("\nPHARMACY - available medicines (alphabetical within categories):");
            pharmacy.showMedicinesAlphabetical();
            int choice;
            while (true) {
                System.out.print("Select medicine by global number (or 0 to skip pharmacy): ");
                String in = sc.nextLine().trim();
                try {
                    choice = Integer.parseInt(in);
                    if (choice == 0) break;
                    if (pharmacy.isValidChoice(choice)) break;
                } catch (NumberFormatException ignored) {}
                System.out.println("Invalid input. Enter a number from the list or 0 to skip.");
            }
            if (choice == 0) break;
            String med = pharmacy.getMedicineByIndex(choice);
            double price = pharmacy.getPrice(med);
            if (price <= 0) {
                System.out.println("Price not available for this item. Cannot add to bill.");
            } else {
                int qty;
                while (true) {
                    System.out.print("Enter quantity for \"" + med + "\": ");
                    String qIn = sc.nextLine().trim();
                    try {
                        qty = Integer.parseInt(qIn);
                        if (qty <= 0) { System.out.println("Quantity must be at least 1."); continue; }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number. Try again.");
                    }
                }
                bill.addMedicine(med, qty, price);
                System.out.println(qty + " x " + med + " added to bill.");
            }

            while (true) {
                System.out.print("Add more medicines? (yes/no): ");
                String in = sc.nextLine().trim().toLowerCase();
                if (in.equals("yes")) { moreMeds = true; break; }
                if (in.equals("no")) { moreMeds = false; break; }
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }

        // --- BILLING & PAYMENT ---
        bill.generateBill();
        double payment;
        while (true) {
            System.out.print("Enter payment amount: ₱");
            String pIn = sc.nextLine().trim();
            try {
                payment = Double.parseDouble(pIn);
                if (payment < 0) { System.out.println("Payment cannot be negative."); continue; }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Enter a number.");
            }
        }

        double total = bill.totalAmount;

        if (payment < total) {
            boolean canAffordOne = false;
            for (double price : bill.medicines.values()) {
                if (price <= payment) { canAffordOne = true; break; }
            }
            if (!canAffordOne) {
                System.out.println("\nYou cannot afford any of the medicines.");
                System.out.println("The hospital will give you a prescription instead.");
                patient.discharge();
                sc.close();
                return;
            }
            while (true) {
                System.out.print("\nYou don't have enough money. Pay only for what you can afford now? (yes/no): ");
                String c = sc.nextLine().trim().toLowerCase();
                if (c.equals("yes")) break;
                if (c.equals("no")) {
                    System.out.println("\nThe hospital will give you a prescription instead.");
                    patient.discharge();
                    sc.close();
                    return;
                }
                System.out.println("Invalid input. Please enter yes or no.");
            }
            List<String> toRemove = new ArrayList<>();
            for (Map.Entry<String, Double> entry : bill.medicines.entrySet()) {
                if (entry.getValue() > payment) toRemove.add(entry.getKey());
            }
            for (String med : toRemove) {
                System.out.println("Removing " + med + " from bill (cannot afford).");
                bill.totalAmount -= bill.medicines.get(med);
                bill.medicines.remove(med);
            }
            System.out.println("\nUpdated bill based on your available money:");
            bill.generateBill();
        }

        bill.applyPayment(payment);

        // --- DISCHARGE ---
        patient.discharge();
        sc.close();
    }
}
