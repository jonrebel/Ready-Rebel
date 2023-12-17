package com.jontdev.readyrebrel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.myView> {
    List<MessageModel> messages;

    public MessageAdapter(List<MessageModel> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View messageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages, null);
      myView view = new myView(messageView);
      return view;
    }

    @Override
    public void onBindViewHolder(@NonNull myView holder, int position) {
        MessageModel message = messages.get(position);
        if(message.getSentBy().equals(MessageModel.SENT_BY_ME)){
            holder.rebResponse.setVisibility(View.GONE);
            holder.humResponse.setVisibility(View.VISIBLE);
            holder.humMessage.setText(message.getMessage());
        }else {
            holder.humResponse.setVisibility(View.GONE);
            holder.rebResponse.setVisibility(View.VISIBLE);
            holder.rebMessage.setText(message.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class myView extends RecyclerView.ViewHolder{
        LinearLayout humResponse, rebResponse;
        TextView humMessage, rebMessage;

        public myView(@NonNull View itemView) {
            super(itemView);
            humResponse = itemView.findViewById(R.id.hum_response);
            humMessage = itemView.findViewById(R.id.hum_message);
            rebResponse = itemView.findViewById(R.id.reb_response);
            rebMessage = itemView.findViewById(R.id.reb_message);
        }
    }
}
