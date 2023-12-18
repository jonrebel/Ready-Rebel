package com.jontdev.readyrebrel;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Rebel extends Fragment {
    List<MessageModel> messages;
    MessageAdapter adapter;
    RecyclerView recyclerView;
    EditText userInput;
    ImageButton send;
    ImageView image;
    ChangeAvatar change;
    public static final MediaType JSON = MediaType.get("application/json");

    OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .build();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.rebel, container, false);

        change = new ViewModelProvider(requireActivity()).get(ChangeAvatar.class);
        image = view.findViewById(R.id.avatar);
        recyclerView = view.findViewById(R.id.response);
        userInput = view.findViewById(R.id.input);
        send = view.findViewById(R.id.send);
        messages = new ArrayList<>();
        adapter = new MessageAdapter(messages);

        recyclerView.setAdapter(adapter);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setStackFromEnd(true);
        recyclerView.setLayoutManager(lm);

        image.setImageResource(change.getSelectedImageResourceId());

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = userInput.getText().toString().trim();
                addToMessages(input,MessageModel.SENT_BY_ME);
                hideKeyboard();
                try {
                    callAPI(input);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                userInput.setText("");
            }
        });

        userInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int key, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (key == KeyEvent.KEYCODE_ENTER)) {
                    String input = userInput.getText().toString().trim();
                    addToMessages(input,MessageModel.SENT_BY_ME);
                    hideKeyboard();
                    try {
                        callAPI(input);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    userInput.setText("");

                    return true;
                }
                return false;
            }
        });

        return view;
    }
    public void addToMessages(String message, String sentBy){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messages.add(new MessageModel(message,sentBy));
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        });

    }
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(userInput.getWindowToken(), 0);
        }
    }

    public void addResponse(String response){
        messages.remove(messages.size()-1);
        addToMessages(response,MessageModel.SENT_BY_REB);
    }
    public void callAPI(String input) throws JSONException {
        messages.add(new MessageModel("Typing...",MessageModel.SENT_BY_REB));
        JSONObject body = new JSONObject();
        try {
            body.put("model", "gpt-3.5-turbo");
            JSONArray messArr = new JSONArray();
            JSONObject mess = new JSONObject();
            mess.put("role", "user");
            mess.put("content", input);
            messArr.put(mess);
            body.put("messages",messArr);
        } catch (JSONException e){
            addResponse("Failure" + e.getMessage());
        }


        RequestBody rb = RequestBody.create(body.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer sk-uDyq8assKmQVfjh04kFXQs7Z")
                .post(rb)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    try {
                        JSONObject resObject = new JSONObject(response.body().string());
                        JSONArray arr = resObject.getJSONArray("choices");
                        String result = arr.getJSONObject(0)
                                .getJSONObject("message")
                                 .getString("content");
                        addResponse(result.trim());

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    addResponse("Failed to get response " + response.body().string());
                }
            }
        });
    }
}





