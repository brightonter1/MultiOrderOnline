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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import group14.multiorder.multiorderonline.Adpter.MenuAdapter;

import org.w3c.dom.Text;

import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.Menu;
import group14.multiorder.multiorderonline.obj.Store;


/**
 * A simple {@link Fragment} subclass.
 */
public class menuFragment extends Fragment {
    RecyclerView _recylerView;
    MenuAdapter _imageAdapter;

    private DatabaseReference _databaseRef;
    private List<Menu> _post;


    public menuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Store post = getActivity().getIntent().getParcelableExtra("STORE");

        _recylerView = getView().findViewById(R.id.menu_recycler_view);
        _recylerView.setHasFixedSize(true);
        _recylerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        _post = new ArrayList<>();
        _databaseRef = FirebaseDatabase.getInstance().getReference("Menus/"+post.getTitle());

        _databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Toast.makeText(getActivity(), String.valueOf(dataSnapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();
                for(DataSnapshot menuSnapshot : dataSnapshot.getChildren()){
                    Menu menu = menuSnapshot.getValue(Menu.class);
                    _post.add(menu);
                }
                _imageAdapter = new MenuAdapter(getActivity(), _post);
                _recylerView.setAdapter(_imageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
