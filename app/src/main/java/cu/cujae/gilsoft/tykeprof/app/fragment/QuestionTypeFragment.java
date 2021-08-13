package cu.cujae.gilsoft.tykeprof.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.adapter.QuestionType_Adapter;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.QuestionTypeViewModel;
import cu.cujae.gilsoft.tykeprof.data.entity.Question_Type;
import cu.cujae.gilsoft.tykeprof.databinding.QuestionTypeFragmentBinding;

public class QuestionTypeFragment extends Fragment {

    private QuestionTypeViewModel myQuestionTypeViewModel;
    private QuestionTypeFragmentBinding binding;

    public static QuestionTypeFragment newInstance() {
        return new QuestionTypeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = QuestionTypeFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myQuestionTypeViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(QuestionTypeViewModel.class);
        RecyclerView recyclerView = binding.RecyclerViewQuestionType;
        final QuestionType_Adapter adapter = new QuestionType_Adapter(new QuestionType_Adapter.QuestionTypeDiff(), myQuestionTypeViewModel, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myQuestionTypeViewModel.getAllQuestionType().observe(getViewLifecycleOwner(), questionTypeList -> {
            adapter.submitList(questionTypeList);
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton materialButtonAddQuestionType = binding.materialButtonAddQuestionType;
        materialButtonAddQuestionType.setOnClickListener(v -> {
            View view1 = getLayoutInflater().inflate(R.layout.dialog_add_question_type, null);
            TextInputLayout textInputLayoutNewQuestionType = view1.findViewById(R.id.textInputLayoutNewQuestionType);
            EditText editText = view1.findViewById(R.id.editTextNewQuestionType);
            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.new_questiontype)
                    //.setMessage("Inserte el nombre de la Estrategia")
                    .setPositiveButton(R.string.accept, null)
                    .setNegativeButton(getString(R.string.cancel), null)
                    .setView(view1)
                    .show();

            Button buttonPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            buttonPos.setOnClickListener(v1 -> {
                String string = editText.getText().toString();
                if (string.isEmpty()) {
                    textInputLayoutNewQuestionType.setError(getString(R.string.required));
                } else {
                    myQuestionTypeViewModel.saveQuestionType(new Question_Type(string));
                    dialog.dismiss();
                }
            });
            Button buttonNeg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            buttonNeg.setOnClickListener(v12 -> dialog.dismiss());
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}



