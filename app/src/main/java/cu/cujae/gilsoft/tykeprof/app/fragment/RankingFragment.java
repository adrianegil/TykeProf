package cu.cujae.gilsoft.tykeprof.app.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.RankingViewModel;
import cu.cujae.gilsoft.tykeprof.databinding.InsigniaFragmentBinding;
import cu.cujae.gilsoft.tykeprof.databinding.RankingFragmentBinding;

public class RankingFragment extends Fragment {

    private RankingViewModel rankingViewModel;
    private RankingFragmentBinding binding;

    public static RankingFragment newInstance() {
        return new RankingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RankingFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rankingViewModel = new ViewModelProvider(this).get(RankingViewModel.class);
        // TODO: Use the ViewModel
    }

}