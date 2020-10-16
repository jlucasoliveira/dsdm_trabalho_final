package br.ufc.quixada.dsdm.meempresta.Fragments;

import android.os.Bundle;
import android.widget.ProgressBar;
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
import br.ufc.quixada.dsdm.meempresta.utils.DBCollections;
import br.ufc.quixada.dsdm.meempresta.utils.ToastMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RecordFragment extends Fragment {

    ProgressBar mPbRecord;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mRefresh;

    private FirebaseUser mUser;
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

        mFirestore = FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        loadRecords();

        mRefresh.setOnRefreshListener(this::loadRecords);
        return view;
    }

    public void loadRecords() {
        mFirestore.collection(DBCollections.REQUEST_COLLECTION).whereEqualTo("owner", mUser.getUid())
        .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mRecords.clear();
                mRecords.addAll(task.getResult().toObjects(Request.class));
                mRecordAdapter = new RecordListAdapter(getContext(), mRecords);
                mRecyclerView.setAdapter(mRecordAdapter);
                mPbRecord.setVisibility(View.INVISIBLE);
                mRefresh.setRefreshing(false);
            }
            else {
                ToastMessage.showMessage(getContext(), "Ocorreu um erro");
            }
        });
    }

}