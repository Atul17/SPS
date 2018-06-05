package cdac.diot.sps;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ParkingBookDetailsAdapter extends RecyclerView.Adapter<ParkingBookDetailsAdapter.MyViewHolder> {
    private List<ParkingBookingDetails> bookingDetails;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_row_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ParkingBookingDetails details = bookingDetails.get(position);
        holder.area_name.setText(details.getArea_name());
        holder.pl_no.setText(details.getPl_no());

    }

    @Override
    public int getItemCount() {
        return bookingDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView area_name, pl_no;

        public MyViewHolder(View view) {
            super(view);
            area_name = view.findViewById(R.id.txt_area_name);
            pl_no = view.findViewById(R.id.txt_pl_no);

        }

    }

    public ParkingBookDetailsAdapter(List<ParkingBookingDetails> bookingDetails) {
        this.bookingDetails = bookingDetails;

    }
}
