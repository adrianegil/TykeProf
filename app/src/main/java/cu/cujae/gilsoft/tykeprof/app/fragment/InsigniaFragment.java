package cu.cujae.gilsoft.tykeprof.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.adapter.Insignia_Adapter;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.InsigniaViewModel;
import cu.cujae.gilsoft.tykeprof.data.entity.Professional_Rol;
import cu.cujae.gilsoft.tykeprof.data.model.Insignia_Model;
import cu.cujae.gilsoft.tykeprof.databinding.InsigniaFragmentBinding;
import cu.cujae.gilsoft.tykeprof.repository.Professional_Rol_Repository;

public class InsigniaFragment extends Fragment {

    private InsigniaViewModel insigniaViewModel;
    private InsigniaFragmentBinding binding;
    private Professional_Rol_Repository professional_rol_repository;
    private ArrayList<Professional_Rol> professional_rols;

    public static InsigniaFragment newInstance() {
        return new InsigniaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = InsigniaFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        professional_rol_repository = new Professional_Rol_Repository(getActivity().getApplication());
        professional_rols = (ArrayList<Professional_Rol>) professional_rol_repository.getAllProfessionalRolList();
        RecyclerView recyclerView = binding.RecyclerViewInsignia;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        insigniaViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(InsigniaViewModel.class);
        final Insignia_Adapter adapter = new Insignia_Adapter(new Insignia_Adapter.InsigniaDiff(), insigniaViewModel, getActivity(), getActivity().getApplication());
        recyclerView.setAdapter(adapter);
        insigniaViewModel.getAllInsignias().observe(getViewLifecycleOwner(), insigniaList -> {
            adapter.submitList(insigniaList);
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton materialButtonAddGrant = binding.materialButtonAddInsignia;
        materialButtonAddGrant.setOnClickListener(v -> {

            View view1 = getLayoutInflater().inflate(R.layout.dialog_add_insignia, null);
            Insignia_Model insignia_model = new Insignia_Model();

            Slider sliderAdvancePoints = view1.findViewById(R.id.sliderAdvancePoints);
            sliderAdvancePoints.addOnChangeListener(new Slider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    sliderAdvancePoints.setValue(Integer.valueOf((int) slider.getValue()));
                }
            });
            Slider sliderGrantPoints = view1.findViewById(R.id.sliderGrantPoints);
            sliderGrantPoints.addOnChangeListener(new Slider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    sliderGrantPoints.setValue(Integer.valueOf((int) slider.getValue()));
                }
            });

            TextInputLayout textInputLayoutNewInsignia = view1.findViewById(R.id.textInputLayoutNewInsignia);
            EditText editTextInsigniaName = view1.findViewById(R.id.editTextInsigniaName);
            TextInputLayout textInputLayoutProfessRolToInsignia = view1.findViewById(R.id.textInputLayoutProfessRolToInsignia);
            AutoCompleteTextView autoCompleteProfessRolToInsignia = view1.findViewById(R.id.autoCompleteProfessRolToInsignia);

            professional_rols = (ArrayList<Professional_Rol>) professional_rol_repository.getAllProfessionalRolList();

            ArrayAdapter<Professional_Rol> professRolAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, professional_rols);
            autoCompleteProfessRolToInsignia.setAdapter(professRolAdapter);
            autoCompleteProfessRolToInsignia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Professional_Rol professional_rol = (Professional_Rol) autoCompleteProfessRolToInsignia.getAdapter().getItem(position);
                    insignia_model.setId_profess_rol(professional_rol.getId_profess_rol());
                }
            });

            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.new_insignia)
                    .setPositiveButton(R.string.accept, null)
                    .setNegativeButton(getString(R.string.cancel), null)
                    .setView(view1)
                    .show();

            Button buttonPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            buttonPos.setOnClickListener(v1 -> {
                String stringInsigniaName = editTextInsigniaName.getText().toString();
                String stringProfessRol = autoCompleteProfessRolToInsignia.getText().toString();

                if (stringInsigniaName.isEmpty() || stringProfessRol.isEmpty()) {
                    if (stringInsigniaName.isEmpty())
                        textInputLayoutNewInsignia.setError(getString(R.string.required));
                    if (stringProfessRol.isEmpty())
                        textInputLayoutProfessRolToInsignia.setError(getString(R.string.required));
                } else {
                    insignia_model.setName(stringInsigniaName);
                    insignia_model.setAdvance_points((int) sliderAdvancePoints.getValue());
                    insignia_model.setGrant_points((int) sliderGrantPoints.getValue());
                    insigniaViewModel.saveInsignia(insignia_model);
                    dialog.dismiss();
                }
            });
            Button buttonNeg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            buttonNeg.setOnClickListener(v12 -> {
                sliderAdvancePoints.setValue(1);
                sliderGrantPoints.setValue(1);
                dialog.dismiss();
            });
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}


