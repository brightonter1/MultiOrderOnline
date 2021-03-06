package group14.multiorder.multiorderonline.Store;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import group14.multiorder.multiorderonline.Cart.CartActivity;
import group14.multiorder.multiorderonline.Cart.CartFragment;
import group14.multiorder.multiorderonline.MainActivity;
import group14.multiorder.multiorderonline.R;

import group14.multiorder.multiorderonline.obj.Store;

public class ViewMenuActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_menu);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        data();
        BackBtn();
    }

    public void BackBtn(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void data(){
        Store post = getIntent().getParcelableExtra("STORE");
        Log.d("POST", ""+post);
//        TextView tv = findViewById(R.id.store_name);
//        tv.setText(post.getTitle());
        ImageView imageView = findViewById(R.id.image_store);
        TextView storeName = findViewById(R.id.store_name);

        Picasso.with(ViewMenuActivity.this)
                .load(post.getImage())
                .fit()
                .centerCrop()
                .into(imageView);
        storeName.setText(post.getTitle());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_cart){
            Intent intent = new Intent(ViewMenuActivity.this, CartActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    
                    return new menuFragment();
                case 1:
                    return new infoFragment();
            }
            return null;
        }



        @Override
        public int getCount() {
            return 2;
        }
    }

}
