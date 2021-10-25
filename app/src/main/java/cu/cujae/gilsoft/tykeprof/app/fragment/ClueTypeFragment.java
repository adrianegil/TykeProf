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

import org.jetbrains.annotations.NotNull;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.adapter.ClueType_Adapter;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.ClueTypeViewModel;
import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;
import cu.cujae.gilsoft.tykeprof.databinding.ClueTypeFragmentBinding;

public class ClueTypeFragment extends Fragment {

    private ClueTypeViewModel clueTypeViewModel;
    private ClueTypeFragmentBinding binding;

    public static ClueTypeFragment newInstance() {
        return new ClueTypeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ClueTypeFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // clueTypeViewModel = new ViewModelProvider(this).get(ClueTypeViewModel.class);
        clueTypeViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(ClueTypeViewModel.class);
        RecyclerView recyclerView = binding.RecyclerViewClueType;
        final ClueType_Adapter adapter = new ClueType_Adapter(new ClueType_Adapter.ClueTypeDiff(), clueTypeViewModel, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        clueTypeViewModel.getAllClueType().observe(getViewLifecycleOwner(), clueTypeList -> {
            adapter.submitList(clueTypeList);
        });
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton materialButtonAddClueType = binding.materialButtonAddClueType;
        materialButtonAddClueType.setOnClickListener(v -> {

            View view1 = getLayoutInflater().inflate(R.layout.dialog_add_clue_type, null);
            TextInputLayout textInputLayoutClueType = view1.findViewById(R.id.textInputLayoutClueType);
            EditText editTextClueType = view1.findViewById(R.id.editTextClueType);
            TextInputLayout textInputLayoutGamePoints = view1.findViewById(R.id.textInputLayoutGamePoints);
            EditText editTextGamePoints = view1.findViewById(R.id.editTextGamePoints);
            TextInputLayout textInputLayoutCluePoints = view1.findViewById(R.id.textInputLayoutCluePoints);
            EditText editTextCluePoints = view1.findViewById(R.id.editTextCluePoints);

            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.new_clue_type)
                    //.setMessage("Inserte el nombre de la Estrategia")
                    .setPositiveButton(R.string.accept, null)
                    .setNegativeButton(getString(R.string.cancel), null)
                    .setView(view1)
                    .show();

            Button buttonPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            buttonPos.setOnClickListener(v1 -> {
                String stringClueType = editTextClueType.getText().toString();
                String stringGamePoints = editTextGamePoints.getText().toString();
                String stringCluePoints = editTextCluePoints.getText().toString();
                if (stringClueType.isEmpty() || stringGamePoints.isEmpty() || stringCluePoints.isEmpty()) {
                    if (stringClueType.isEmpty())
                        textInputLayoutClueType.setError(getString(R.string.required));
                    if (stringGamePoints.isEmpty())
                        textInputLayoutGamePoints.setError(getString(R.string.required));
                    if (stringCluePoints.isEmpty())
                        textInputLayoutCluePoints.setError(getString(R.string.required));
                    //ToastHelper.showCustomToast(getActivity(), "warning", getString(R.string.must_fill_fields));
                } else {
                    clueTypeViewModel.saveClueType(new Clue_Type(stringClueType, Integer.valueOf(stringCluePoints), Integer.valueOf(stringGamePoints)));
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