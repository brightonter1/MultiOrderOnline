package group14.multiorder.multiorderonline.Account;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import group14.multiorder.multiorderonline.BaseFragment;
import group14.multiorder.multiorderonline.MainActivity;
import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.SelectPhotoDialog;
import group14.multiorder.multiorderonline.ViewOrderedActivity;
import group14.multiorder.multiorderonline.obj.Store;
import group14.multiorder.multiorderonline.util.UniversalImageLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends BaseFragment implements SelectPhotoDialog.OnPhotoSelectedListener {

    public static final String  TAG = "AccountFragment";

    private ArrayList<String> option = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDB;
    private String _Name;
    private String _Email;
    private TextView vName;
    private TextView vEmail;
    private Toolbar toolbar;
    DatabaseReference reference;

    ImageView _penImg;
    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        toolbar = getActivity().findViewById(R.id.account_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_back);
        TextView title = getActivity().findViewById(R.id.title_bar);

        title.setText("My Profile");
        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseFirestore.getInstance();
        getProfile();
        ListOption();
        initLogout();
        toolbar = setToolbar("My Profile");
        backBtn();
    }

    @Override
    public void backBtn(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountFragment.this.getActivity().finish();
            }
        });
    }


    private void ListOption() {
        ImageView _pen = getView().findViewById(R.id.account_btn_image);
        SharedPreferences sp = getActivity().getSharedPreferences("center", Context.MODE_PRIVATE);
        String type = sp.getString("type", "not found");
        Log.d("System", "get type : " + type);

        if (type.equals("customer")) {
            option.add("Track Order");
            option.add("History Order");
            _pen.setVisibility(View.INVISIBLE);
        } else {
            option.add("Edit Info");
            option.add("Menu");
            option.add("View Ordered");
        }
        final ListView _optionList = getView().findViewById(R.id.account_list);
        final ArrayAdapter<String> _optionAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, option);
        _optionList.setAdapter(_optionAdapter);

        _optionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (_optionAdapter.getItem(position)) {

                    case "Track Order":
                        Log.d("System", "Track Order");
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new TrackOrderFragment())
                                .commit();
                        break;
                    case "History Order":
                        Log.d("System", "History Order");
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new OrderHistory())
                                .addToBackStack(null)
                                .commit();
                        break;
                    case "Edit Info":
                        Log.d("System", "Edit info");
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new EditInfoFragment()) // chang back to edit info duay
                                .addToBackStack(null)
                                .commit();
                        break;
                    case "Menu":
                        Log.d("System", "Menu");
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new MenuFragment())
                                .addToBackStack(null)
                                .commit();
                        break;
                    case "View Ordered":
                        Intent intent = new Intent(getActivity() ,ViewOrderedActivity.class);
                        startActivity(intent);
                        Log.d("System", "Edit Menu");
                        break;
                }
            }
        });


    }
    Store myStore = new Store();
    String name;
    ImageView _img;
    private void getProfile(){
        _penImg = getView().findViewById(R.id.account_btn_image);
        //_penImg.setVisibility(View.INVISIBLE);
        _penImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageInit();
            }
        });
        final String mUid = mAuth.getCurrentUser().getUid();
        _Email = mAuth.getCurrentUser().getEmail();
        _img = getView().findViewById(R.id.fragment_account_image);
        DatabaseReference _dataRef = FirebaseDatabase.getInstance().getReference("Store/");
        _dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dat : dataSnapshot.getChildren()){

                    if(dat.getValue(Store.class).getUser_id().equals(mAuth.getCurrentUser().getUid())){
                        myStore  = dat.getValue(Store.class);
                        name = myStore.getTitle();
                        Log.d("System", "title " + name);
                        Picasso.with(getContext())
                                .load(myStore.getImage())
                                .fit()
                                .centerCrop()
                                .into(_img);
                    }

                }
                mDB.collection("customer")
                        .document(mUid)
                        .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@javax.annotation.Nullable DocumentSnapshot snapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                                if (snapshot.exists()){
                                    _Name = snapshot.getString("email");
                                    setProfile();
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void setProfile(){
        vEmail = getActivity().findViewById(R.id.account_email);
        vEmail.setText(_Email);
    }
    private void initLogout(){
        Button btn = getActivity().findViewById(R.id.account_logout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("center", Context.MODE_PRIVATE);
                sp.edit().putString("type", "not found").apply();
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    //vars
    private Bitmap mSelectedBitmap;
    private Uri mSelectedUri;
    private byte[] mUploadBytes;
    private double mProgress = 0;

    @Override
    public void getImagePath(Uri imagePath) {
        Log.d(TAG, "getImagePath: setting the image to imageview");
        UniversalImageLoader.setImage(imagePath.toString(), _img);
        //assign to global variable
        mSelectedBitmap = null;
        mSelectedUri = imagePath;
        postInit();
    }

    @Override
    public void getImageBitmap(Bitmap bitmap) {
        Log.d(TAG, "getImageBitmap: setting the image to imageview");
        _img.setImageBitmap(bitmap);
        //assign to a global variable
        mSelectedUri = null;
        mSelectedBitmap = bitmap;
        postInit();
    }

    private void selectImageInit(){
                Log.d(TAG, "getImageBitmap: setting the image to imageview");
                SelectPhotoDialog dialog = new SelectPhotoDialog();
                dialog.show(getFragmentManager(), getString(R.string.dialog_select_photo));
                dialog.setTargetFragment(AccountFragment.this, 1);
        ProgressBar pg = getView().findViewById(R.id.fragment_account_progress);
        pg.setVisibility(View.VISIBLE);
    }

    private void postInit(){
        ProgressBar pg = getView().findViewById(R.id.fragment_account_progress);

        if(mSelectedBitmap != null && mSelectedUri == null){
            pg.setVisibility(View.VISIBLE);
                        UploadImage(mSelectedBitmap);
                    }
                    else if(mSelectedBitmap == null && mSelectedUri != null){
            pg.setVisibility(View.VISIBLE);
                        UploadImage(mSelectedUri);
                    }
    }

    private void UploadImage(Bitmap bitmap){
        Log.d(TAG, "uploadNewPhoto: uploading a new image bitmap to storage");
        ProgressBar pg = getView().findViewById(R.id.fragment_account_progress);
        pg.setVisibility(View.VISIBLE);
        AccountFragment.BackgroundImageResize resize = new AccountFragment.BackgroundImageResize(bitmap);
        //MenuForm.BackgroundImageResize resize = new MenuForm.BackgroundImageResize(bitmap);
        //PostFragment.BackgroundImageResize resize = new PostFragment.BackgroundImageResize(bitmap);
        Uri uri = null;
        resize.execute(uri);
    }
    private void UploadImage(Uri imagePath){
        Log.d(TAG, "uploadNewPhoto: uploading a new image uri to storage");
        ProgressBar pg = getView().findViewById(R.id.fragment_account_progress);
        pg.setVisibility(View.VISIBLE);
        AccountFragment.BackgroundImageResize resize = new AccountFragment.BackgroundImageResize(null);
        resize.execute(imagePath);
    }



    public static  byte[] getBytesFromBitmap(Bitmap bitmap, int quality){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        return stream.toByteArray();
    }

    public class BackgroundImageResize extends AsyncTask<Uri, Integer, byte[]>{
        Bitmap _bitmap;

        public BackgroundImageResize(Bitmap _bitmap) {
            this._bitmap = _bitmap;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBar pg = getView().findViewById(R.id.fragment_account_progress);
            pg.setVisibility(View.VISIBLE);
        }

        @Override
        protected byte[] doInBackground(Uri... uris) {
            Log.d(TAG, "doInBackground: Strat");

            if(_bitmap == null){
                try {
                    _bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uris[0]);

                }catch (IOException e){
                    Log.e(TAG, "doInBackground: IOException: "+ e.getMessage());
                }
            }
            byte[] bytes = null;
            Log.d(TAG, "doInBackground: megabytes before compression: "+ _bitmap.getByteCount()/1000000);
            bytes = getBytesFromBitmap(_bitmap, 100);
            Log.d(TAG, "doInBackground: megabytes before compression: "+ bytes.length/1000000);
            return bytes;
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);
            mUploadBytes = bytes;
            excecuteUploadTask();
        }
    }

    private void excecuteUploadTask(){

        final StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("Store/"+myStore.getTitle()+"/"+myStore.getTitle());

        UploadTask uploadTask = storageReference.putBytes(mUploadBytes);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(), "Post Success", Toast.LENGTH_SHORT).show();

                //insert the download uri into firebase database
                Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String UI = uri.toString();
                        //System.out.print("URI = "+UI);
                        Log.d(TAG, "URI = "+UI);
                        reference = FirebaseDatabase.getInstance().getReference();
                        myStore.setImage(UI);
                        //Toast.makeText(getActivity(), myStore.getTitle(), Toast.LENGTH_SHORT).show();
                        reference.child("Store")
                                .child(myStore.getTitle())
                                .setValue(myStore).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                ProgressBar pg = getView().findViewById(R.id.fragment_account_progress);
                                pg.setVisibility(View.INVISIBLE);
                                Toast.makeText(getActivity(), "Succesfully update image", Toast.LENGTH_SHORT).show();
                            }
                        });
                        //resetFields();
                        Log.d(TAG, "OnSuccess: firebase download url"+ UI);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.print("ERERER");
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "could not upload photo", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double currentProgress = (100*taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                if(currentProgress > (mProgress+15)){
                    mProgress = (100*taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    Log.d(TAG, "onProgress: upload is "+ mProgress+ "& done");
                    //Toast.makeText(getActivity(), mProgress + "%", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
