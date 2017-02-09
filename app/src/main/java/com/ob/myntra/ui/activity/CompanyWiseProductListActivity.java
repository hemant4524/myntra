package com.ob.myntra.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ob.myntra.R;
import com.ob.myntra.ui.adapter.CategoryAdapter;
import com.ob.myntra.ui.vo.Category;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class CompanyWiseProductListActivity extends BaseActivity {

    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";
    private ArrayList<Category> mAdapterItems;
    private ArrayList<String> mAdapterKeys;

    private static final String TAG = CompanyWiseProductListActivity.class.getSimpleName();
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mDatabase;
    private String categoryId;

    List notes = new ArrayList<>();
    private RecyclerView mrvCategory;
    private Query mQuery;
    private CategoryAdapter mMyAdapter;
    List categoryLists = new ArrayList<>();
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_wise_product_list);

        mProgressBar = (android.widget.ProgressBar) findViewById(R.id.loading_spinner);
     //   handleInstanceState(savedInstanceState);

        setupFirebase();

        /*createCategory("Jeans","http://pngimg.com/upload/jeans_PNG5746.png");
        createCategory("T-Shirt","http://icomix.eu/gr/images/superman-logo-h-gold.png");
        createCategory("Belt","http://pngimg.com/upload/jeans_PNG5746.png");*/


        getData();




    }


    // Restoring the item list and the keys of the items: they will be passed to the adapter
    private void handleInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null &&
                savedInstanceState.containsKey(SAVED_ADAPTER_ITEMS) &&
                savedInstanceState.containsKey(SAVED_ADAPTER_KEYS)) {
            mAdapterItems = Parcels.unwrap(savedInstanceState.getParcelable(SAVED_ADAPTER_ITEMS));
            mAdapterKeys = savedInstanceState.getStringArrayList(SAVED_ADAPTER_KEYS);
        } else {
            mAdapterItems = new ArrayList<Category>();
            mAdapterKeys = new ArrayList<String>();
        }
    }



    private void setupFirebase() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference();
        mQuery = mDatabase.child("category");
    }


    /**
     * Creating new user node under 'users'
     */
    private void createCategory(String categoryName, String imageUrl) {


        // Create category list
         // createCategory("Jeans","http://pngimg.com/upload/jeans_PNG5746.png");
         // createCategory("T-Shirt","http://icomix.eu/gr/images/superman-logo-h-gold.png");
         // createCategory("Belt","http://pngimg.com/upload/jeans_PNG5746.png");

        // In real apps this categoryId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(categoryId)) {
            categoryId = mDatabase.push().getKey();
        }

        Category category = new Category(categoryId,categoryName, imageUrl);

        mDatabase.child("category").child(categoryId).setValue(category);
        categoryId = null;


    }

    private void getData() {


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot catDataSnapshot: dataSnapshot.getChildren()){

                    Category category = catDataSnapshot.getValue(Category.class);
                    categoryLists.add(category);

                }
                Log.d(TAG, "getData: "+categoryLists.toString());

                // Progress bar visibility gone
                mProgressBar.setVisibility(View.GONE);

                setupRecyclerview();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    private void setupRecyclerview() {

        mrvCategory = (RecyclerView) findViewById(R.id.acwpl_rvCategory);
        mrvCategory.setVisibility(View.VISIBLE);
        mMyAdapter = new CategoryAdapter(this, categoryLists);

        mrvCategory.setLayoutManager(new LinearLayoutManager(this));
        mrvCategory.setAdapter(mMyAdapter);
    }

}
