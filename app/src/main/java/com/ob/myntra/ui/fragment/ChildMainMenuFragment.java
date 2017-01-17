package com.ob.myntra.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ob.myntra.R;
import com.ob.myntra.ui.adapter.ChildMainAdapter;
import com.ob.myntra.ui.lib.ExpandableRecyclerAdapterBackUp;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChildMainMenuFragment extends Fragment {


    public ChildMainMenuFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.child_main_menu_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView ivBack = (ImageView) view.findViewById(R.id.cmhi_ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_right)
                        .remove(ChildMainMenuFragment.this).commit();

            }
        });

        setRecycleView(view);
    }

    private void setRecycleView(View view) {
        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.cmm_recyclerView);
        ChildMainAdapter adapter = new ChildMainAdapter(getActivity());
        adapter.setMode(ExpandableRecyclerAdapterBackUp.MODE_ACCORDION);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
     /*   RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        recycler.addItemDecoration(itemDecoration);*/
        //adapter.setRecyleViewLayoutManager((LinearLayoutManager) recycler.getLayoutManager());

        recycler.setAdapter(adapter);


    }

}
