package cu.cujae.gilsoft.tykeprof.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.adapter.Strategy_Adapter;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.StrategyViewModel;
import cu.cujae.gilsoft.tykeprof.databinding.StrategyFragmentBinding;

public class StrategyFragment extends Fragment {

    private StrategyViewModel strategyViewModel;
    private StrategyFragmentBinding binding;
    private NavController navController;

    public static StrategyFragment newInstance() {
        return new StrategyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = StrategyFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        strategyViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(StrategyViewModel.class);
        RecyclerView recyclerView = binding.RecyclerViewStrategy;
        Strategy_Adapter adapter = new Strategy_Adapter(new Strategy_Adapter.StrategyDiff(), strategyViewModel, getActivity(), getActivity().getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        strategyViewModel.getAllStrategies().observe(getViewLifecycleOwner(), strategyList -> {
            adapter.submitList(strategyList);
        });

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_container);
        binding.materialButtonAddStrategy.setOnClickListener(v -> {
            navController.navigate(R.id.go_newStrategy);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}