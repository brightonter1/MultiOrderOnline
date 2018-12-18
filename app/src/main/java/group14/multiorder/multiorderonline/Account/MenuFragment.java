package group14.multiorder.multiorderonline.Account;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import group14.multiorder.multiorderonline.BaseFragment;
import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.Menu;
import group14.multiorder.multiorderonline.obj.Store;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends BaseFragment {

    private ArrayList<Menu> menuArrayList = new ArrayList<>();
    private ListView editList;
    private menuAdapter nMenuAdapter;
    private DatabaseReference myRef;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private Store myStore;
    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editList = getView().findViewById(R.id.menu_edit_list);
        nMenuAdapter = new menuAdapter(getActivity(),R.layout.fragment_menu2, menuArrayList);
        toolbar = setToolbar("Menu");
        backBtn();
        addBtn();
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("Store");
        getDB();
    }

    public void getDB(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot store : dataSnapshot.getChildren()){
                    Store st = store.getValue(Store.class);
                    Log.d("System", st.getTitle());

                    if(st.getUser_id().equals(mAuth.getCurrentUser().getUid())){
                        myStore = st;
                    }
                }

                Log.d("System", myStore.getTitle() + " " + myStore.getUser_id());

                myRef = FirebaseDatabase.getInstance().getReference("Menus/"+myStore.getTitle());
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot menu : dataSnapshot.getChildren()){
                            Menu temp = menu.getValue(Menu.class);
                            menuArrayList.add(temp);
                        }
                        editList.setAdapter(nMenuAdapter);
                        editList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Menu currentMenu = menuArrayList.get(position);
                                Bundle bundle = new Bundle();
                                bundle.putString("shoptitle", myStore.getTitle());
                                bundle.putString("menudescription", currentMenu.getDescription());
                                bundle.putString("menutitle", currentMenu.getTitle());
                                bundle.putString("menuimage", currentMenu.getImage());
                                bundle.putString("menuprice", currentMenu.getPrice());
                                bundle.putInt("menushopID", currentMenu.getShop_id());

                                EditMenuFragment editMenuFragment = new EditMenuFragment();
                                editMenuFragment.setArguments(bundle);

                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.main_view, editMenuFragment)
                                        .addToBackStack(null)
                                        .commit();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void addBtn(){
        Button addBtn = getActivity().findViewById(R.id.menu_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuForm())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void backBtn() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new AccountFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public Toolbar setToolbar(String nPager) {
        return super.setToolbar(nPager);
    }
}
