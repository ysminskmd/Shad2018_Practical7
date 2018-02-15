package com.yandex.appmetrica.applications;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class PackageFragment extends Fragment {

    public PackageFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_package_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.list);
        Context context = recyclerView.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        PackageProvider packageProvider = new PackageProvider(context);
        final PackageRecyclerViewAdapter recyclerViewAdapter = new PackageRecyclerViewAdapter(packageProvider);
        recyclerView.setAdapter(recyclerViewAdapter);

        Button loadButton = view.findViewById(R.id.load);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewAdapter.load();
            }
        });

        Button clearButton = view.findViewById(R.id.clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewAdapter.clear();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
