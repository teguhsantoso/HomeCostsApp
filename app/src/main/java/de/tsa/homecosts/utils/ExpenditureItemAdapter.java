package de.tsa.homecosts.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import de.tsa.homecosts.R;
import de.tsa.homecosts.entities.Expenditure;

public class ExpenditureItemAdapter extends RecyclerView.Adapter<ExpenditureItemAdapter.CustomViewHolder> {

    public interface OnAdapterInteractionListener {
        void onOrderItemClicked(Expenditure expenditure);
    }

    private static Context                          cTxt;
    private static List<Expenditure>                data;
    private static OnAdapterInteractionListener     mListener;

    public ExpenditureItemAdapter(Context cTxt, List<Expenditure> data) {
        this.cTxt = cTxt;
        this.data = data;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.expenditure_row_item, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, final int i) {
        customViewHolder.textViewProductName.setText(data.get(i).getName());
        customViewHolder.textViewSupplierName.setText(data.get(i).getChargeDate());
        customViewHolder.textViewSumOrders.setText("Sum orders #");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView textViewProductName;
        protected TextView textViewSupplierName;
        protected TextView textViewSumOrders;
        protected ImageButton buttonDeleteItem;

        public CustomViewHolder(View view) {
            super(view);
            this.textViewProductName = view.findViewById(R.id.textViewProductName);
            this.textViewSupplierName = view.findViewById(R.id.textViewSupplierName);
            this.textViewSumOrders = view.findViewById(R.id.textViewSumOrders);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener = (OnAdapterInteractionListener) cTxt;
                    mListener.onOrderItemClicked(data.get(getAdapterPosition()));
                }
            });
        }
    }
}
