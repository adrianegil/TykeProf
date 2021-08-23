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

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.ProfessionalRolViewModel;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.QuestionViewModel;
import cu.cujae.gilsoft.tykeprof.databinding.FragmentHomeBinding;
import cu.cujae.gilsoft.tykeprof.repository.Strategy_Repository;
import cu.cujae.gilsoft.tykeprof.util.ToastHelper;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;

import static android.content.Context.MODE_PRIVATE;


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
                    // Toast.makeText(getContext(), getString(R.string.exit_again_toast), Toast.LENGTH_SHORT).show();
                    ToastHelper.showCustomToast(getActivity(), "warning", getString(R.string.exit_again_toast));
                    cont++;
                } else {
                    getActivity().finish();
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

        binding.materialButtonGotoClueTypeFragment.setOnClickListener(v -> {
            navController.navigate(R.id.go_ClueTypeFragment);
        });

        binding.materialButtonGotoQuestionTypeFragment.setOnClickListener(v -> {
            navController.navigate(R.id.go_QuestionTypeFragment);
        });

        //COMPROBANDO SI ES LA PRIMERA VEZ QUE EL USUARIO ENTRA EN LA APP
        if (getActivity().getSharedPreferences("autenticacion", MODE_PRIVATE).getBoolean("firstLaunch", true)) {
            ToastHelper.showCustomToast(getActivity(), "success", getString(R.string.success_aut));
            ProfessionalRolViewModel professionalRolViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(ProfessionalRolViewModel.class);
            UserHelper.changefirstLaunch(getContext());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}