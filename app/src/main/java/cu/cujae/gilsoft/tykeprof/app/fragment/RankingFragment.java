package cu.cujae.gilsoft.tykeprof.app.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.adapter.ViewPager_Adapter;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.RankingViewModel;
import cu.cujae.gilsoft.tykeprof.databinding.RankingFragmentBinding;

public class RankingFragment extends Fragment {

    public static RankingViewModel rankingViewModel;
    private RankingFragmentBinding binding;
    private AppBarLayout appBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    public static RankingFragment newInstance() {
        return new RankingFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rankingViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(RankingViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RankingFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (savedInstanceState == null) {
            configTabLayout(container);
            viewPager = binding.viewPagerRanking;
            configViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_chart);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_people);
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @SuppressLint("ResourceAsColor")
    private void configTabLayout(ViewGroup container) {
        View parent = (View) container.getParent();
        appBar = (AppBarLayout) parent.findViewById(R.id.appBarLayoutMainActivity);
        View view1 = getLayoutInflater().inflate(R.layout.ranking_tablayout, null);
        tabLayout = (TabLayout) view1;
        // tabLayout.setBackgroundColor(Color.parseColor("#3F9655"));
        //tabLayout.setTabRippleColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        appBar.addView(tabLayout);
    }

    private void configViewPager(ViewPager viewPager) {
        ViewPager_Adapter adapter = new ViewPager_Adapter(getChildFragmentManager());
        adapter.addFragment(new RankingChartFragment(), getString(R.string.ranking_chart));
        adapter.addFragment(new RankingListFragment(), getString(R.string.ranking_list));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        appBar.removeView(tabLayout);
        binding = null;
    }
}