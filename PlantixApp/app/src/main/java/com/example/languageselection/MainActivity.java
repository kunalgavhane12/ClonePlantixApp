package com.example.languageselection;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnAccept;
    private LanguageAdapter adapter;
    private List<Language> languages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Ensure this matches your XML file name

        recyclerView = findViewById(R.id.recyclerViewLanguages);
        btnAccept = findViewById(R.id.btnAccept);
        btnAccept.setEnabled(false); // Disable button initially

        // Initialize the language list
        languages = new ArrayList<>();
        languages.add(new Language("हिन्दी", "खेती आपकी भाषा में"));
        languages.add(new Language("English", "Farming in your language"));
        languages.add(new Language("Français", "L'agriculture dans votre langue"));
        languages.add(new Language("Português", "Agricultura no seu idioma"));
        languages.add(new Language("हिन्दी", "खेती आपकी भाषा में"));
        languages.add(new Language("English", "Farming in your language"));
        languages.add(new Language("Français", "L'agriculture dans votre langue"));
        languages.add(new Language("Português", "Agricultura no seu idioma"));
        languages.add(new Language("हिन्दी", "खेती आपकी भाषा में"));
        languages.add(new Language("English", "Farming in your language"));
        languages.add(new Language("Français", "L'agriculture dans votre langue"));
        languages.add(new Language("Português", "Agricultura no seu idioma"));

        // Set up RecyclerView
        adapter = new LanguageAdapter(languages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Add spacing between items
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(30)); // Adjust the gap size in pixels

        // Enable button when a language is selected
        adapter.setOnLanguageSelectListener(() -> btnAccept.setEnabled(true));

        // Handle Accept button click
        btnAccept.setOnClickListener(v -> {
            int selectedIndex = adapter.getSelectedPosition();
            if (selectedIndex != -1) {
                Language selectedLanguage = languages.get(selectedIndex);
                // Proceed with selected language (e.g., Save selection or Open new Activity)
                Intent introIntent = new Intent(MainActivity.this, IntroActivity.class);
                startActivity(introIntent);
            }
        });
    }

    // Custom class to add spacing between items
    public static class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final int verticalSpaceHeight;

        public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                   @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;
        }
    }
}
