package com.ob.myntra.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ob.myntra.R;
import com.ob.myntra.ui.vo.Category;

import java.util.List;

/***
 * Created by Hemant4524 on 24/Jan/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private static final String TAG = CategoryAdapter.class.getSimpleName();
    private Context context;
    private List categoryLists;

    public CategoryAdapter(Context context, List categoryLists) {
            this.context = context;
            this.categoryLists = categoryLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Category category = (Category) categoryLists.get(position);

       // Uri uri = Uri.parse(category.logoUrl);
        //holder.imageView.setImageURI(uri);
        holder.name.setText(category.categoryName);

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+categoryLists.size());
        return categoryLists.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private SimpleDraweeView imageView;
        private TextView name;
        public MyViewHolder(View view) {
            super(view);
            imageView = (SimpleDraweeView) view.findViewById(R.id.cil_ivLogo);
            name = (TextView) view.findViewById(R.id.cil_tvName);

        }
    }
}
