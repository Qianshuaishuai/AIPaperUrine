package com.xinxin.aicare.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xinxin.aicare.R;
import com.xinxin.aicare.ui.order.RefundActivity;

import org.xutils.x;

import java.util.List;

public class RefundPhotoAdapter extends RecyclerView.Adapter<RefundPhotoAdapter.ViewHolder> {

    private List<String> mList;
    private RefundActivity context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView add, icon, delete;

        public ViewHolder(View view) {
            super(view);
            add = (ImageView) view.findViewById(R.id.add);
            icon = (ImageView) view.findViewById(R.id.icon);
            delete = (ImageView) view.findViewById(R.id.delete);
        }

    }

    public RefundPhotoAdapter(List<String> mList, RefundActivity context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_refund_photo, parent, false);
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

        if (position == 0) {
            holder.add.setVisibility(View.VISIBLE);
            holder.icon.setVisibility(View.GONE);
            holder.delete.setVisibility(View.GONE);
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.addPhoto();
                }
            });
        } else {
            holder.add.setVisibility(View.GONE);
            holder.icon.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.VISIBLE);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.deletePhoto(position);
                }
            });

            x.image().bind(holder.icon, mList.get(position));
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
