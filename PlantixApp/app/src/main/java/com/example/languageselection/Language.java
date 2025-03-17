package com.example.languageselection;

public class Language {
    private String name;
    private String description;
    private boolean isSelected;

    public Language(String name, String description) {
        this.name = name;
        this.description = description;
        this.isSelected = false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
