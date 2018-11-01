package group14.multiorder.multiorderonline.Account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import group14.multiorder.multiorderonline.R;

public class AccountListFragment extends Fragment {
    String[] option = {"Sign Up", "Log in", "Setting", "Log out"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListOption();
    }

    public void ListOption(){
        ListView _optionList = getView().findViewById(R.id.account_list);
        ArrayAdapter<String> _optionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, option);
        _optionList.setAdapter(_optionAdapter);
    }


}
