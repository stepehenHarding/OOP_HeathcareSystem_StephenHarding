package Core;

public class Centre {

    private int centre_id;
    private String location;

   public Centre (Centre centre){
       this.centre_id= centre.centre_id;
       this.location = centre.location;
   }

    public Centre(int centre_id,String location){
        this.centre_id=centre_id;
        this.location=location;
    }

    public int getCentre_id() {
        return centre_id;
    }

    public void setCentre_id(int centre_id) {
        this.centre_id = centre_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Centre{" +
                "centre_id=" + centre_id +
                ", location='" + location + '\'' +
                '}';
    }
}
