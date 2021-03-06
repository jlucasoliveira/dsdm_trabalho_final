package br.ufc.quixada.dsdm.meempresta.Fragments;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import br.ufc.quixada.dsdm.meempresta.R;
import br.ufc.quixada.dsdm.meempresta.Adapters.RecordListAdapter;
import br.ufc.quixada.dsdm.meempresta.Models.Request;
import br.ufc.quixada.dsdm.meempresta.utils.Constants;
import br.ufc.quixada.dsdm.meempresta.utils.DBCollections;
import br.ufc.quixada.dsdm.meempresta.utils.OfflineUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecordFragment extends Fragment {

    ProgressBar mPbRecord;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mRefresh;

    private OfflineUser offlineUser;
    private FirebaseFirestore mFirestore;

    private RecordListAdapter mRecordAdapter;
    private final List<Request> mRecords = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);

        mPbRecord = view.findViewById(R.id.pb_record);
        mRefresh = view.findViewById(R.id.record_refresh);
        mRecyclerView = view.findViewById(R.id.record_list);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        offlineUser = new OfflineUser(getContext());
        mFirestore = FirebaseFirestore.getInstance();
        mRefresh.setOnRefreshListener(this::loadRecords);

        loadRecords();

        return view;
    }

    public void loadRecords() {
        String uid = offlineUser.getString(Constants.USER_ID);
        mFirestore.collection(DBCollections.REQUEST_COLLECTION)
        .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mRecords.clear();
                for (Request r :task.getResult().toObjects(Request.class))
                    if (r.getOwner().equals(uid) || r.getResolver().equals(uid))
                        mRecords.add(r);
                mRecordAdapter = new RecordListAdapter(getContext(), mRecords);
                mRecyclerView.setAdapter(mRecordAdapter);
                mPbRecord.setVisibility(View.INVISIBLE);
                mRefresh.setRefreshing(false);
            }
            else {
                Toast.makeText(getContext(), "Ocorreu um erro", Toast.LENGTH_SHORT).show();
            }
        });
    }

}