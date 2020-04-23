package com.example.chapter3.homework;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class mPagerAdapter extends PagerAdapter {
    private static final String TAG = "FragmentPagerAdapter";
    private static final boolean DEBUG = false;
    private final FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;
    private static final int PAGE_COUNT = 2;

    public mPagerAdapter(FragmentManager fm) {
        this.mFragmentManager = fm;
    }

    private Fragment getItem(int i) {
        return  new PlaceholderFragment();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    public void startUpdate(@NonNull ViewGroup container) {
        Log.d(TAG, "startUpdate is called");
        if (container.getId() == -1) {
            throw new IllegalStateException("ViewPager with adapter " + this + " requires a view id");
        }

    }

    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.d(TAG, "instantiateItem is called");
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }

        long itemId = this.getItemId(position);
        String name = makeFragmentName(container.getId(), itemId);
        Fragment fragment = this.mFragmentManager.findFragmentByTag(name);
        if (fragment != null) {
            Log.d(TAG, "fragment is not null");
            this.mCurTransaction.attach(fragment);
        } else {
            Log.d(TAG, "fragment is created!");
            fragment = this.getItem(position);
            this.mCurTransaction.add(container.getId(), fragment, makeFragmentName(container.getId(), itemId));
        }

        if (fragment != this.mCurrentPrimaryItem) {
            fragment.setMenuVisibility(false);
            fragment.setUserVisibleHint(false);
        }

        return fragment;
    }

    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        Log.d(TAG,"destroyItem is called, position is " + position);
        this.mCurTransaction.detach((Fragment)object);
    }

    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //fragment是新的，mCurrentPrimaryItem是旧的
        Log.d(TAG, "setPrimaryItem is called  "+ position);
        Fragment fragment = (Fragment)object;
        if (fragment != this.mCurrentPrimaryItem) {
            if (this.mCurrentPrimaryItem != null) {
                this.mCurrentPrimaryItem.setMenuVisibility(false);
                this.mCurrentPrimaryItem.setUserVisibleHint(false);
            }

            fragment.setMenuVisibility(true);
            fragment.setUserVisibleHint(true);
            this.mCurrentPrimaryItem = fragment;
        }

    }

    public void finishUpdate(@NonNull ViewGroup container) {
        Log.d(TAG, "startUpdate is called");
        if (this.mCurTransaction != null) {
            this.mCurTransaction.commitNowAllowingStateLoss();
            this.mCurTransaction = null;
        }

    }

    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return ((Fragment)object).getView() == view;
    }

    public Parcelable saveState() {
        return null;
    }

    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    public long getItemId(int position) {
        return (long)position;
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    @Override
    public CharSequence getPageTitle(int position){
        if(position == 0)
            return "-好友列表-";
        else
            return "-附近的人-";
    }
}
