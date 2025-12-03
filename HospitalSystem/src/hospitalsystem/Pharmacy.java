import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class Pharmacy implements ServiceProvider {
    // category -> (medName -> price)
    private LinkedHashMap<String, LinkedHashMap<String, Double>> catalogByCategory;

    public Pharmacy() {
        catalogByCategory = new LinkedHashMap<>();

        // === Pain, Fever, Body Care ===
        LinkedHashMap<String, Double> pain = new LinkedHashMap<>();
        pain.put("Biogesic – Paracetamol 500 mg", 5.0);
        pain.put("Medicol Advance – Ibuprofen 200/400 mg", 8.0);
        pain.put("Ponstan – Mefenamic Acid 500 mg", 10.0);
        pain.put("Cataflam – Diclofenac Potassium 50 mg", 12.0);
        pain.put("Salonpas Patch – Menthol 3.1% + Methyl Salicylate 10%", 7.0);
        pain.put("Omega Pain Killer – Methyl Salicylate 25% + Menthol 20%", 15.0);
        pain.put("Efficascent Oil – Mentholated Oil 11%", 20.0);
        pain.put("Tiger Balm – Camphor 11% + Menthol 10%", 25.0);
        pain.put("Kinesiology Tape", 50.0);
        pain.put("Hot/Cold Gel Pack", 80.0);
        catalogByCategory.put("Pain / Fever / Body Care", sortMapByKey(pain));

        // === Cough, Cold, Allergy ===
        LinkedHashMap<String, Double> cough = new LinkedHashMap<>();
        cough.put("Ambroxol – 30 mg", 10.0);
        cough.put("Bioflu – Paracetamol 500 mg + Phenylephrine 10 mg + Chlorphenamine 2 mg", 8.0);
        cough.put("Cetirizine – 10 mg", 6.0);
        cough.put("Decolgen – Paracetamol 500 mg + Phenylephrine 10 mg", 7.0);
        cough.put("Diphenhydramine – 25 mg", 5.0);
        cough.put("Loratadine – 10 mg", 6.0);
        cough.put("Neozep – Paracetamol 500 mg + Phenylephrine 10 mg + Chlorphenamine 2 mg", 9.0);
        cough.put("Salbutamol – 2 mg", 8.0);
        cough.put("Solmux – Carbocisteine 500 mg", 12.0);
        catalogByCategory.put("Cough / Cold / Allergy", sortMapByKey(cough));

        // === Stomach / GI ===
        LinkedHashMap<String, Double> stomach = new LinkedHashMap<>();
        stomach.put("Buscopan – Hyoscine-N-Butylbromide 10 mg", 10.0);
        stomach.put("Gaviscon – Sodium Alginate", 18.0);
        stomach.put("Kremil-S – Antacid", 7.0);
        stomach.put("Loperamide – 2 mg", 5.0);
        stomach.put("Omeprazole – 20 mg", 12.0);
        stomach.put("ORS / Hydrite", 15.0);
        stomach.put("Pantoprazole – 40 mg", 12.0);
        stomach.put("Ranitidine – 150 mg", 5.0);
        stomach.put("Simethicone – 80 mg", 5.0);
        catalogByCategory.put("Stomach / Gastrointestinal", sortMapByKey(stomach));

        // === Infection / Antibiotics ===
        LinkedHashMap<String, Double> antibiotics = new LinkedHashMap<>();
        antibiotics.put("Amoxicillin – 500 mg", 8.0);
        antibiotics.put("Azithromycin – 500 mg", 25.0);
        antibiotics.put("Cefalexin – 500 mg", 12.0);
        antibiotics.put("Clindamycin – 300 mg", 15.0);
        antibiotics.put("Clotrimazole – 1% cream", 40.0);
        antibiotics.put("Co-Amoxiclav – 500 mg + 125 mg", 20.0);
        antibiotics.put("Metronidazole – 500 mg", 8.0);
        antibiotics.put("Mupirocin – 2% ointment", 150.0);
        antibiotics.put("Nitrofurantoin – 100 mg", 12.0);
        catalogByCategory.put("Infection / Antibiotics", sortMapByKey(antibiotics));

        // === Chronic Conditions ===
        LinkedHashMap<String, Double> chronic = new LinkedHashMap<>();
        chronic.put("Amlodipine – 5 / 10 mg", 5.0);
        chronic.put("Aspirin Low Dose – 80 mg", 3.0);
        chronic.put("Atorvastatin – 10 / 20 mg", 10.0);
        chronic.put("Captopril – 25 mg", 3.0);
        chronic.put("Glimepiride – 2 mg", 6.0);
        chronic.put("Gliclazide – 80 mg", 6.0);
        chronic.put("Losartan – 50 mg", 5.0);
        chronic.put("Metformin – 500 / 850 mg", 6.0);
        chronic.put("Simvastatin – 20 mg", 5.0);
        catalogByCategory.put("Chronic Conditions", sortMapByKey(chronic));

        // === IV Fluids ===
        LinkedHashMap<String, Double> iv = new LinkedHashMap<>();
        iv.put("D5 LR 1 L", 120.0);
        iv.put("D5 NSS 1 L", 100.0);
        iv.put("Dextrose 5% in Water (D5W) 1 L", 80.0);
        iv.put("Lactated Ringer’s (LR) 1 L", 80.0);
        iv.put("Normal Saline (0.9% NSS) 1 L", 60.0);
        iv.put("Sterile Water for Injection 50 mL", 30.0);
        catalogByCategory.put("IV Fluids", sortMapByKey(iv));
    }

    private LinkedHashMap<String, Double> sortMapByKey(LinkedHashMap<String, Double> m) {
        List<String> keys = new ArrayList<>(m.keySet());
        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
        LinkedHashMap<String, Double> sorted = new LinkedHashMap<>();
        for (String k : keys) sorted.put(k, m.get(k));
        return sorted;
    }

    public void showMedicinesAlphabetical() {
        int count = 1;
        for (String category : catalogByCategory.keySet()) {
            System.out.println("\n=== " + category.toUpperCase() + " ===");
            LinkedHashMap<String, Double> meds = catalogByCategory.get(category);
            for (String med : meds.keySet()) {
                System.out.println(count + ". " + med + " - ₱" + meds.get(med));
                count++;
            }
        }
    }

    public String getMedicineByIndex(int index) {
        int count = 1;
        for (LinkedHashMap<String, Double> meds : catalogByCategory.values()) {
            for (String med : meds.keySet()) {
                if (count == index) return med;
                count++;
            }
        }
        return null;
    }

    public double getPrice(String med) {
        for (LinkedHashMap<String, Double> meds : catalogByCategory.values()) {
            if (meds.containsKey(med)) return meds.get(med);
        }
        return 0;
    }

    public boolean isValidChoice(int choice) {
        int total = 0;
        for (LinkedHashMap<String, Double> meds : catalogByCategory.values()) total += meds.size();
        return choice >= 1 && choice <= total;
    }

    public int totalMedicinesCount() {
        int total = 0;
        for (LinkedHashMap<String, Double> meds : catalogByCategory.values()) total += meds.size();
        return total;
    }

    @Override
    public void provideService(Patient patient) {
        // Pharmacy provides service by facilitating medicine purchasing
        System.out.println("Pharmacy assisting patient " + patient.name + " with medicine selection.");
    }
}
