package br.ufc.quixada.dsdm.meempresta.Auth;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.ufc.quixada.dsdm.meempresta.MainActivity;
import br.ufc.quixada.dsdm.meempresta.Models.User;
import br.ufc.quixada.dsdm.meempresta.R;
import br.ufc.quixada.dsdm.meempresta.utils.DBCollections;
import br.ufc.quixada.dsdm.meempresta.utils.ToastMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;

    Button mBtnSignUp;
    EditText mEditName, mEditEmail, mEditPass, mEditConfirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mBtnSignUp = findViewById(R.id.btn_sign_up);
        mEditName = findViewById(R.id.edit_sign_up_name);
        mEditEmail = findViewById(R.id.edit_sign_up_email);
        mEditPass = findViewById(R.id.edit_sign_up_password);
        mEditConfirmPass = findViewById(R.id.edit_sign_up_confirm_password);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        mBtnSignUp.setOnClickListener(this::onClickSigUp);
    }

    public void onClickSigUp(View view) {
        String name = mEditName.getText().toString().trim();
        String email = mEditEmail.getText().toString().trim();
        String pass = mEditPass.getText().toString().trim();
        String confirmPass = mEditConfirmPass.getText().toString().trim();

        if (name.isEmpty()) {
            mEditName.setError("O nome é obrigatório!");
            return;
        }

        if (email.isEmpty()) {
            mEditEmail.setError("Insira um email válido!");
            return;
        }

        if (pass.isEmpty()) {
            mEditPass.setError("A senha é obrigatória!");
            return;
        }

        if (pass.length() < 6) {
            mEditPass.setError("A senha dever ter no mínimo 6 caracteres.");
            return;
        }

        if (!pass.equals(confirmPass)) {
            mEditConfirmPass.setError("As senhas são diferentes!");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mFirestore.collection(DBCollections.USER_COLLECTION).add(new User(null, name, email, 0.0));
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
            else
                ToastMessage.showMessage(this, task.getException().getMessage());
        });

    }
}