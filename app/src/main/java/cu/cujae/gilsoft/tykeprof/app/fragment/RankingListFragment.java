package cu.cujae.gilsoft.tykeprof.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cu.cujae.gilsoft.tykeprof.adapter.Ranking_Adapter;
import cu.cujae.gilsoft.tykeprof.databinding.RankingListFragmentBinding;


public class RankingListFragment extends Fragment {

    private RankingListFragmentBinding binding;

    public RankingListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = RankingListFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = binding.recyclerViewRanking;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Ranking_Adapter adapter = new Ranking_Adapter(new Ranking_Adapter.PunctuationDiff());
        recyclerView.setAdapter(adapter);
        RankingFragment.rankingViewModel.getAllPunctuations().observe(getParentFragment().getViewLifecycleOwner(), punctuationList -> {
            adapter.submitList(punctuationList);
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}