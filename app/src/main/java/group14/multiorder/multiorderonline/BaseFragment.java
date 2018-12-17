package group14.multiorder.multiorderonline;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public abstract class BaseFragment extends Fragment {
    public Toolbar toolbar = null;

    public abstract void backBtn();

    public Toolbar setToolbar(String nPager){
        toolbar = getActivity().findViewById(R.id.account_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        TextView title = getActivity().findViewById(R.id.title_bar);
        title.setText(nPager);
        return toolbar;
    }
}
