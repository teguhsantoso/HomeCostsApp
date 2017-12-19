package de.tsa.homecosts.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import de.tsa.homecosts.R;
import de.tsa.homecosts.entities.CategoryType;
import de.tsa.homecosts.entities.Expenditure;

public class ExpenditureItemAdapter extends RecyclerView.Adapter<ExpenditureItemAdapter.CustomViewHolder> {

    public interface OnAdapterInteractionListener {
        void onExpenditureItemClicked(Expenditure expenditure);
        void onDeleteData(Expenditure expenditure);
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
        customViewHolder.textViewExpenditureName.setText(data.get(i).getChargeDate() + "\n" + data.get(i).getName());
        customViewHolder.textViewCategory.setText((data.get(i).getCategory() == 0) ? CategoryType.INCOME.toString() : CategoryType.OUTCOME.toString());
        customViewHolder.textViewSumPayment.setTextColor((data.get(i).getCategory()==0) ? Color.BLUE : Color.RED);
        customViewHolder.textViewSumPayment.setText(String.valueOf(data.get(i).getAmountPayment()) + " EUR");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView textViewExpenditureName;
        protected TextView textViewCategory;
        protected TextView textViewSumPayment;
        protected ImageButton buttonDeleteItem;

        public CustomViewHolder(View view) {
            super(view);
            this.textViewExpenditureName = view.findViewById(R.id.textViewExpenditureName);
            this.textViewCategory = view.findViewById(R.id.textViewCategory);
            this.textViewSumPayment = view.findViewById(R.id.textViewSum);
            this.buttonDeleteItem = view.findViewById(R.id.imageButtonDeleteItem);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Do nothing.
                }
            });
            this.buttonDeleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener = (OnAdapterInteractionListener) cTxt;
                    mListener.onDeleteData(data.get(getAdapterPosition()));
                }
            });
        }
    }
}
