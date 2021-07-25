package cu.cujae.gilsoft.tykeprof.app.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.databinding.ClueTypeFragmentBinding;
import cu.cujae.gilsoft.tykeprof.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_container);

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