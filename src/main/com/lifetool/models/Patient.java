package main.com.lifetool.models;

public class Patient extends User {

    private String dateOfBirth;
    private boolean hivPositive;
    private String diagnosisDate;
    private boolean onART;
    private String artStartDate;
    private String countryCode;
    private String uuidCode;

    public Patient(String firstName, String lastName, String email, String hPassword, String dateOfBirth,
    boolean hivPositive, String diagnosisDate, boolean onART, String artStartDate, String countryCode, String uuidCode){
        super(firstName, lastName, email, hPassword);
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

    
}
