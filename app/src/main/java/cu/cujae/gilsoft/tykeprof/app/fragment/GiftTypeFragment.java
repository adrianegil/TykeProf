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
import cu.cujae.gilsoft.tykeprof.adapter.GiftType_Adapter;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.GiftTypeViewModel;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;
import cu.cujae.gilsoft.tykeprof.databinding.GiftTypeFragmentBinding;

public class GiftTypeFragment extends Fragment {

    private GiftTypeViewModel giftTypeViewModel;
    private GiftTypeFragmentBinding binding;

    public static GiftTypeFragment newInstance() {
        return new GiftTypeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = GiftTypeFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        giftTypeViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(GiftTypeViewModel.class);
        RecyclerView recyclerView = binding.RecyclerViewGiftType;
        final GiftType_Adapter adapter = new GiftType_Adapter(new GiftType_Adapter.GiftTypeDiff(), giftTypeViewModel, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        giftTypeViewModel.getAllGiftType().observe(getViewLifecycleOwner(), gift_typeList -> {
            adapter.submitList(gift_typeList);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton materialButtonAddGiftType = binding.materialButtonAddGiftType;
        materialButtonAddGiftType.setOnClickListener(v -> {
            View view1 = getLayoutInflater().inflate(R.layout.dialog_add_gift_type, null);
            TextInputLayout textInputLayoutNewGifType = view1.findViewById(R.id.textInputLayoutNewGiftType);
            EditText editText = view1.findViewById(R.id.editTextNewGiftType);
            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.new_gifttype)
                    .setPositiveButton(R.string.accept, null)
                    .setNegativeButton(getString(R.string.cancel), null)
                    .setView(view1)
                    .show();

            Button buttonPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            buttonPos.setOnClickListener(v1 -> {
                String string = editText.getText().toString();
                if (string.isEmpty()) {
                    //ToastHelper.showCustomToast(getActivity(), "warning", getString(R.string.must_fill_fields));
                    textInputLayoutNewGifType.setError(getString(R.string.required));
                } else {
                    giftTypeViewModel.saveGiftType(new Gift_Type(string));
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