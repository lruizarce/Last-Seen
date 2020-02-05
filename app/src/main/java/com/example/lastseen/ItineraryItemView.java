package com.example.lastseen;

import android.content.Context;

import androidx.appcompat.widget.AppCompatTextView;

public class ItineraryItemView extends AppCompatTextView {

    public ItineraryItemView(Context context) {
        super(context);
    }

    @Override
    public String toString(){
        return super.getText().toString();
    }
}
