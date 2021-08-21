package Core;

public class Patient {
    private int user_id;
    private String email;
    private String password;


    public Patient(int user_id, String email, String password)
    {
        this.user_id = user_id;
        this.email = email;
        this.password = password;

    }

    public Patient(Patient patient)
    {
        this.user_id = patient.user_id;
        this.email = patient.email;
        this.password = patient.password;
    }

    public boolean verifyPatientLogin(String email, String password)
    {
        if(this.email == email && this.password.equals(password))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public int getUser_id()
    {
        return user_id;
    }

    public void setUser_id(int user_id)
    {
        this.user_id = user_id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "Patient{" +
                "user_id=" + user_id +
                ", dateOfBirth='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}