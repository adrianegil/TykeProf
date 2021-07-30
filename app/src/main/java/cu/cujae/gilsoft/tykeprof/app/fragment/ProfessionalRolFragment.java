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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.adapter.Gift_Adapter;
import cu.cujae.gilsoft.tykeprof.adapter.ProfessionalRol_Adapter;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.GiftViewModel;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.ProfessionalRolViewModel;
import cu.cujae.gilsoft.tykeprof.data.entity.Career;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Grant;
import cu.cujae.gilsoft.tykeprof.data.entity.Topic;
import cu.cujae.gilsoft.tykeprof.data.model.Gift_Model;
import cu.cujae.gilsoft.tykeprof.data.model.Professional_Rol_Model;
import cu.cujae.gilsoft.tykeprof.databinding.ProfessionalRolFragmentBinding;
import cu.cujae.gilsoft.tykeprof.databinding.QuestionTypeFragmentBinding;
import cu.cujae.gilsoft.tykeprof.repository.Career_Repository;
import cu.cujae.gilsoft.tykeprof.repository.Gift_Type_Repository;
import cu.cujae.gilsoft.tykeprof.repository.Grant_Repository;
import cu.cujae.gilsoft.tykeprof.repository.Topic_Repository;

public class ProfessionalRolFragment extends Fragment {

    private ProfessionalRolViewModel professionalRolViewModel;
    private ProfessionalRolFragmentBinding binding;
    Topic_Repository topic_repository;
    Career_Repository career_repository;
    ArrayList<Topic> topics;
    ArrayList<Career> careers;


    public static ProfessionalRolFragment newInstance() {
        return new ProfessionalRolFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ProfessionalRolFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        topic_repository = new Topic_Repository(getActivity().getApplication());
        career_repository = new Career_Repository(getActivity().getApplication());
        topics = (ArrayList<Topic>) topic_repository.getAllTopicList();
        careers = (ArrayList<Career>) career_repository.getAllCareerList();

        RecyclerView recyclerView = binding.RecyclerViewProfessionalRol;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        professionalRolViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(ProfessionalRolViewModel.class);
        final ProfessionalRol_Adapter adapter = new ProfessionalRol_Adapter(new ProfessionalRol_Adapter.ProfessionalRolDiff(), professionalRolViewModel, getActivity(), getActivity().getApplication());
        recyclerView.setAdapter(adapter);
        professionalRolViewModel.getAllProfessionalRol().observe(getViewLifecycleOwner(), professionalRolList -> {
            adapter.submitList(professionalRolList);
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View view1 = getLayoutInflater().inflate(R.layout.dialog_add_professional_rol, null);

        MaterialButton materialButtonAddGift = binding.materialButtonAddProfessionalRol;
        materialButtonAddGift.setOnClickListener(v -> {

            ViewGroup parent = (ViewGroup) view1.getParent();
            if (parent != null)
                parent.removeAllViews();

            AutoCompleteTextView autoCompleteCareerOfProfessRol = view1.findViewById(R.id.autoCompleteCareerOfProfessRol);
            AutoCompleteTextView autoCompleteTopicOfProfessRol = view1.findViewById(R.id.autoCompleteTopicOfProfessRol);

            Professional_Rol_Model professional_rol_model = new Professional_Rol_Model();

            topics = (ArrayList<Topic>) topic_repository.getAllTopicLocalList();
            careers = (ArrayList<Career>) career_repository.getAllCareerLocalList();

            ArrayAdapter<Topic> topicsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, topics);
            ArrayAdapter<Career> careersAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, careers);

            // spinner.setAdapter(adapterGiftType);
            // Spinner spinner = view1.findViewById(R.id.textInputLayoutGiftTypeOfGift);

            autoCompleteCareerOfProfessRol.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Career career = (Career) autoCompleteCareerOfProfessRol.getAdapter().getItem(position);
                    professional_rol_model.setId_career(career.getId_career());
                }
            });

            autoCompleteTopicOfProfessRol.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Topic topic = (Topic) autoCompleteTopicOfProfessRol.getAdapter().getItem(position);
                    professional_rol_model.setId_topic(topic.getId_topic());
                }
            });

            autoCompleteCareerOfProfessRol.setAdapter(careersAdapter);
            autoCompleteTopicOfProfessRol.setAdapter(topicsAdapter);

            TextInputLayout textInputLayoutNameOfProfessRol = view1.findViewById(R.id.textInputLayoutNameOfProfessRol);
            EditText editTextNameOfProfessRol = view1.findViewById(R.id.editTextNameOfProfessRol);
            TextInputLayout textInputLayoutCareerOfProfessRol = view1.findViewById(R.id.textInputLayoutCareerOfProfessRol);
            TextInputLayout textInputLayoutTopicOfProfessRol = view1.findViewById(R.id.textInputLayoutTopicOfProfessRol);

            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle("Nuevo Rol Profesional")
                    //.setMessage("Inserte el nombre de la Estrategia")
                    .setPositiveButton(R.string.accept, null)
                    .setNegativeButton(getString(R.string.cancel), null)
                    .setView(view1)
                    .show();

            Button buttonPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            buttonPos.setOnClickListener(v1 -> {
                String stringCareer = autoCompleteCareerOfProfessRol.getText().toString();
                String stringNameOfProfessRol = editTextNameOfProfessRol.getText().toString();
                String stringTopic = autoCompleteTopicOfProfessRol.getText().toString();

                if (stringCareer.isEmpty() || stringNameOfProfessRol.isEmpty() || stringTopic.isEmpty()) {
                    if (stringTopic.isEmpty())
                        textInputLayoutTopicOfProfessRol.setError(getString(R.string.required));
                    if (stringNameOfProfessRol.isEmpty())
                        textInputLayoutNameOfProfessRol.setError(getString(R.string.required));
                    if (stringCareer.isEmpty())
                        textInputLayoutCareerOfProfessRol.setError(getString(R.string.required));
                    Toast.makeText(getActivity(), professional_rol_model.getName_profess_rol() + "" + professional_rol_model.getId_career() + " " + professional_rol_model.getId_topic(), Toast.LENGTH_SHORT).show();

                    //ToastHelper.showCustomToast(getActivity(), "warning", getString(R.string.must_fill_fields));
                } else {
                    professional_rol_model.setName_profess_rol(stringNameOfProfessRol);
                    professionalRolViewModel.saveProfessionalRol(professional_rol_model);
                    editTextNameOfProfessRol.setText("");
                    autoCompleteCareerOfProfessRol.setText("");
                    autoCompleteTopicOfProfessRol.setText("");
                    dialog.dismiss();
                }
            });
            Button buttonNeg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            buttonNeg.setOnClickListener(v12 -> {
                editTextNameOfProfessRol.setText("");
                autoCompleteCareerOfProfessRol.setText("");
                autoCompleteTopicOfProfessRol.setText("");
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