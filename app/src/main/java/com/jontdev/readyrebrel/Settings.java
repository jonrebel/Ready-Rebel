package com.jontdev.readyrebrel;

import android.animation.LayoutTransition;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class Settings extends Fragment {
    LinearLayout layout;
    RadioGroup btnGroup;
    RadioButton boy, girl;
    CardView card;
    ImageView image;
    ChangeAvatar change;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View reb = inflater.inflate(R.layout.rebel, container, false);
        View view = inflater.inflate(R.layout.settings, container, false);
        boy = view.findViewById(R.id.boy);
        girl = view.findViewById(R.id.girl);
        layout = view.findViewById(R.id.setting_layout);
        btnGroup = view.findViewById(R.id.avatar_settings);
        layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        card = view.findViewById(R.id.card_view);
        image = reb.findViewById(R.id.avatar);
        change = new ViewModelProvider(requireActivity()).get(ChangeAvatar.class);


        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expand(view);
            }
        });

        btnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                onRadioButtonClicked(view.findViewById(i));
            }
        });

        return view;
    }
    public void expand(View view) {
        int v =(btnGroup.getVisibility() ==View.GONE)? View.VISIBLE: View.GONE;

        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
        btnGroup.setVisibility(v);
    }
    public void onRadioButtonClicked(View view) {
        Log.d("RadioButton", "RadioButton clicked: " + view.getId());
        boolean checked = ((RadioButton) view).isChecked();

        if (view.getId() == R.id.boy) {
            if (checked) {
                change.setSelectedImageResourceId(R.drawable.artist);
            }
        } else if(view.getId() == R.id.girl) {

            if (checked) {
                Log.d("RadioButton", "Setting image to girl");
                change.setSelectedImageResourceId(R.drawable.girl);
            }
        }

        }
    }




