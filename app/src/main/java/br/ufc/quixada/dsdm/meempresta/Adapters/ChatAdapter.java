package br.ufc.quixada.dsdm.meempresta.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.ufc.quixada.dsdm.meempresta.Models.Chat;
import br.ufc.quixada.dsdm.meempresta.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ChatAdapter  extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private final static int MSG_TYPE_RIGHT = 0;
    private final static int MSG_TYPE_LEFT = 1;

    private List<Chat> mMessages;

    public ChatAdapter(List<Chat> messages) {
        this.mMessages = messages;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MSG_TYPE_LEFT)
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_left, parent, false);
        else view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_right, parent, false);
        return new ChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        holder.mTxtMessage.setText(mMessages.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (mMessages.get(position).getSender().equals(user.getUid()))
            return MSG_TYPE_RIGHT;
        return MSG_TYPE_LEFT;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTxtMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtMessage = itemView.findViewById(R.id.message_text);
        }
    }
}
