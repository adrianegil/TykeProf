package cu.cujae.gilsoft.tykeprof.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
        stepViewNewQuestion = binding.stepViewNewQuestion;
        stepViewNewQuestion.getState()
                // .selectedTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                // .animationType(StepView.ANIMATION_CIRCLE)
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
                .animationType(StepView.ANIMATION_ALL)
                // You should specify only steps number or steps array of strings.
                // In case you specify both steps array is chosen.
                //.stepsNumber(4)
                .animationDuration(getResources().getInteger(android.R.integer.config_longAnimTime))
                //.stepLineWidth(getResources().getDimensionPixelSize(R.dimen.dp1))
                //.textSize(getResources().getDimensionPixelSize(R.dimen.sp14))
                //.stepNumberTextSize(getResources().getDimensionPixelSize(R.dimen.sp16))
                //.typeface(ResourcesCompat.getFont(context, R.font.roboto_italic))
                // other state methods are equal to the corresponding xml attributes
                .commit();
    }
}