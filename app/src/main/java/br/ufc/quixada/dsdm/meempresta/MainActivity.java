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
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final ViewHolder mViewHolder = new ViewHolder();
    protected List<Integer> mTabIcons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mViewHolder.viewWrapperMain = findViewById(R.id.view_wrapper_main);
        this.mViewHolder.tabMainView     = findViewById(R.id.tabs_main_view);

        configTabs();
    }

    private void configTabs() {
        TabAdapter tabAdapter = new TabAdapter(this);

        tabAdapter.add(new FeedFragment(), R.drawable.ic_outline_home_24);
        tabAdapter.add(new ChatFragment(), R.drawable.ic_request);
        tabAdapter.add(new RecordFragment(), R.drawable.ic_record);

        this.mViewHolder.viewWrapperMain.setAdapter(tabAdapter);

        new TabLayoutMediator(
            this.mViewHolder.tabMainView, this.mViewHolder.viewWrapperMain,
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
        if (item.getItemId() == R.id.action_bar_setting) {
            Intent intentSettingActivity = new Intent(this, SettingsActivity.class);
            startActivity(intentSettingActivity);
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