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

import java.util.ArrayList;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.HomeNewQuestionViewModel;
import cu.cujae.gilsoft.tykeprof.data.model.Question_Model;
import cu.cujae.gilsoft.tykeprof.databinding.FragmentHomeNewQuestionBinding;

public class HomeNewQuestionFragment extends Fragment {

    public static StepView stepViewNewQuestion;
    private FragmentHomeNewQuestionBinding binding;
    public static Question_Model questionModel;
    public static HomeNewQuestionViewModel homeNewQuestionViewModel;
    private boolean themeDark;

    public HomeNewQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionModel = new Question_Model();
        homeNewQuestionViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(HomeNewQuestionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeNewQuestionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStepper();
    }

    // INICIAR CONFIGURACIÓN DEL STEPPER
    public void initStepper() {
        stepViewNewQuestion = binding.stepViewNewQuestion;
        stepViewNewQuestion.getState()
                // .selectedCircleColor(ContextCompat.getColor(this, R.color.colorAccent))
                // .selectedCircleRadius(getResources().getDimensionPixelSize(R.dimen.dp14))
                //.selectedStepNumberColor(ContextCompat.getColor(this, R.color.colorPrimary))
                // You should specify only stepsNumber or steps array of strings.
                // In case you specify both steps array is chosen.
                .steps(new ArrayList<String>() {{
                    add(getString(R.string.question_data));
                    add(getString(R.string.type_and_answers));
                    add(getString(R.string.clues));
                    add(getString(R.string.bonusses));
                    add(getString(R.string.summary));
                }})
                //.stepsNumber(4)
                .animationType(StepView.ANIMATION_ALL)
                .animationDuration(getResources().getInteger(android.R.integer.config_longAnimTime))
                //.stepLineWidth(getResources().getDimensionPixelSize(R.dimen.dp1))
                //.textSize(getResources().getDimensionPixelSize(R.dimen.sp14))
                //.stepNumberTextSize(getResources().getDimensionPixelSize(R.dimen.sp16))
                //.typeface(ResourcesCompat.getFont(context, R.font.roboto_italic))
                .commit();

        themeDark = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("DarkTheme", false);
        if (themeDark) {
            stepViewNewQuestion.getState().
                    selectedTextColor(ContextCompat.getColor(getContext(), R.color.white))
                    .doneTextColor(ContextCompat.getColor(getContext(), R.color.white))
                    .commit();
        }
    }
}