package cu.cujae.gilsoft.tykeprof.app.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.QuestionViewModel;
import cu.cujae.gilsoft.tykeprof.databinding.FragmentHomeBinding;
import cu.cujae.gilsoft.tykeprof.repository.Strategy_Repository;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private QuestionViewModel questionViewModel;
    private Strategy_Repository strategy_repository;
    private NavController navController;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        strategy_repository = new Strategy_Repository(getActivity().getApplication());
        strategy_repository.getAllStrategiesList();

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            int cont;
            @Override
            public void handleOnBackPressed() {
                if (cont == 0) {
                    Snackbar.make(binding.getRoot(), getResources().getString(R.string.exit_again_toast), Snackbar.LENGTH_SHORT).show();
                    cont++;
                } else {
                    getActivity().finishAffinity();
                }
                new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        cont = 0;
                    }
                }.start();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        questionViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(QuestionViewModel.class);
        questionViewModel.getAllQuestion().observe(getViewLifecycleOwner(), questionList -> {
            binding.textViewCantsQuestions.setText(getString(R.string.question_cant) + " " + questionList.size());
            binding.textViewCantsStrategies.setText(getString(R.string.strategy_cant) + " " + strategy_repository.getStrategiesListSize());
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_container);

        binding.materialButtonGotoQuestionFragment.setOnClickListener(v -> {
            //Navigation.createNavigateOnClickListener(R.id.go_ClueTypeFragment,null);
            // navController.navigate(R.id.nav_clueTypeFragment, null);
            navController.navigate(R.id.go_QuestionFragmentFromHome);
        });
        binding.materialGotoStrategyFragment.setOnClickListener(v -> {
            navController.navigate(R.id.go_StrategyFragmentFromHome);
        });
        binding.materialButtonGotoClueTypeFragment.setOnClickListener(v -> {
            navController.navigate(R.id.go_ClueTypeFragment);
        });
        binding.materialButtonGotoQuestionTypeFragment.setOnClickListener(v -> {
            navController.navigate(R.id.go_QuestionTypeFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}