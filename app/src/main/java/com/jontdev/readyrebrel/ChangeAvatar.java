package com.jontdev.readyrebrel;

import androidx.lifecycle.ViewModel;

public class ChangeAvatar extends ViewModel {
    private int imageResource = R.drawable.artist;

    public int getSelectedImageResourceId() {
        return imageResource;
    }

    public void setSelectedImageResourceId(int resourceId) {
        imageResource = resourceId;
    }
}

