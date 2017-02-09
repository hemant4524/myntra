package com.ob.myntra.ui.adapter;


import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ob.myntra.R;
import com.ob.myntra.ui.vo.HorizontalListVo;

import java.util.ArrayList;
import java.util.List;

/***
 * Created by Hemant4524 on 06/Jan/2017.
 */

public class TopHorizontalScrollAdapter extends RecyclerView.Adapter<TopHorizontalScrollAdapter.ItemViewHolder> {

    private Context context;
    private List<HorizontalListVo> lists = new ArrayList<>();

    public TopHorizontalScrollAdapter(Context context) {
        this.context = context;
        setListValues();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_horizontal_scroll_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ItemViewHolder itemViewHolder= new ItemViewHolder(view);

        return  itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
                    holder.title.setText(lists.get(position).getName());
                    holder.image.setBackground(lists.get(position).getDrawable());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private SimpleDraweeView image;
        public ItemViewHolder(View itemView) {
            super(itemView);
           title = (TextView) itemView.findViewById(R.id.tvsi_tvTitleName);
           image = (SimpleDraweeView) itemView.findViewById(R.id.tvsi_ivHeader);
        }
    }

    private void setListValues() {

        lists.add(new HorizontalListVo("Men", ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_men, null)));
        lists.add(new HorizontalListVo("Women", ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_women, null)));
        lists.add(new HorizontalListVo("Kids", ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_kids, null)));
        lists.add(new HorizontalListVo("Home", ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_home, null)));
        lists.add(new HorizontalListVo("Kids", ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_kids, null)));
        lists.add(new HorizontalListVo("Men", ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_men, null)));
        lists.add(new HorizontalListVo("Kids", ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_kids, null)));
        lists.add(new HorizontalListVo("Kids", ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_kids, null)));
        lists.add(new HorizontalListVo("Men", ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_men, null)));
        lists.add(new HorizontalListVo("Kids", ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_kids, null)));

    }

}