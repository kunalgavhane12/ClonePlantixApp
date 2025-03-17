package com.example.languageselection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {

    private List<Language> languages;
    private int selectedPosition = -1; // To store the selected language index
    private OnLanguageSelectListener onLanguageSelectListener;

    public interface OnLanguageSelectListener {
        void onLanguageSelected();
    }

    public void setOnLanguageSelectListener(OnLanguageSelectListener listener) {
        this.onLanguageSelectListener = listener;
    }

    public LanguageAdapter(List<Language> languages) {
        this.languages = languages;
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_language, parent, false);
        return new LanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {
        Language language = languages.get(position);
        holder.languageName.setText(language.getName());
        holder.languageDescription.setText(language.getDescription());
        holder.radioButton.setChecked(position == selectedPosition);

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged(); // Refresh the list to update selection

            if (onLanguageSelectListener != null) {
                onLanguageSelectListener.onLanguageSelected();
            }
        });

        holder.radioButton.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged();

            if (onLanguageSelectListener != null) {
                onLanguageSelectListener.onLanguageSelected();
            }
        });
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public static class LanguageViewHolder extends RecyclerView.ViewHolder {
        TextView languageName, languageDescription;
        RadioButton radioButton;

        public LanguageViewHolder(@NonNull View itemView) {
            super(itemView);
            languageName = itemView.findViewById(R.id.languageName);
            languageDescription = itemView.findViewById(R.id.languageDescription);
            radioButton = itemView.findViewById(R.id.radioButton);
        }
    }
}
