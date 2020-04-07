package com.example.vrindavan.ThreeTabs.HomeAll;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.vrindavan.ThreeTabs.OrderAll.OrderFragment;
import com.example.vrindavan.ThreeTabs.ProfileFragment;
import com.example.vrindavan.activites.MainActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final List<TabLayout.Tab> mFragmentList = new ArrayList<TabLayout.Tab>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
     int tabCount;

    public SectionsPagerAdapter(FragmentManager manager,int tabCount) {
        super(manager);
       this.tabCount=tabCount;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                //MainActivity.type="home";
                return new HomeFragment();

            case 1:
               // MainActivity.type="order";
                return new OrderFragment();

            case 2:
                return new ProfileFragment();

                default:
                    return null;

        }

      // return mFragmentList.get(position);
    }

    @Override
    public int getCount() {

     //  return tabCount;
       return mFragmentList.size();
    }

    public void addFragment(TabLayout.Tab fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mFragmentTitleList.get(position);
    }
}