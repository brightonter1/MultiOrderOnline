package group14.multiorder.multiorderonline;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import group14.multiorder.multiorderonline.obj.Store;


/**
 * A simple {@link Fragment} subclass.
 */
public class menuFragment extends Fragment {


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
        Log.d("boom", "Wow "+post);
        TextView textView = getView().findViewById(R.id.menu);
        textView.setText(post.getTitle());
    }
}
