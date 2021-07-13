package com.netpaisa.aepsriseinlib.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netpaisa.aepsriseinlib.R;
import com.netpaisa.aepsriseinlib.model.MiniStmntAepsModel;

import java.util.List;

public class AepsMiniStmtAdapter extends RecyclerView.Adapter<AepsMiniStmtAdapter.ViewHolder>{

    Context context;
    List<MiniStmntAepsModel.Res.Data.MiniStatement> mAepsMiniStmtList;


    public AepsMiniStmtAdapter(List<MiniStmntAepsModel.Res.Data.MiniStatement> AepsMiniStmtLists, Context context) {
        this.mAepsMiniStmtList = AepsMiniStmtLists;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_aeps_ministmt_layout,viewGroup,false);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        //final List<MiniStmntAepsModel.Data.MiniStatement> AepsMiniStmtList = mAepsMiniStmtList.get(i).getData().getMiniStatement();

        viewHolder.mAepsDateTxt.setText("Transaction Date : " +mAepsMiniStmtList.get(i).getDate());
        viewHolder.mAepsTxnTypeTxt.setText("Transaction Type : " +mAepsMiniStmtList.get(i).getTxnType());
        viewHolder.mAepsAmountTxt.setText("Transaction Amount : " +mAepsMiniStmtList.get(i).getAmount());
        viewHolder.mAepsNarrationTxt.setText("Narration : " +mAepsMiniStmtList.get(i).getNarration());

    }



    @Override
    public int getItemCount() {
        return mAepsMiniStmtList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView mAepsDateTxt;
        private final TextView mAepsTxnTypeTxt;
        private final TextView mAepsAmountTxt;
        private final TextView mAepsNarrationTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mAepsDateTxt = (TextView)itemView.findViewById(R.id.idAepsDateTxt);
            mAepsTxnTypeTxt = (TextView)itemView.findViewById(R.id.idAepsTxnTypeTxt);
            mAepsAmountTxt = (TextView)itemView.findViewById(R.id.idAepsAmountTxt);
            mAepsNarrationTxt = (TextView)itemView.findViewById(R.id.idAepsNarrationTxt);

        }
    }
}
