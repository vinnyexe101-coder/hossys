import java.util.LinkedHashMap;

public class DiseaseInfo {
    String diseaseName;
    int doctorIndex;
    int[] nurseIndices;
    String ward;
    LinkedHashMap<String, String> recommendedMedsAndDose;

    public DiseaseInfo(String diseaseName, int doctorIndex, int[] nurseIndices, String ward) {
        this.diseaseName = diseaseName;
        this.doctorIndex = doctorIndex;
        this.nurseIndices = nurseIndices;
        this.ward = ward;
        this.recommendedMedsAndDose = new LinkedHashMap<>();
    }

    public void addRecommendedMed(String med, String dose) {
        recommendedMedsAndDose.put(med, dose);
    }
}
