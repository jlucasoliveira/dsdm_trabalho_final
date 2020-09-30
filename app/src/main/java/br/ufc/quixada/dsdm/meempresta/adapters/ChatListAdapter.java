package br.ufc.quixada.dsdm.meempresta.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.ufc.quixada.dsdm.meempresta.R;
import br.ufc.quixada.dsdm.meempresta.models.Chat;

import java.util.List;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    // ~~TEMP~~
    static List<Chat> chats = Chat.getChats();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_contact_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(chats.get(position));
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtUsername;
        TextView txtLastMessage;
        ImageView imgProfilePic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUsername = itemView.findViewById(R.id.txt_account_name);
            txtLastMessage = itemView.findViewById(R.id.txt_last_message);
            imgProfilePic = itemView.findViewById(R.id.img_profile_pic);
        }

        public void bind(Chat chat) {
            txtUsername.setText(chat.getUsuarioName());
            txtLastMessage.setText(chat.getLastMessage());
        }
    }
}
