package cdac.diot.sps;

public class ParkingSlotDetails {
    public long getBook_status() {
        return book_status;
    }

    public void setBook_status(long book_status) {
        this.book_status = book_status;
    }

    public long getSlot_status() {
        return slot_status;
    }

    public void setSlot_status(long slot_status) {
        this.slot_status = slot_status;
    }

    public long book_status, slot_status;

    public String getpl_name() {
        return pl_name;
    }

    public void setpl_name(String pl_name) {
        this.pl_name = pl_name;
    }

    public String pl_name;

    public ParkingSlotDetails() {

    }

    public ParkingSlotDetails(long book_status, long slot_status, String pl_name) {
        this.book_status = book_status;
        this.slot_status = slot_status;
        this.pl_name = pl_name;

    }
}
