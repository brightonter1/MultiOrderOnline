package group14.multiorder.multiorderonline;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class RoleSelectorFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_role_selector, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        customerButton();
        suplierButton();


    }


    public void suplierButton(){
        Button _supplierBtn = getView().findViewById(R.id.role_selector_suplier_btn01);
        _supplierBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.main_view, new PostFragment())
//                        .addToBackStack(null)
//                        .commit();
            }
        });
    }

    public void customerButton(){
        Button _customerBtn = getView().findViewById(R.id.role_selector_customer_btn01);
        _customerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.main_view, new LoginFragment())
//                        .commit();
            }
        });
    }



}
