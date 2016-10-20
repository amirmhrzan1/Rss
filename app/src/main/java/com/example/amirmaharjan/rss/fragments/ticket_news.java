package com.example.amirmaharjan.rss.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amirmaharjan.rss.Parsing.ReadRss;
import com.example.amirmaharjan.rss.R;

/**
 * Created by Amir Maharjan on 10/20/2016.
 */

public class ticket_news extends Fragment {
    String url ="http://www.liverpoolfc.com/news/tickets.rss";
    boolean state = false;
    RecyclerView recyclerView;
    ReadRss networktask;
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.latestnews, container, false);

        return v;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        state = isVisibleToUser;
        if(state==true)
        {
            recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
            networktask=new ReadRss(getActivity(),recyclerView);
            networktask.execute(url);

        }
    }
}
