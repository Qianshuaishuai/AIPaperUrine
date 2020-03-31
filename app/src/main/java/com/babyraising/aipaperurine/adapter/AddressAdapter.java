package com.babyraising.aipaperurine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.bean.AddressBean;
import com.babyraising.aipaperurine.bean.GrowthPointBean;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private List<AddressBean> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView singleTxt, nameTxt, phoneTxt, addressTxt;

        public ViewHolder(View view) {
            super(view);
            singleTxt = (TextView) view.findViewById(R.id.single);
            nameTxt = (TextView) view.findViewById(R.id.name);
            phoneTxt = (TextView) view.findViewById(R.id.phone);
            addressTxt = (TextView) view.findViewById(R.id.address);
        }

    }

    public AddressAdapter(List<AddressBean> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_address, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });

        holder.nameTxt.setText(mList.get(position).getCNAME());
        holder.phoneTxt.setText(mList.get(position).getCPHONE());
        holder.addressTxt.setText(mList.get(position).getADDRESS());
        if (mList.get(position).getADDRESS().length() > 0) {
            holder.singleTxt.setText(mList.get(position).getADDRESS().substring(0, 1));
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
