package br.ufc.quixada.dsdm.meempresta;

import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private final ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mViewHolder.editname = findViewById(R.id.edit_username);
        mViewHolder.editPassword = findViewById(R.id.edit_password);

        mViewHolder.editname.setText("");
    }

    public void onClick( View v ) {
        finish();
    }

    private static class ViewHolder {
        EditText editname;
        EditText editPassword;
    }
}