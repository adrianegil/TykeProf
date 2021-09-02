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

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.adapter.Question_Adapter;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.QuestionViewModel;
import cu.cujae.gilsoft.tykeprof.databinding.QuestionFragmentBinding;

public class QuestionFragment extends Fragment {

    private QuestionViewModel questionViewModel;
    private QuestionFragmentBinding binding;
    private NavController navController;

    public static QuestionFragment newInstance() {
        return new QuestionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = QuestionFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        questionViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(QuestionViewModel.class);
        RecyclerView recyclerView = binding.RecyclerViewQuestion;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Question_Adapter adapter = new Question_Adapter(new Question_Adapter.QuestionDiff(), questionViewModel, getActivity(), getActivity().getApplication());
        recyclerView.setAdapter(adapter);
        questionViewModel.getAllQuestion().observe(getViewLifecycleOwner(), questionList -> {
            adapter.submitList(questionList);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_container);
        binding.materialButtonAddQuestion.setOnClickListener(v -> {
            navController.navigate(R.id.go_newQuestion);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}