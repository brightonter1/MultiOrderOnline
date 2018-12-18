package group14.multiorder.multiorderonline.Account;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import group14.multiorder.multiorderonline.BaseFragment;
import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.Menu;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends BaseFragment {

    ArrayList<Menu> menuArrayList = new ArrayList<>();
    ListView editList;
    menuAdapter menuAdapter;

    private Toolbar toolbar;

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
        menuAdapter = new menuAdapter(getActivity(),R.layout.fragment_menu2, menuArrayList);

        
        toolbar = setToolbar("Menu");
        backBtn();
        addBtn();
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
