package br.ufc.quixada.dsdm.meempresta;

import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mEditName, mEditPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mEditName = findViewById(R.id.edit_username);
        mEditPassword = findViewById(R.id.edit_password);

        mEditName.setText("");
    }

    public void onClick(View v) {
        finish();
    }

}