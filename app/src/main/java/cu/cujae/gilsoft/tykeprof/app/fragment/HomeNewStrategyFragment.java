package cu.cujae.gilsoft.tykeprof.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.shuhart.stepview.StepView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.HomeNewStrategyViewModel;
import cu.cujae.gilsoft.tykeprof.data.entity.Group;
import cu.cujae.gilsoft.tykeprof.data.entity.Question;
import cu.cujae.gilsoft.tykeprof.data.entity.Topic;
import cu.cujae.gilsoft.tykeprof.data.model.Strategy_Model;
import cu.cujae.gilsoft.tykeprof.databinding.HomeNewStrategyFragmentBinding;

public class HomeNewStrategyFragment extends Fragment {

    public static HomeNewStrategyViewModel homeNewStrategyViewModel;
    public static StepView stepViewNewStrategy;
    private HomeNewStrategyFragmentBinding binding;
    public static Strategy_Model strategy_model;
    public static ArrayList<Topic> topicList;
    public static ArrayList<Group> groupList;
    public static ArrayList<Question> questionList;
    private boolean themeDark;

    public static HomeNewStrategyFragment newInstance() {
        return new HomeNewStrategyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomeNewStrategyFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        strategy_model = new Strategy_Model();
        topicList = new ArrayList<>();
        groupList = new ArrayList<>();
        questionList = new ArrayList<>();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeNewStrategyViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(HomeNewStrategyViewModel.class);
        homeNewStrategyViewModel.getTeacherSubjectsModel(binding.getRoot());
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStepper();
    }

    // INICIAR CONFIGURACIÓN DEL STEPPER
    public void initStepper() {
        stepViewNewStrategy = binding.stepViewNewStrategy;
        stepViewNewStrategy.getState()
                .steps(new ArrayList<String>() {{
                    add(getString(R.string.strategy_data));
                    add(getString(R.string.build_strategy));
                    add(getString(R.string.add_questions));
                    add(getString(R.string.summary));
                }})
                .animationType(StepView.ANIMATION_ALL)
                .animationDuration(getResources().getInteger(android.R.integer.config_longAnimTime))
                .commit();

        themeDark = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("DarkTheme", false);
        if (themeDark) {
            stepViewNewStrategy.getState().
                    selectedTextColor(ContextCompat.getColor(getContext(), R.color.white))
                    .doneTextColor(ContextCompat.getColor(getContext(), R.color.white))
                    .commit();
        }
    }
}