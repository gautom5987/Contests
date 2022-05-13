package com.example.contests.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contests.R;
import com.example.contests.databinding.ItemContainerContestsBinding;
import com.example.contests.databinding.ItemContainerSitesBinding;
import com.example.contests.models.Site;
import com.example.contests.utility.Constants;

import java.util.List;

public class SitesAdapter extends RecyclerView.Adapter<SitesAdapter.SiteViewHolder> {
    private final List<Site> siteList;
    private final SiteListener siteListener;

    public SitesAdapter(List<Site> siteList, SiteListener siteListener) {
        this.siteList = siteList;
        this.siteListener = siteListener;
    }

    @NonNull
    @Override
    public SiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerSitesBinding itemContainerSitesBinding =
                ItemContainerSitesBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new SitesAdapter.SiteViewHolder(itemContainerSitesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SiteViewHolder holder, int position) {
        holder.setSiteData(siteList.get(position));
    }

    @Override
    public int getItemCount() {
        return siteList.size();
    }

    public class SiteViewHolder extends RecyclerView.ViewHolder {
        ItemContainerSitesBinding binding;

        public SiteViewHolder(@NonNull ItemContainerSitesBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void setSiteData(Site site) {
            binding.siteName.setText(site.name);

            switch (site.name) {
                case Constants.CODE_CODECHEF :
                    binding.siteLogo.setImageResource(R.drawable.codechef);
                    break;
                case Constants.CODE_CODEFORCES:
                case Constants.CODE_CODEFORCES_GYM:
                    binding.siteLogo.setImageResource(R.drawable.codeforces);
                    break;
                case Constants.CODE_TOPCODER:
                    binding.siteLogo.setImageResource(R.drawable.top_coder);
                    break;
                case Constants.CODE_ATCODER:
                    binding.siteLogo.setImageResource(R.drawable.at_coder);
                    break;
                case Constants.CODE_CS_ACADEMY:
                    binding.siteLogo.setImageResource(R.drawable.cs);
                    break;
                case Constants.CODE_HACKER_RANK:
                    binding.siteLogo.setImageResource(R.drawable.hacker_rank);
                    break;
                case Constants.CODE_HACKER_EARTH:
                    binding.siteLogo.setImageResource(R.drawable.hacker_earth);
                    break;
                case Constants.CODE_KICK_START:
                    binding.siteLogo.setImageResource(R.drawable.google);
                    break;
                case Constants.CODE_LEET_CODE:
                    binding.siteLogo.setImageResource(R.drawable.leetcode);
                    break;
                case Constants.CODE_TOPH:
                    binding.siteLogo.setImageResource(R.drawable.toph);
                    break;
            }

            binding.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public interface SiteListener {
        void onSiteClicked(Site site);
    }
}
