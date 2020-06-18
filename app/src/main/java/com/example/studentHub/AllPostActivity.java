package com.example.studentHub;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentHub.Login.StartUp;
import com.example.studentHub.Model_helper_classes.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class AllPostActivity extends AppCompatActivity {

    //Firebase

    private DatabaseReference mAllJobPost; //ref
    private FirebaseRecyclerOptions<Data> options;
    private FirebaseRecyclerAdapter<Data, AllViewHolder> adapter;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    EditText inputSearch;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_post);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);   //show back button

        //database
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser mUser = mAuth.getCurrentUser();
        //String uId = mUser.getUid();
        mAllJobPost = FirebaseDatabase.getInstance().getReference()
                .child("Public Database");
        mAllJobPost.keepSynced(true);

        inputSearch = findViewById(R.id.input_Search);
        recyclerView = findViewById(R.id.recycler_alljob);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        //recyclerView.setLayoutManager(layoutManager);
        //layoutManager.setReverseLayout(true);
        //layoutManager.setStackFromEnd(true);

        LoadData("");

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().toLowerCase() != null)
                {
                    LoadData(s.toString().toLowerCase());
                }

                else
                {
                    LoadData("");
                }

            }
        });

    }



    private void LoadData(final String data) {

        Query query = mAllJobPost.orderByChild("title").startAt(data.toLowerCase()).endAt(data.toLowerCase() + "\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(query, Data.class).build();

        adapter = new FirebaseRecyclerAdapter<Data, AllViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AllViewHolder holder, final int position, @NonNull final Data model) {
                holder.mTitle.setText(model.getTitle());
                holder.mDate.setText(model.getDate());
                holder.mDescription.setText(model.getDescription());
                Picasso.get().load(model.getImageUrl()).into(holder.imageView);

                //click listener to view notice
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getApplicationContext(), ViewNotice.class);
                        intent.putExtra("key", getRef(position).getKey());
//                        intent.putExtra("title", data.getTitle());
//                        intent.putExtra("date", model.getDate());
//                        intent.putExtra("description", model.getDescription());
                        startActivity(intent);

                    }
                });


                //getting the time-ago- to work//
                String DateTime = model.getDateTime();

                //  SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd SS HH:mm:ss");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                try {
                    long time = sdf.parse(DateTime).getTime(); //time of uploading the post
                    long now = System.currentTimeMillis();  //time of viewing the post
                    CharSequence ago =
                            DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
                    Log.d("myTime", "onBindViewHolder: "+ago);
                    holder.timeAgo.setText(""+ago);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @NonNull
            @Override
            public AllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.alljobpost, parent, false);
                return new AllViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    private MenuItem searchMenu;
    private String mSearchString = "";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.allpost_menu, menu);
        super.onCreateOptionsMenu(menu);

        SearchManager searchManager = (SearchManager) getApplicationContext().getSystemService(Context.SEARCH_SERVICE);

        SearchView mSearchView = new SearchView(getSupportActionBar().getThemedContext());
        mSearchView.setQueryHint(getString(R.string.prompt_search));
        mSearchView.setMaxWidth(Integer.MAX_VALUE);

        searchMenu = menu.add("searchMenu").setVisible(false).setActionView(mSearchView);
        searchMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

        assert searchManager != null;
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setIconifiedByDefault(false);


        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                mSearchString = newText;

                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                mSearchString = query;

                searchMenu.collapseActionView();


                return true;
            }

        };

        mSearchView.setOnQueryTextListener(queryTextListener);

        return true;


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.allpost_logout:
                mAuth.signOut();
                startActivity(new Intent (getApplicationContext(), StartUp.class));
                break;

            case R.id.allpost_home:
                startActivity(new Intent (getApplicationContext(), AllPostActivity.class));
                break;

            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
