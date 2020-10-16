package br.ufc.quixada.dsdm.meempresta;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import br.ufc.quixada.dsdm.meempresta.Adapters.TabAdapter;
import br.ufc.quixada.dsdm.meempresta.Auth.LoginActivity;
import br.ufc.quixada.dsdm.meempresta.Fragments.FeedFragment;
import br.ufc.quixada.dsdm.meempresta.Fragments.RecordFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager2 mViewPager;
    TabLayout mTabLayout;
    TabAdapter tabAdapter;

    private final List<Integer> mTabIcons = new ArrayList<>(2);
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.view_wrapper_main);
        mTabLayout = findViewById(R.id.tabs_main_view);

        configTabs();
    }

    private void configTabs() {
        tabAdapter = new TabAdapter(this);

        addTab(new FeedFragment(), R.drawable.ic_outline_home_24);
        addTab(new RecordFragment(), R.drawable.ic_record);

        mViewPager.setAdapter(tabAdapter);

        new TabLayoutMediator(
            mTabLayout, mViewPager,
            (tab, position) -> tab.setIcon(mTabIcons.get(position))
        ).attach();
    }

    private void addTab(Fragment tab, int icon) {
        tabAdapter.add(tab);
        mTabIcons.add(icon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Integer itemId = item.getItemId();
        if (itemId.equals(R.id.action_bar_edit_profile))
            startActivity(new Intent(this, EditProfileActivity.class));
        else if (itemId.equals(R.id.action_bar_share_app)) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Recomendo que não compartilhe o app!!!");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, "Compartilhar app");
            startActivity(shareIntent);
        }
        else if (itemId.equals(R.id.action_bar_about_app))
            startActivity(new Intent(this, AboutAppActivity.class));
        else if (itemId.equals(R.id.action_bar_logoff)){
            mAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}