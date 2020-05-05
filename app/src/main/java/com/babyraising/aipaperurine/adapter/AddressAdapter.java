package com.babyraising.aipaperurine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.bean.AddressBean;
import com.babyraising.aipaperurine.bean.GrowthPointBean;
import com.babyraising.aipaperurine.ui.address.AddressEditActivity;
import com.babyraising.aipaperurine.ui.address.AddressManagerActivity;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private List<AddressBean> mList;
    private AddressManagerActivity context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView singleTxt, nameTxt, phoneTxt, addressTxt;
        ImageView icDelete;
        CheckBox cbDefault;
        LinearLayout cbLayout;


        public ViewHolder(View view) {
            super(view);
            singleTxt = (TextView) view.findViewById(R.id.single);
            nameTxt = (TextView) view.findViewById(R.id.name);
            phoneTxt = (TextView) view.findViewById(R.id.phone);
            addressTxt = (TextView) view.findViewById(R.id.address);
            icDelete = (ImageView) view.findViewById(R.id.cb_delete);
            cbDefault = (CheckBox) view.findViewById(R.id.cb_default);
            cbLayout = (LinearLayout) view.findViewById(R.id.layout_cb);
        }

    }

    public AddressAdapter(AddressManagerActivity context, List<AddressBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_address, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        switch (mList.get(position).getISDEFAULT()) {
            case "1":
                holder.cbDefault.setChecked(false);
                break;
            case "2":
                holder.cbDefault.setChecked(true);
                break;
        }

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

//        holder.cbDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    context.setDefault(mList.get(position).getADDRESS_ID());
//                } else {
////                    holder.cbDefault.setChecked(true);
//                }
//            }
//        });

        holder.cbLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mList.get(position).getISDEFAULT().equals("1")){
                    context.setDefault(mList.get(position).getADDRESS_ID());
                }
            }
        });

        holder.icDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.deleteAddressId(mList.get(position).getADDRESS_ID());
            }
        });
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
