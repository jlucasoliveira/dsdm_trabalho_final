package br.ufc.quixada.dsdm.meempresta.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends FragmentStateAdapter {

    private List<Fragment> tabs = new ArrayList<>();

    public TabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void add(Fragment fragment){
        this.tabs.add(fragment);
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
