package group14.multiorder.multiorderonline;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
        customerLogin();
        suplierButton();


    }


    public void suplierButton(){
        Button _supplierBtn = getView().findViewById(R.id.role_selector_suplier_btn);
        _supplierBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public void customerLogin(){
        Button _customerBtn = getView().findViewById(R.id.role_selector_customer_btn);
        _customerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new LoginFragment())
                        .commit();
            }
        });
    }
}
