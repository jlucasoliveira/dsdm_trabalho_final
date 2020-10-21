package br.ufc.quixada.dsdm.meempresta.Auth;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.ufc.quixada.dsdm.meempresta.MainActivity;
import br.ufc.quixada.dsdm.meempresta.Models.User;
import br.ufc.quixada.dsdm.meempresta.R;
import br.ufc.quixada.dsdm.meempresta.utils.Constants;
import br.ufc.quixada.dsdm.meempresta.utils.DBCollections;
import br.ufc.quixada.dsdm.meempresta.utils.OfflineUser;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.List;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;

    Button mBtnSignUp;
    EditText mEditName, mEditEmail, mEditPass, mEditConfirmPass, mEditAddress;

    OfflineUser offlineUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mBtnSignUp = findViewById(R.id.btn_sign_up);
        mEditName = findViewById(R.id.edit_sign_up_name);
        mEditEmail = findViewById(R.id.edit_sign_up_email);
        mEditPass = findViewById(R.id.edit_sign_up_password);
        mEditAddress = findViewById(R.id.edit_sign_up_address);
        mEditConfirmPass = findViewById(R.id.edit_sign_up_confirm_password);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        offlineUser = new OfflineUser(this);

        mBtnSignUp.setOnClickListener(this::onClickSignUp);
    }

    public void onClickSignUp(View view) {
        String name = mEditName.getText().toString().trim();
        String email = mEditEmail.getText().toString().trim();
        String pass = mEditPass.getText().toString();
        String confirmPass = mEditConfirmPass.getText().toString();
        String locationName = mEditAddress.getText().toString();

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

        if (locationName.isEmpty()) {
            mEditAddress.setError("Informe um endereço!");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                try {
                    List<Address> address = new Geocoder(this).getFromLocationName(locationName, 1);
                    if (address == null) {
                        Toast.makeText(this, "Algo deu errado!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    GeoPoint local  = new GeoPoint(address.get(0).getLatitude(), address.get(0).getLongitude());
                    mFirestore.collection(DBCollections.USER_COLLECTION)
                            .add(new User(null, name, email, null, local))
                            .addOnCompleteListener(user -> {
                                offlineUser.storeString(Constants.USER_ID, user.getResult().getId());
                            });
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
                catch (Exception e) {
                    if (e instanceof IOException)
                        Toast.makeText(this, "Serviço indisponível", Toast.LENGTH_SHORT).show();
                    else if (e instanceof IllegalArgumentException)
                        Toast.makeText(this, "Localização inválida", Toast.LENGTH_SHORT).show();
                }
            }
            else
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
        });

    }
}