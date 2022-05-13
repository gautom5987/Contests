package com.example.contests.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contests.R;
import com.example.contests.databinding.ItemContainerContestsBinding;
import com.example.contests.databinding.ItemContainerSitesBinding;
import com.example.contests.models.Site;

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

            switch (site.code) {
                case "code_chef":
                    binding.siteLogo.setImageResource(R.drawable.codechef);
                    break;
                case "codeforces":
                case "codeforces_gym":
                    binding.siteLogo.setImageResource(R.drawable.codeforces);
                    break;
                case "top_coder":
                    binding.siteLogo.setImageResource(R.drawable.top_coder);
                    break;
                case "at_coder":
                    binding.siteLogo.setImageResource(R.drawable.at_coder);
                    break;
                case "cs_academy":
                    binding.siteLogo.setImageResource(R.drawable.cs);
                    break;
                case "hacker_rank":
                    binding.siteLogo.setImageResource(R.drawable.hacker_rank);
                    break;
                case "hacker_earth":
                    binding.siteLogo.setImageResource(R.drawable.hacker_earth);
                    break;
                case "kick_start":
                    binding.siteLogo.setImageResource(R.drawable.google);
                    break;
                case "leet_code":
                    binding.siteLogo.setImageResource(R.drawable.leetcode);
                    break;
                case "toph":
                    binding.siteLogo.setImageResource(R.drawable.toph);
                    break;
            }
        }
    }

    public interface SiteListener {
        void onSiteClicked(Site site);
    }
}
