package com.example.mukfinalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MukCustomAdapter extends RecyclerView.Adapter<MukCustomAdapter.MukViewHolder> {

    interface MukOnItemClickListener {
        public void mukOnItemClick(MukCustomAdapter mukCustomAdapter, int mukPosition);
    }

    // Variables
    private MukQuestion mukQuestion;
    private MukOnItemClickListener mukOnItemClickListener;

    // Constructors
    public MukCustomAdapter(MukQuestion mukQuestion) {
        this.mukQuestion = mukQuestion;
    }

    // RecyclerView.Adapter Methods
    @Override
    public MukViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mukLayoutInflater = LayoutInflater.from(parent.getContext());

        View mukView = mukLayoutInflater.inflate(R.layout.recycler_item_layout, parent, false);
        GridLayoutManager.LayoutParams mukLayoutParams = (GridLayoutManager.LayoutParams) mukView.getLayoutParams();
        mukLayoutParams.height = (parent.getMeasuredHeight() / 2) - 30; // Watch out for Hardcoding
        mukLayoutParams.setMargins(15, 15, 15, 15);
        mukView.setLayoutParams(mukLayoutParams);

        MukViewHolder mukViewHolder = new MukViewHolder(mukView);

        return mukViewHolder;
    }

    @Override
    public void onBindViewHolder(MukViewHolder holder, int position) {
        switch(position) {
            case 0:
                holder.mukQuestionOptionImageView.setImageResource(R.drawable.xcode);
                break;
            case 1:
                holder.mukQuestionOptionImageView.setImageResource(R.drawable.android_studio);
                break;
            case 2:
                holder.mukQuestionOptionImageView.setImageResource(R.drawable.visual_studio);
                break;
            case 3:
                holder.mukQuestionOptionImageView.setImageResource(R.drawable.ruby_mine);
                break;
        }

        if(position == mukQuestion.getMukChosenIndex())
            holder.itemView.setBackgroundResource(R.drawable.view_border);
        else
            holder.itemView.setBackgroundResource(0);
    }

    @Override
    public int getItemCount() {
        return mukQuestion.getMukOptions().length;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        mukOnItemClickListener = null;
    }

    // Getters and Setters
    public MukQuestion getMukQuestion() {
        return mukQuestion;
    }

    public void setMukQuestion(MukQuestion mukQuestion) {
        this.mukQuestion = mukQuestion;
    }

    public MukOnItemClickListener getMukOnItemClickListener() {
        return mukOnItemClickListener;
    }

    public void setMukOnItemClickListener(MukOnItemClickListener mukOnItemClickListener) {
        this.mukOnItemClickListener = mukOnItemClickListener;
    }

    // Custom View Holder Inner Class
    public class MukViewHolder extends RecyclerView.ViewHolder {
        ImageView mukQuestionOptionImageView;

        public MukViewHolder(View mukView) {
            super(mukView);
            mukQuestionOptionImageView = (ImageView) mukView.findViewById(R.id.questionOptionImageView);

            mukView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mukPosition = getAdapterPosition();
                    if(mukOnItemClickListener != null)
                        mukOnItemClickListener.mukOnItemClick(MukCustomAdapter.this, mukPosition);
                }
            });
        }
    }
}
