package cu.cujae.gilsoft.tykeprof.app.fragment;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputLayout;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.adapter.ClueType_Adapter;
import cu.cujae.gilsoft.tykeprof.adapter.Grant_Adapter;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.ClueTypeViewModel;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.GrantViewModel;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Grant;
import cu.cujae.gilsoft.tykeprof.databinding.ClueTypeFragmentBinding;
import cu.cujae.gilsoft.tykeprof.databinding.GrantFragmentBinding;

public class GrantFragment extends Fragment {

    private GrantViewModel grantViewModel;
    private GrantFragmentBinding binding;

    public static GrantFragment newInstance() {
        return new GrantFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = GrantFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        grantViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(GrantViewModel.class);
        RecyclerView recyclerView = binding.RecyclerViewGrant;
        final Grant_Adapter adapter = new Grant_Adapter(new Grant_Adapter.GrantDiff(), grantViewModel, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        grantViewModel.getAllGrant().observe(getViewLifecycleOwner(), grantList -> {
            adapter.submitList(grantList);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton materialButtonAddGrant = binding.materialButtonAddGrant;
        materialButtonAddGrant.setOnClickListener(v -> {
            View view1 = getLayoutInflater().inflate(R.layout.dialog_add_grant, null);
            Slider slider = view1.findViewById(R.id.sliderGrantPoints);

            slider.addOnChangeListener(new Slider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    slider.setValue(Integer.valueOf((int) slider.getValue()));
                }
            });

            TextInputLayout textInputLayoutNewGrant = view1.findViewById(R.id.textInputLayoutNewGrant);
            EditText editTextGrantName = view1.findViewById(R.id.editTextGrantName);
            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.new_grant)
                    .setPositiveButton(R.string.accept, null)
                    .setNegativeButton(getString(R.string.cancel), null)
                    .setView(view1)
                    .show();

            Button buttonPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            buttonPos.setOnClickListener(v1 -> {
                String string = editTextGrantName.getText().toString();
                if (string.isEmpty()) {
                    //ToastHelper.showCustomToast(getActivity(), "warning", getString(R.string.must_fill_fields));
                    textInputLayoutNewGrant.setError(getString(R.string.required));
                } else {
                    grantViewModel.saveGrant(new Grant((int) slider.getValue(), string));
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