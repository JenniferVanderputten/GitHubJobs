package com.jpvander.githubjobs.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.activities.searches.ViewSearchResultsFragment.OnFragmentInteractionListener;
import com.jpvander.githubjobs.datasets.data.GitHubJobs;
import com.jpvander.githubjobs.ui.graphics.ImageDownloader;
import com.jpvander.githubjobs.ui.graphics.ImageDownloaderCallback;
import com.jpvander.githubjobs.ui.holders.RecyclerImageAndTextViewHolder;

import java.util.ArrayList;

public class SearchResultsViewAdapter extends RecyclerView.Adapter<RecyclerImageAndTextViewHolder> {

    private final OnFragmentInteractionListener listener;
    private GitHubJobs jobs;
    private final float displayDensity;

    public SearchResultsViewAdapter(OnFragmentInteractionListener listener, float displayDensity) {
        this.jobs = new GitHubJobs();
        this.listener = listener;
        this.displayDensity = displayDensity;
    }

    @Override
    public RecyclerImageAndTextViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        int imageAndTextView = R.layout.item_image_and_text_view;
        Context context = parent.getContext();
        View parentView = LayoutInflater.from(context).inflate(imageAndTextView, parent, false);
        return new RecyclerImageAndTextViewHolder(parentView);
    }

    @Override
    public void onBindViewHolder(final RecyclerImageAndTextViewHolder holder, final int position) {
        holder.imageView.setImageBitmap(null);
        holder.textView.setText("");

        Bitmap cachedBitmap = jobs.get(position).getLogo();

        if (null != cachedBitmap) {
            holder.imageView.setImageBitmap(cachedBitmap);
        }
        else {
            String logoUrl = jobs.get(position).getCompany_logo();
            if (null != logoUrl && !logoUrl.isEmpty()) {
                new ImageDownloader(
                        displayDensity,
                        holder.getPreferredImageWidth(),
                        new ImageDownloaderCallback() {
                            @Override
                            public void onDownloadComplete(Bitmap bitmap) {
                                holder.imageView.setImageBitmap(bitmap);
                                jobs.get(position).setLogo(bitmap);
                            }
                        }
                ).execute(logoUrl);
            }
        }

        ArrayList<String> jobFields = new ArrayList<>();
        jobFields.add(jobs.get(position).getCompany());
        jobFields.add(jobs.get(position).getTitle());
        jobs.get(position).setDisplayTitle(jobFields);
        holder.textView.setText(jobs.get(position).getDisplayTitle());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onViewSearchResultsInteraction(jobs.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == jobs) { return 0; }
        return jobs.size();
    }

    public void updateDataSet(GitHubJobs jobs) {
        this.jobs = jobs;
        this.notifyDataSetChanged();
    }
}
