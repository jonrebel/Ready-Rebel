package com.jontdev.readyrebrel;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class Welcome extends Fragment {

    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.welcome, container, false);
        relativeLayout = view.findViewById(R.id.welcome_screen);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRebel();
                button.setVisibility(View.INVISIBLE);
            }
        });






        return view;
    }

    public void openRebel() {
        getParentFragmentManager().beginTransaction().replace(R.id.welcome_screen, new Rebel()).commit();
    }


}
