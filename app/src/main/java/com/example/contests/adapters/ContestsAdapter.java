package com.example.contests.adapters;

import android.content.Intent;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contests.R;
import com.example.contests.databinding.ItemContainerContestsBinding;
import com.example.contests.models.Contest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ContestsAdapter extends RecyclerView.Adapter<ContestsAdapter.ContestViewHolder> {

    private final List<Contest> contestList;
    private final ContestListener contestListener;

    public ContestsAdapter(List<Contest> contestList, ContestListener contestListener) {
        this.contestList = contestList;
        this.contestListener = contestListener;
    }

    @NonNull
    @Override
    public ContestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerContestsBinding itemContainerContestsBinding = ItemContainerContestsBinding.inflate(
            LayoutInflater.from(parent.getContext()),
            parent,
                false
        );
        return new ContestViewHolder(itemContainerContestsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContestViewHolder holder, int position) {
        holder.setContestData(contestList.get(position));
    }

    @Override
    public int getItemCount() {
        return contestList.size();
    }

    public class ContestViewHolder extends RecyclerView.ViewHolder {
        ItemContainerContestsBinding binding;

        public ContestViewHolder(@NonNull ItemContainerContestsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void setContestData(Contest contest) {
            binding.contestName.setText(contest.name);
            if(contest.site.equals("CodeChef")) {
                binding.contestDate.setText(getCodechefDate(contest.startTime));
            } else {
                binding.contestDate.setText(getReadableDate(contest.startTime));
            }

            switch (contest.site) {
                case "CodeChef":
                    binding.imageProfile.setImageResource(R.drawable.codechef);
                    break;
                case "CodeForces":
                case "codeforces_gym":
                    binding.imageProfile.setImageResource(R.drawable.codeforces);
                    break;
                case "TopCoder":
                    binding.imageProfile.setImageResource(R.drawable.top_coder);
                    break;
                case "AtCoder":
                    binding.imageProfile.setImageResource(R.drawable.at_coder);
                    break;
                case "CsAcademy":
                    binding.imageProfile.setImageResource(R.drawable.cs);
                    break;
                case "HackerRank":
                    binding.imageProfile.setImageResource(R.drawable.hacker_rank);
                    break;
                case "HackerEarth":
                    binding.imageProfile.setImageResource(R.drawable.hacker_earth);
                    break;
                case "Kick Start":
                    binding.imageProfile.setImageResource(R.drawable.google);
                    break;
                case "LeetCode":
                    binding.imageProfile.setImageResource(R.drawable.leetcode);
                    break;
                case "toph":
                    binding.imageProfile.setImageResource(R.drawable.toph);
                    break;
            }

            binding.getRoot().setOnClickListener(v -> contestListener.onContestClicked(contest));

            binding.cal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);
                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, contest.startTime);
                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, contest.endTime);
                    intent.putExtra(CalendarContract.Events.TITLE, contest.name);
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, contest.url);

                    if(intent.resolveActivity(v.getContext().getPackageManager())!=null) {
                        v.getContext().startActivity(intent);
                    } else {
                        Toast.makeText(v.getContext(), "No Calendar Application!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        public String getCodechefDate(String str) {
            String ans = str;
            try {
                Date date =
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(str.substring(0,
                                str.length()-4));
                ans = new SimpleDateFormat("HH:mm MMM, dd E", Locale.getDefault()).format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return ans;
        }

        public String getReadableDate(String str) {
            LocalDateTime time = LocalDateTime.parse(str.substring(0,str.length()-2));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm MMM, dd E");
            return time.format(formatter);
        }
    }

    public interface ContestListener {
        void onContestClicked(Contest contest);
    }
}
