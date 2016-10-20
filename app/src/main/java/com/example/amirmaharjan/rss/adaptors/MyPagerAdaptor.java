package com.example.amirmaharjan.rss.adaptors;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.amirmaharjan.rss.fragments.latest_news;
import com.example.amirmaharjan.rss.fragments.media_class;
import com.example.amirmaharjan.rss.fragments.ticket_news;

import java.util.List;

/**
 * Created by Amir Maharjan on 10/20/2016.
 */

public class MyPagerAdaptor extends FragmentPagerAdapter {

    int mNumOfTabs;

    public MyPagerAdaptor(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    public void currentPage(int pageLimit){
        this.mNumOfTabs = pageLimit;
        notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                latest_news tab1 = new latest_news();
                return tab1;
            case 1:
                media_class tab2 = new media_class();
                return tab2;
            case 2:
                ticket_news tab3 = new ticket_news();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if (position >= 0) {
            // The current data matches the data in this active fragment, so let it be as it is.
            return position;
        } else {
            // Returning POSITION_NONE means the current data does not matches the data this fragment is showing right now.  Returning POSITION_NONE constant will force the fragment to redraw its view layout all over again and show new data.
            return POSITION_NONE;
        }
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
