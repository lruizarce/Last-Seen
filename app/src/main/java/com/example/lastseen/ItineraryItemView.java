package com.example.lastseen;

import android.content.Context;

import androidx.appcompat.widget.AppCompatTextView;

import org.jetbrains.annotations.NotNull;

public class ItineraryItemView extends AppCompatTextView {

    public ItineraryItemView(Context context) {
        super(context);
    }

    @NotNull
    @Override
    public String toString(){
        return super.getText().toString();
    }
}
