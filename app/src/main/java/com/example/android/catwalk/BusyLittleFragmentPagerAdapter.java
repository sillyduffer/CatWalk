package com.example.android.catwalk;

import android.support.v4.app.*;

class BusyLittleFragmentPagerAdapter extends FragmentPagerAdapter {

    public BusyLittleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new CameraFragment();
        } else if (position == 1) {
            return new CatsListFragment();
        } else {
            return new DetailsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
