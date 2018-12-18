package group14.multiorder.multiorderonline.Account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.Menu;

public class EditMenuFragment extends Fragment {

    private DatabaseReference _dataRef;
    TextView _title;
    EditText _price;
    EditText _description;
    Bundle bundle;

    Menu newMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        newMenu = new Menu();
        super.onActivityCreated(savedInstanceState);
        _title = getView().findViewById(R.id.edit_menu_title);
        _price = getView().findViewById(R.id.edit_menu_price);
        _description = getView().findViewById(R.id.edit_menu_description);
        bundle = this.getArguments();
        _title.setText(bundle.get("menutitle").toString());
        _price.setText(bundle.get("menuprice").toString());
        _description.setText(bundle.get("menudescription").toString());
        _dataRef = FirebaseDatabase.getInstance().getReference("Menus/"+bundle.getString("shoptitle").toString());


        newMenu.setImage(bundle.getString("menuimage"));
        newMenu.setTitle(bundle.getString("menutitle"));
        newMenu.setShop_id(bundle.getInt("menushopID"));



        Button saveButton = getView().findViewById(R.id.edit_menu_save_btn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                newMenu.setDescription(_description.getText().toString());
                newMenu.setPrice(_price.getText().toString());
                Log.d("System", bundle.getString("menuimage") + bundle.getString("menutitle")
                        + String.valueOf((bundle.getInt("menushopID"))) + _description.getText().toString() + _price.getText().toString());


                _dataRef.child(bundle.getString("menutitle")).setValue(newMenu);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });


    }



}
