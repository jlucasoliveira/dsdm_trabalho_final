package br.ufc.quixada.dsdm.meempresta;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.ufc.quixada.dsdm.meempresta.Adapters.ChatAdapter;
import br.ufc.quixada.dsdm.meempresta.Models.Chat;
import br.ufc.quixada.dsdm.meempresta.Models.enums.RequestStatus;
import br.ufc.quixada.dsdm.meempresta.utils.DBCollections;
import br.ufc.quixada.dsdm.meempresta.utils.ToastMessage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DealActivity extends AppCompatActivity {

    EditText mInputMessage;
    RecyclerView mRecyclerView;
    FloatingActionButton mFaSendMessage;
    ProgressBar mPbDealChat;
    TextView mTxtTitle, mTxtDescription, mTxtLoanDate;

    List<Chat> messages = new ArrayList<>();
    ChatAdapter chatAdapter;
    FirebaseFirestore mFirestore;
    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal);

        mPbDealChat = findViewById(R.id.pb_deal);
        mRecyclerView = findViewById(R.id.rv_chat);
        mTxtTitle = findViewById(R.id.txt_title_deal);
        mTxtLoanDate = findViewById(R.id.loan_date_deal);
        mFaSendMessage = findViewById(R.id.fa_message_send);
        mInputMessage = findViewById(R.id.edit_message_input);
        mTxtDescription = findViewById(R.id.txt_description_deal);

        mFirestore = FirebaseFirestore.getInstance();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFaSendMessage.setOnClickListener(this::sendMessage);

        changeUI();
        loadChat();
    }

    public void changeUI() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            mTxtTitle.setText(bundle.getString("r_title"));
            mTxtDescription.setText(bundle.getString("r_description"));
            mTxtLoanDate.setText(
                    new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm").format(
                            ((Timestamp) bundle.get("r_date")).toDate())
            );
        }
        else finish();
    }

    private void loadChat() {
        String requestId = getIntent().getExtras().getString("r_id");
        mFirestore.collection(DBCollections.CHAT_COLLECTION).whereEqualTo("request", requestId)
        .addSnapshotListener((value, error) -> {
            mPbDealChat.setVisibility(View.INVISIBLE);
            if (error != null){
                ToastMessage.showMessage(this, "Ocorreu um erro ao carregar o chat! Tente mais tarde.");
                return;
            }

            assert value != null;
            messages.clear();
            messages.addAll(value.toObjects(Chat.class));
            chatAdapter = new ChatAdapter(messages);
            mRecyclerView.setAdapter(chatAdapter);
        });
    }

    public void sendMessage(View view) {
        String requestId = getIntent().getExtras().getString("r_id");
        String resolver = getIntent().getExtras().getString("r_resolver");
        String message = mInputMessage.getText().toString().trim();

        if (message.isEmpty()) {
            mInputMessage.setError("Digite algo");
            return;
        }

        mFirestore.collection(DBCollections.CHAT_COLLECTION)
                .add(new Chat(null, mUser.getUid(), resolver, message, requestId, Timestamp.now()));
        mInputMessage.setText("");
    }

    public void alertDialogOptions(Integer RString, RequestStatus status, boolean doRemove) {
        String requestId = getIntent().getExtras().getString("r_id");
        ToastMessage.showMessage(this, requestId);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(RString)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    if (!doRemove)
                        mFirestore.collection(DBCollections.REQUEST_COLLECTION)
                                .document(requestId).update("status", status.getCode());
                    else
                        mFirestore.collection(DBCollections.REQUEST_COLLECTION).document(requestId).delete();
                    finish();
                })
                .setNegativeButton(R.string.no, null);
        builder.create();
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_deal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Integer itemId = item.getItemId();
        if (itemId.equals(R.id.cancel_request))
            alertDialogOptions(R.string.do_wanna_cancel, RequestStatus.CANCELED, false);
        else if (itemId.equals(R.id.done_request))
            alertDialogOptions(R.string.do_wanna_done, RequestStatus.DONE, false);
        else if (itemId.equals(R.id.delete_request))
            alertDialogOptions(R.string.do_wanna_remove, null, true);
        return super.onOptionsItemSelected(item);
    }

}