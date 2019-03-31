package mra.com.vehicletracker;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import mra.com.vehicletracker.fragment.BlackListFragment;
import mra.com.vehicletracker.fragment.WhitelistFragment;

public class BlackListedVehicleForAdmin extends AppCompatActivity
{

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_listed_vehicle_for_admin);




        final TabLayout tabLayout=findViewById(R.id.tablayout);
        viewPager=(ViewPager)findViewById(R.id.bview);




        final BlackListedVehicleForAdmin.ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new BlackListFragment(), "Black Listed");
        viewPagerAdapter.addFragment(new WhitelistFragment(), "White Listed");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);




    }

    class ViewPagerAdapter extends FragmentPagerAdapter
    {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm)
        {
            super(fm);
            this.fragments=new ArrayList<>();
            this.titles=new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position)
        {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        public void addFragment(Fragment fragment,String title)
        {
            fragments.add(fragment);
            titles.add(title);
        }
        //ctrl+o
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
