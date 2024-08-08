package karisimbi.com.lifetool.models;

public class Patient extends User {

    private String dateOfBirth;
    private boolean hivPositive;
    private String diagnosisDate;
    private boolean onART;
    private String artStartDate;
    private String countryCode;
    private String uuidCode;

    public Patient(String firstName, String lastName, String email, String hPassword, String role, String dateOfBirth,
                   boolean hivPositive, String diagnosisDate, boolean onART, String artStartDate, String countryCode, String uuidCode) {
        super(firstName, lastName, email, hPassword, role);
        this.dateOfBirth = dateOfBirth;
        this.hivPositive = hivPositive;
        this.diagnosisDate = diagnosisDate;
        this.onART = onART;
        this.artStartDate = artStartDate;
        this.countryCode = countryCode;
        this.uuidCode = uuidCode;
    }

    @Override
    public void viewProfile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewProfile'");
    }

    @Override
    public void updateProfile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProfile'");
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isHivPositive() {
        return hivPositive;
    }

    public void setHivPositive(boolean hivPositive) {
        this.hivPositive = hivPositive;
    }

    public String getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(String diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public boolean isOnART() {
        return onART;
    }

    public void setOnART(boolean onART) {
        this.onART = onART;
    }

    public String getArtStartDate() {
        return artStartDate;
    }

    public void setArtStartDate(String artStartDate) {
        this.artStartDate = artStartDate;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getUuidCode() {
        return uuidCode;
    }

    public void setUuidCode(String uuidCode) {
        this.uuidCode = uuidCode;
    }
}
