package group14.multiorder.multiorderonline.Store;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import group14.multiorder.multiorderonline.Adpter.FastfoodAdapter;
import group14.multiorder.multiorderonline.ImageAdater;
import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.Store;

public class FastfoodFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FastfoodAdapter mAdater;
    private ProgressBar progressBar;
    private DatabaseReference mDatabaseRef;
    //private List<Post> mPosts;
    private List<Store> mPosts;


    public FastfoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fastfood, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = getView().findViewById(R.id.fastfood_recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = getView().findViewById(R.id.fastfood_progress);
        mPosts = new ArrayList<>();

        //mDatabaseRef = FirebaseDatabase.getInstance().getReference("posts");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Stores");
        mRecyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //Post post = postSnapshot.getValue(Post.class);
                    Store store = postSnapshot.getValue(Store.class);
                    if(store.getTag().equals("fastfood")){mPosts.add(store);}

                }
                mAdater = new FastfoodAdapter(getActivity(), mPosts);
                mRecyclerView.setAdapter(mAdater);
                mRecyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
