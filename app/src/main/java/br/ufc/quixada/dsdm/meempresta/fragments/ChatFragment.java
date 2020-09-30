package br.ufc.quixada.dsdm.meempresta.fragments;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import br.ufc.quixada.dsdm.meempresta.R;
import br.ufc.quixada.dsdm.meempresta.adapters.ChatListAdapter;

public class ChatFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        mRecyclerView = view.findViewById(R.id.chats_list);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new ChatListAdapter());

        return view;
    }

}