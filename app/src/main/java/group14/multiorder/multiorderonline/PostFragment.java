package group14.multiorder.multiorderonline;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import group14.multiorder.multiorderonline.util.UniversalImageLoader;


public class PostFragment extends Fragment implements SelectPhotoDialog.onPhotoSelectedLister {
    private static final String TAG = "PostFragment";

    private ImageView _postImage;
    private EditText _title;
    private ProgressBar _ProgressBar;

    private Bitmap mSelectedBitmap;
    private Uri mSelectedUri;
    @Override
    public void getImagePath(Uri imagePath) {
        Log.d(TAG, "getImagePth: setting the image to imageview");
        UniversalImageLoader.setImage(imagePath.toString(), _postImage);
        mSelectedBitmap = null;
        mSelectedUri = imagePath;
    }

    @Override
    public void getImageBitmap(Bitmap bitmap) {
        Log.d(TAG, "getImageBitmap: setting the image to imageview");
        _postImage.setImageBitmap(bitmap);
        mSelectedUri = null;
        mSelectedBitmap = bitmap;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        _postImage = view.findViewById(R.id.post_image);
        _title = view.findViewById(R.id.post_title);
        _ProgressBar = view.findViewById(R.id.progressBar);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        init();
        return view;
    }

    private void init(){
        _postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");
                SelectPhotoDialog dialog = new SelectPhotoDialog();
                dialog.show(getFragmentManager(), getString(R.string.dialog_select_photo));
                dialog.setTargetFragment(PostFragment.this, 1);
            }
        });
    }

    private void resetFiels(){
        UniversalImageLoader.setImage("", _postImage);
        _title.setText("");


    }

    private void showProgressBar(){
        _ProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        if(_ProgressBar.getVisibility() == View.VISIBLE){
            _ProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private boolean isEmpty(String string){
        return string.equals("");
    }


}
