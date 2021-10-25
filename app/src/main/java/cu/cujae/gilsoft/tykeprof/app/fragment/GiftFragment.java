package cu.cujae.gilsoft.tykeprof.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.adapter.Gift_Adapter;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.GiftViewModel;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Grant;
import cu.cujae.gilsoft.tykeprof.data.model.Gift_Model;
import cu.cujae.gilsoft.tykeprof.databinding.GiftFragmentBinding;
import cu.cujae.gilsoft.tykeprof.repository.Gift_Type_Repository;
import cu.cujae.gilsoft.tykeprof.repository.Grant_Repository;

public class GiftFragment extends Fragment {

    private GiftViewModel giftViewModel;
    private GiftFragmentBinding binding;
    private Gift_Type_Repository gift_type_repository;
    private Grant_Repository grant_repository;
    private ArrayList<Gift_Type> gift_typeArrayList;
    private ArrayList<Grant> grantArrayList;

    public static GiftFragment newInstance() {
        return new GiftFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = GiftFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gift_type_repository = new Gift_Type_Repository(getActivity().getApplication());
        grant_repository = new Grant_Repository(getActivity().getApplication());
        gift_typeArrayList = (ArrayList<Gift_Type>) gift_type_repository.getAllGiftTypeList();
        grantArrayList = (ArrayList<Grant>) grant_repository.getAllGrantList();
        RecyclerView recyclerView = binding.RecyclerViewGift;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        giftViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(GiftViewModel.class);
        final Gift_Adapter adapter = new Gift_Adapter(new Gift_Adapter.GiftDiff(), giftViewModel, getActivity(), getActivity().getApplication());
        recyclerView.setAdapter(adapter);
        giftViewModel.getAllGift().observe(getViewLifecycleOwner(), giftList -> {
            adapter.submitList(giftList);
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View view1 = getLayoutInflater().inflate(R.layout.dialog_add_gift, null);

        // Spinner spinner = view1.findViewById(R.id.textInputLayoutGiftTypeOfGift);

        /*autoCompleteGiftTypeOfGift.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Gift_Type  gift_type = (Gift_Type) autoCompleteGiftTypeOfGift.getAdapter().getItem(position);
                gift_model.setId_gift_typeName(gift_type.getName());
            }
        });

        autoCompleteGrantOfGift.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Grant  grant = (Grant) autoCompleteGrantOfGift.getAdapter().getItem(position);
                gift_model.setId_grant_Name(grant.getGrant_name());
            }
        });*/

        MaterialButton materialButtonAddGift = binding.materialButtonAddGift;
        materialButtonAddGift.setOnClickListener(v -> {

            Gift_Model gift_model = new Gift_Model();

            ViewGroup parent = (ViewGroup) view1.getParent();
            if (parent != null)
                parent.removeAllViews();

            gift_typeArrayList = (ArrayList<Gift_Type>) gift_type_repository.getAllGiftTypeLocalList();
            grantArrayList = (ArrayList<Grant>) grant_repository.getAllGrantLocalList();

            ArrayAdapter<Gift_Type> giftTypeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, gift_typeArrayList);
            ArrayAdapter<Grant> grantAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, grantArrayList);

            AutoCompleteTextView autoCompleteGiftTypeOfGift = view1.findViewById(R.id.autoCompleteGiftTypeOfGift);
            AutoCompleteTextView autoCompleteGrantOfGift = view1.findViewById(R.id.autoCompleteGrantOfGift);
            // spinner.setAdapter(adapterGiftType);
            autoCompleteGiftTypeOfGift.setAdapter(giftTypeAdapter);
            autoCompleteGrantOfGift.setAdapter(grantAdapter);

            TextInputLayout textInputLayoutGiftTypeOfGift = view1.findViewById(R.id.textInputLayoutGiftTypeOfGift);
            TextInputLayout textInputLayouGrantOfGift = view1.findViewById(R.id.textInputLayouGrantOfGift);
            TextInputLayout textInputLayoutDescripOfGift = view1.findViewById(R.id.textInputLayoutDescripOfGift);
            EditText editTextDescripOfGift = view1.findViewById(R.id.editTextDescripOfGift);

            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.new_gift)
                    //.setMessage("Inserte el nombre de la Estrategia")
                    .setPositiveButton(R.string.accept, null)
                    .setNegativeButton(getString(R.string.cancel), null)
                    .setView(view1)
                    .show();

            Button buttonPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            buttonPos.setOnClickListener(v1 -> {
                String stringDescrip = editTextDescripOfGift.getText().toString();
                String stringGiftTypeNameId = autoCompleteGiftTypeOfGift.getText().toString();
                String stringGrantNameId = autoCompleteGrantOfGift.getText().toString();

                if (stringDescrip.isEmpty() || stringGiftTypeNameId.isEmpty() || stringGiftTypeNameId.isEmpty()) {
                    if (stringDescrip.isEmpty())
                        textInputLayoutDescripOfGift.setError(getString(R.string.required));
                    if (stringGiftTypeNameId.isEmpty())
                        textInputLayoutGiftTypeOfGift.setError(getString(R.string.required));
                    if (stringGrantNameId.isEmpty())
                        textInputLayouGrantOfGift.setError(getString(R.string.required));
                } else {
                    gift_model.setDescrip(stringDescrip);
                    gift_model.setId_gift_typeName(autoCompleteGiftTypeOfGift.getText().toString());
                    gift_model.setId_grant_Name(autoCompleteGrantOfGift.getText().toString());
                    giftViewModel.saveGift(gift_model);
                    autoCompleteGiftTypeOfGift.setText("");
                    autoCompleteGrantOfGift.setText("");
                    editTextDescripOfGift.setText("");
                    dialog.dismiss();
                }
            });
            Button buttonNeg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            buttonNeg.setOnClickListener(v12 -> {
                editTextDescripOfGift.setText("");
                autoCompleteGrantOfGift.setText("");
                autoCompleteGiftTypeOfGift.setText("");
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