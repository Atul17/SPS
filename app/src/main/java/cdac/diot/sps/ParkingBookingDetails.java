package cdac.diot.sps;

public class ParkingBookingDetails {
    public String pl_no;

    public String getPl_no() {
        return pl_no;
    }

    public void setPl_no(String pl_no) {
        this.pl_no = pl_no;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String vehicle_no;
    public String area_name;

    public ParkingBookingDetails() {

    }

    public ParkingBookingDetails(String pl_no, String vehicle_no, String area_name) {
        this.pl_no = pl_no;
        this.vehicle_no = vehicle_no;
        this.area_name = area_name;

    }
}
