package com.ob.myntra.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ob.myntra.R;
import com.ob.myntra.ui.vo.DrawerItem;

import java.util.ArrayList;


public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {
    private ArrayList<DrawerItem> mDrawerMenuList;
    private OnItemSelecteListener mOnItemSelecteListener;
    public DrawerAdapter(ArrayList<DrawerItem> drawerMenuList) {
        this.mDrawerMenuList = drawerMenuList;
    }

    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_main_menu_item, parent, false);
        return new DrawerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DrawerViewHolder holder, int position) {
        holder.title.setText(mDrawerMenuList.get(position).getTitle());
        //holder.icon.setImageResource(mDrawerMenuList.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return mDrawerMenuList.size();
    }

    class DrawerViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;

        public DrawerViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.dmmi_tvName);
            icon = (ImageView) itemView.findViewById(R.id.dmmi_ivIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemSelecteListener.onItemSelected(view,getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickLister(OnItemSelecteListener mListener) {
        this.mOnItemSelecteListener = mListener;
    }

    public interface OnItemSelecteListener{
        public void onItemSelected(View v, int position);
    }
}