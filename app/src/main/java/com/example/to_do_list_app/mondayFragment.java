package com.example.to_do_list_app;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class mondayFragment extends Fragment {

      private RecyclerView recy;


    private daysDataAdapter adapter;
    private View rootView;

      private ArrayList<String> titles= new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment

         rootView=inflater.inflate(R.layout.fragment_monday, container, false);
        recy = rootView.findViewById(R.id.RecyclerView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        manager.canScrollVertically();
        recy.setLayoutManager(manager);
        adapter = new daysDataAdapter(getContext(), titles);
        recy.setAdapter(adapter);
        Bundle bundle=getArguments();
        if(bundle!=null) {
            String day = bundle.getString("day");
            ArrayList<String> initialTitles=bundle.getStringArrayList(day);
            if(initialTitles!=null)
            {
                updateTitles(initialTitles);
            }
        }

        return  rootView;
    }
    public void updateTitles(ArrayList<String> newTitles) {
        titles.clear();
        titles.addAll(newTitles);
        adapter.notifyDataSetChanged();
    }
    public void changeBackground(int color)
    {    if(color==1){
           rootView.findViewById(R.id.mondayFragment).setBackgroundColor(Color.BLACK);
           recy.setBackgroundColor(Color.BLACK);
       }
    }
}