package cu.cujae.gilsoft.tykeprof.app.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.adapter.Question_Adapter;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.QuestionViewModel;
import cu.cujae.gilsoft.tykeprof.databinding.ClueTypeFragmentBinding;
import cu.cujae.gilsoft.tykeprof.databinding.FragmentHomeBinding;
import cu.cujae.gilsoft.tykeprof.repository.Question_Repository;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private QuestionViewModel questionViewModel;
    private Question_Repository question_repository;
    private NavController navController;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        question_repository = new Question_Repository(getActivity().getApplication());
        binding.textViewCantsQuestions.setText(getString(R.string.question_cant) + question_repository.getQuestionListSize());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_container);
        binding.materialButtonGotoClueTypeFragment.setOnClickListener(v -> {
            //Navigation.createNavigateOnClickListener(R.id.go_ClueTypeFragment,null);
            // navController.navigate(R.id.nav_clueTypeFragment, null);
            navController.navigate(R.id.go_ClueTypeFragment);
        });

        binding.materialButtonGotoQuestionTypeFragment.setOnClickListener(v -> {
            //  Navigation.createNavigateOnClickListener(R.id.go_QuestionTypeFragment, null);
            //  navController.navigate(R.id.nav_questionTypeFragment, null);
            navController.navigate(R.id.go_QuestionTypeFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}