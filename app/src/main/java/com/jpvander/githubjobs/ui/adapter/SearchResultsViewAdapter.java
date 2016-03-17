package com.jpvander.githubjobs.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.fragment.ViewSearchResultsFragment.OnFragmentInteractionListener;
import com.jpvander.githubjobs.dataset.data.GitHubJobs;
import com.jpvander.githubjobs.ui.graphics.ImageTransform;
import com.jpvander.githubjobs.ui.holder.RecyclerTextViewAndImageViewHolder;
import com.squareup.picasso.Picasso;

public class SearchResultsViewAdapter extends RecyclerView.Adapter<RecyclerTextViewAndImageViewHolder> {

    private final OnFragmentInteractionListener listener;
    private GitHubJobs jobs;
    private final float density;

    public SearchResultsViewAdapter(OnFragmentInteractionListener listener) {
        this.jobs = new GitHubJobs();
        this.listener = listener;
        this.density = Resources.getSystem().getDisplayMetrics().density;
    }

    @Override
    public RecyclerTextViewAndImageViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        int imageAndTextView = R.layout.item_image_and_text_view;
        Context context = parent.getContext();
        View parentView = LayoutInflater.from(context).inflate(imageAndTextView, parent, false);
        return new RecyclerTextViewAndImageViewHolder(parentView);
    }

    @Override
    public void onBindViewHolder(final RecyclerTextViewAndImageViewHolder holder, final int position) {
        //TODO: Add a placeholder image for companies that have not uploaded their logo?
        holder.imageView.setImageBitmap(null);
        holder.textView.setText("");
        Picasso.with(holder.getContext())
                .load(jobs.get(position).getCompany_logo())
                .transform(new ImageTransform(density, holder.getPreferredImageWidth()))
                .into(holder.imageView);
        holder.textView.setText(jobs.get(position).getJobTitle());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onViewSearchResultsInteraction(jobs.get(position));
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
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
