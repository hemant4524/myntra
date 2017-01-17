package com.ob.myntra.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ob.myntra.R;
import com.ob.myntra.ui.adapter.DrawerAdapter;
import com.ob.myntra.ui.vo.DrawerItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainMenuFragment extends Fragment {


    public MainMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_menu_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<DrawerItem> drawerItemList = createMenu();


        DrawerAdapter adapter = new DrawerAdapter(drawerItemList);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dmmi_rvMainMenu);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickLister(new DrawerAdapter.OnItemSelecteListener() {
            @Override
            public void onItemSelected(View v, int position) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ChildMainMenuFragment childMainMenuFragment = new ChildMainMenuFragment();
                fragmentTransaction.add(R.id.ah_flDrawerContainer, childMainMenuFragment);
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
                fragmentTransaction.show(childMainMenuFragment);
                fragmentTransaction.commit();

            }
        });
    }

    private ArrayList<DrawerItem> createMenu() {

        ArrayList<DrawerItem> drawerItemList = new ArrayList<DrawerItem>();
        // Item #1
        DrawerItem item1 = new DrawerItem();
        item1.setIcon(android.R.drawable.ic_dialog_email);
        item1.setTitle("Women");
        drawerItemList.add(item1);

        // Item #2
        DrawerItem item2 = new DrawerItem();
        item2.setIcon(android.R.drawable.ic_dialog_email);
        item2.setTitle("Men");
        drawerItemList.add(item2);

        // Item #3
        DrawerItem item3 = new DrawerItem();
        item3.setIcon(android.R.drawable.ic_dialog_email);
        item3.setTitle("Kids");
        drawerItemList.add(item3);

        // Item #4
        DrawerItem item4 = new DrawerItem();
        item4.setIcon(android.R.drawable.ic_dialog_email);
        item4.setTitle("Home & Living");
        drawerItemList.add(item4);


        return drawerItemList;
    }
}
