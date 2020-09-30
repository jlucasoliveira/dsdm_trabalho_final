package br.ufc.quixada.dsdm.meempresta;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import br.ufc.quixada.dsdm.meempresta.Auth.LoginActivity;
import br.ufc.quixada.dsdm.meempresta.fragments.ChatFragment;
import br.ufc.quixada.dsdm.meempresta.fragments.FeedFragment;
import br.ufc.quixada.dsdm.meempresta.fragments.RecordFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final ViewHolder mViewHolder = new ViewHolder();
    protected List<Integer> mTabIcons = new ArrayList<>();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

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

        mViewHolder.viewWrapperMain = findViewById(R.id.view_wrapper_main);
        mViewHolder.tabMainView     = findViewById(R.id.tabs_main_view);

        configTabs();
    }

    private void configTabs() {
        TabAdapter tabAdapter = new TabAdapter(this);

        tabAdapter.add(new FeedFragment(), R.drawable.ic_outline_home_24);
        tabAdapter.add(new ChatFragment(), R.drawable.ic_request);
        tabAdapter.add(new RecordFragment(), R.drawable.ic_record);

        mViewHolder.viewWrapperMain.setAdapter(tabAdapter);

        new TabLayoutMediator(
            mViewHolder.tabMainView, this.mViewHolder.viewWrapperMain,
            (tab, position) -> tab.setIcon(mTabIcons.get(position))
        ).attach();
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
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private static class ViewHolder {
        ViewPager2 viewWrapperMain;
        TabLayout tabMainView;
    }

    private class TabAdapter extends FragmentStateAdapter {

        private List<Fragment> tabs = new ArrayList<>();

        public TabAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        public void add(Fragment fragment, Integer iconResourceId){
            this.tabs.add(fragment);
            mTabIcons.add(iconResourceId);
        }
        
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return tabs.get(position);
        }

        @Override
        public int getItemCount() {
            return tabs.size();
        }

    }
}