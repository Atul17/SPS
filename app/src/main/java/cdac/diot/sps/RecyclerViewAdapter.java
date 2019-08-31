package cdac.diot.sps;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<ParkingSlotDetails> slotDataList;


    public RecyclerViewAdapter(Context mContext, List<ParkingSlotDetails> slotDataList) {
        this.mContext = mContext;
        this.slotDataList = slotDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        view = mLayoutInflater.inflate(R.layout.row_slot_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_slot_no.setText(slotDataList.get(position).getpl_name());
        long b = slotDataList.get(position).getBook_status();
        if(b==1){
            holder.cardView.setCardBackgroundColor(Color.RED);
        }else {
            holder.cardView.setCardBackgroundColor(Color.GREEN);

        }

    }

    @Override
    public int getItemCount() {
        return slotDataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_slot_no;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_slot_no = itemView.findViewById(R.id.txt_slot_no);
            cardView = itemView.findViewById(R.id.crvMain);
        }
    }
}
