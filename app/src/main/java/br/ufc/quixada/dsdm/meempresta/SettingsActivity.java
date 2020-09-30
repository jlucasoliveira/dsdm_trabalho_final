package br.ufc.quixada.dsdm.meempresta;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mViewHolder.txtEditProfile = findViewById(R.id.txt_profile_edit);
        mViewHolder.txtShareApp    = findViewById(R.id.txt_share_app);
        mViewHolder.txtAboutApp    = findViewById(R.id.txt_about);
        mViewHolder.txtLogOff      = findViewById(R.id.txt_exit);

        mViewHolder.txtEditProfile.setOnClickListener(this::onClickEditProfile);
        mViewHolder.txtShareApp.setOnClickListener(this::onClickShareApp);
        mViewHolder.txtAboutApp.setOnClickListener(this::onClickAboutApp);
        mViewHolder.txtLogOff.setOnClickListener(this::onClickLogOff);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onClickEditProfile(View v) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    public void onClickShareApp(View v) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Recomendo que não compartilhe o app!!!");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, "Compartilhar app");
        startActivity(shareIntent);
    }

    public void onClickAboutApp(View v) {
        Intent intent = new Intent(this, AboutAppActivity.class);
        startActivity(intent);
    }

    public void onClickLogOff(View v) {
        mAuth.signOut();
        finish();
    }

    private static class ViewHolder {
        TextView txtEditProfile;
        TextView txtShareApp;
        TextView txtAboutApp;
        TextView txtLogOff;
    }
}