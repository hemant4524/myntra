package com.ob.myntra.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ob.myntra.R;
import com.ob.myntra.ui.lib.ExpandableRecyclerAdapterBackUp;

import java.util.ArrayList;
import java.util.List;

/***
 * Created by Hemant4524 on 06/Jan/2017.
 */

public class ChildMainAdapter extends ExpandableRecyclerAdapterBackUp<ChildMainAdapter.ChildListItem> {

    public static final int TYPE_MENU = 1001;

    public ChildMainAdapter(Context context) {
        super(context);

        setItems(getSampleItems());
    }

    public static class ChildListItem extends ExpandableRecyclerAdapterBackUp.ListItem {
        public String Text;

        public ChildListItem(String group) {
            super(TYPE_HEADER);

            Text = group;
        }

        public ChildListItem(String first, String last) {
            super(TYPE_MENU);

            Text = first + " " + last;
        }
    }

    public class HeaderViewHolder extends ExpandableRecyclerAdapterBackUp.HeaderViewHolder {
        TextView name;

        public HeaderViewHolder(View view) {
            super(view, (ImageView) view.findViewById(R.id.item_arrow));

            name = (TextView) view.findViewById(R.id.item_header_name);
        }

        public void bind(int position) {
            super.bind(position);

            name.setText(visibleItems.get(position).Text);
        }
    }

    public class ItemViewHolder extends ExpandableRecyclerAdapterBackUp.ViewHolder {
        TextView name;

        public ItemViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.item_name);
        }

        public void bind(int position) {
            name.setText(visibleItems.get(position).Text);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderViewHolder(inflate(R.layout.item_header, parent));
            case TYPE_MENU:
            default:
                return new ItemViewHolder(inflate(R.layout.item_sub_menu, parent));
        }
    }

    @Override
    public void onBindViewHolder(ExpandableRecyclerAdapterBackUp.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ((HeaderViewHolder) holder).bind(position);
                break;
            case TYPE_MENU:
            default:
                ((ItemViewHolder) holder).bind(position);
                break;
        }
    }

    private List<ChildListItem> getSampleItems() {
        List<ChildListItem> items = new ArrayList<>();

        items.add(new ChildListItem("NEW ARRIVALS"));
        items.add(new ChildListItem("Clothing", ""));
        items.add(new ChildListItem("Footwear", ""));
        items.add(new ChildListItem("Accessories", ""));
        items.add(new ChildListItem("Indian & Fusion Wear"));
        items.add(new ChildListItem("Kurtas & Suits", ""));
        items.add(new ChildListItem("Kurtis, Tunics & Tops", ""));
        items.add(new ChildListItem("Leggings, Salwars, Churidars", ""));
        items.add(new ChildListItem("Skits & Plazzos", ""));
        items.add(new ChildListItem("Sarees & Blouses", ""));
        items.add(new ChildListItem("Dress Material", ""));
        items.add(new ChildListItem("Lehenga Choli", ""));
        items.add(new ChildListItem("Dupatts & Shawls", ""));
        items.add(new ChildListItem("Jackets & Waistcoats", ""));
        items.add(new ChildListItem("Western Wear"));
        items.add(new ChildListItem("Dresses & Jumpsuits", ""));
        items.add(new ChildListItem("Top, T-Shirts & Shirts", ""));
        items.add(new ChildListItem("Jeans & Jeggings", ""));
        items.add(new ChildListItem("Trousers & Capris", ""));
        items.add(new ChildListItem("Shorts & Skirts", ""));
        items.add(new ChildListItem("Shrugs", ""));
        items.add(new ChildListItem("Sweaters & Sweatshirts", ""));
        items.add(new ChildListItem("Jackets & Waistcoats", ""));
        items.add(new ChildListItem("Lingerie & Sleepwear"));
        items.add(new ChildListItem("Footwear"));
        items.add(new ChildListItem("Sports & Active Wear"));
        items.add(new ChildListItem("Handbangs, Bags & Wallets"));
        items.add(new ChildListItem("Watches"));

        return items;
    }
}