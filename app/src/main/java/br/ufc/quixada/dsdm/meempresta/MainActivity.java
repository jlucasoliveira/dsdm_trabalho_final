package br.ufc.quixada.dsdm.meempresta;

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

        this.mViewHolder.viewWrapperMain.setAdapter(tabAdapter);

        new TabLayoutMediator(
            this.mViewHolder.tabMainView, this.mViewHolder.viewWrapperMain,
            (tab, position) -> tab.setIcon(tabAdapter.getTabIcons().get(position))
        ).attach();
    }

    private static class ViewHolder {
        ViewPager2 viewWrapperMain;
        TabLayout tabMainView;
    }

    private class TabAdapter extends FragmentStateAdapter {

        private List<Fragment> tabs = new ArrayList<>();
        private List<Integer> tabIcons = new ArrayList<>();

        public TabAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        public void add(Fragment fragment, int icon){
            this.tabs.add(fragment);
            this.tabIcons.add(icon);
        }

        public List<Integer> getTabIcons() {
            return tabIcons;
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