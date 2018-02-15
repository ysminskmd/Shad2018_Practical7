package com.yandex.appmetrica.applications;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PackageRecyclerViewAdapter extends RecyclerView.Adapter<PackageRecyclerViewAdapter.ViewHolder> {

    private final List<String> mPackages = new ArrayList<>();
    private final PackageProvider mPackageProvider;

    PackageRecyclerViewAdapter(PackageProvider packageProvider) {
        mPackageProvider = packageProvider;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_package, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mPackage = mPackages.get(position);
        holder.mContentView.setText(holder.mPackage);
    }

    @Override
    public int getItemCount() {
        return mPackages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mContentView;
        String mPackage;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    void load() {
        mPackages.addAll(mPackageProvider.getInstalledPackageNames());
        notifyDataSetChanged();
    }

    void clear() {
        mPackages.clear();
        notifyDataSetChanged();
    }
}
