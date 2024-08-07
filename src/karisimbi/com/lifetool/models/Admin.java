package karisimbi.com.lifetool.models;

public class Admin extends User {

    public Admin(String firstName, String lastName, String email, String hPassword, String role) {
        super(firstName, lastName, email, hPassword, role);
        //TODO Auto-generated constructor stub
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
