package com.ob.myntra.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ob.myntra.R;
import com.ob.myntra.ui.activity.CompanyWiseProductListActivity;
import com.ob.myntra.ui.adapter.TopHorizontalScrollAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {



    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        SimpleDraweeView sdBannerImage = (SimpleDraweeView) view.findViewById(R.id.my_image_view1);
        sdBannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), CompanyWiseProductListActivity.class));

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView mrvTopHorizontalScroll = (RecyclerView) view.findViewById(R.id.thsl_recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        mrvTopHorizontalScroll.setLayoutManager(layoutManager);
        TopHorizontalScrollAdapter topHorizontalScrollAdapter = new TopHorizontalScrollAdapter(getActivity());
        mrvTopHorizontalScroll.setAdapter(topHorizontalScrollAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}
