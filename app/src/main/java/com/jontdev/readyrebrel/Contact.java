package com.jontdev.readyrebrel;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class Contact extends Fragment {
    private EditText editTextEmail, editTextPhone, editTextDescription;
    private Button buttonSubmit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.contact, container, false);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPhone = view.findViewById(R.id.editTextPhone);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        return view;
    }
    private void submitForm() {
        String email = editTextEmail.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        String toastMessage = "Email: " + email + "\nPhone: " + phone + "\nDescription: " + description;
        Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }



}
