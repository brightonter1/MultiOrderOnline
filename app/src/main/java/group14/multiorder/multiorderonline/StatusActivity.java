package group14.multiorder.multiorderonline;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import group14.multiorder.multiorderonline.Account.AllOrderFragment;
import group14.multiorder.multiorderonline.Account.FinishOrderFragment;
import group14.multiorder.multiorderonline.Account.ProgressOrderFragment;
import group14.multiorder.multiorderonline.Store.AllFragment;
import group14.multiorder.multiorderonline.Store.DessertFragment;
import group14.multiorder.multiorderonline.Store.FastfoodFragment;

public class StatusActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        toolbar = findViewById(R.id.account_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        TextView title = findViewById(R.id.title_bar);
        title.setText("Ordered History");

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        TabLayout tabLayout = findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new AllOrderFragment();
                case 1:
                    return new ProgressOrderFragment();
                case 2:
                    return new FinishOrderFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
