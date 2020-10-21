package br.ufc.quixada.dsdm.meempresta.Auth;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.ufc.quixada.dsdm.meempresta.MainActivity;
import br.ufc.quixada.dsdm.meempresta.Models.User;
import br.ufc.quixada.dsdm.meempresta.R;
import br.ufc.quixada.dsdm.meempresta.utils.Constants;
import br.ufc.quixada.dsdm.meempresta.utils.DBCollections;
import br.ufc.quixada.dsdm.meempresta.utils.OfflineUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    OfflineUser offlineUser;

    Button mBtnLogin, mBtnSignUp;
    EditText mEditEmail, mEditPassword;
    ProgressBar mProgressBarLogin;

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        mBtnLogin = findViewById(R.id.btn_login);
        mBtnSignUp = findViewById(R.id.btn_login_signup);
        mEditEmail = findViewById(R.id.edit_login_email);
        mEditPassword = findViewById(R.id.edit_login_password);
        mProgressBarLogin = findViewById(R.id.progressBar_login);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        offlineUser = new OfflineUser(this);

        mBtnLogin.setOnClickListener(this::onClickLogin);
        mBtnSignUp.setOnClickListener(this::onClickSignUp);
    }

    public void onClickLogin(View view) {
        String email = mEditEmail.getText().toString().trim();
        String password = mEditPassword.getText().toString().trim();

        if (email.isEmpty()){
            mEditEmail.setError("Insira um email válido");
            return;
        }

        if (password.isEmpty()){
            mEditPassword.setError("Senha é obrigatória.");
            return;
        }

        if (password.length() < 6) {
            mEditPassword.setError("A senha deve ter no mínimo 6 caracteres.");
            return;
        }

        mProgressBarLogin.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            mProgressBarLogin.setVisibility(View.INVISIBLE);
            if (task.isSuccessful()) {
                mFirestore.collection(DBCollections.USER_COLLECTION).whereEqualTo("email", email)
                .get().addOnCompleteListener(user -> {
                    String uid = user.getResult().toObjects(User.class).get(0).getId();
                    offlineUser.storeString(Constants.USER_ID, uid);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                });
            }
            else {
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onClickSignUp(View view) {
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
    }
}