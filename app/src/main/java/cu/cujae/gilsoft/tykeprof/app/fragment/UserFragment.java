package cu.cujae.gilsoft.tykeprof.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.squareup.picasso.Picasso;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.UserViewModel;
import cu.cujae.gilsoft.tykeprof.data.entity.User;
import cu.cujae.gilsoft.tykeprof.databinding.DialogChangePasswordBinding;
import cu.cujae.gilsoft.tykeprof.databinding.DialogEditUserBinding;
import cu.cujae.gilsoft.tykeprof.databinding.UserFragmentBinding;
import cu.cujae.gilsoft.tykeprof.util.ToastHelper;

public class UserFragment extends Fragment {

    private UserViewModel userViewModel;
    private UserFragmentBinding binding;
    private DialogChangePasswordBinding dialogChangePasswordBinding;
    private DialogEditUserBinding dialogEditUserBinding;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = UserFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toast.makeText(getContext(), getString(R.string.user_profile), Toast.LENGTH_SHORT).show();
        userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(UserViewModel.class);
        userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            binding.setUser(user);
            loadUserImage(user.getImage_url());
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.materialButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitApp(v);
            }
        });

        binding.materialButtonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = binding.getUser();

                dialogChangePasswordBinding = DialogChangePasswordBinding.inflate(getLayoutInflater(), null, false);

                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.change_password)
                        //.setMessage("Inserte el nombre de la Estrategia")
                        .setPositiveButton(R.string.accept, null)
                        .setNegativeButton(getString(R.string.cancel), null)
                        .setView(dialogChangePasswordBinding.getRoot())
                        .show();

                Button buttonPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                buttonPos.setOnClickListener(v1 -> {
                    String stringNewPassword = dialogChangePasswordBinding.editTextNewPassword.getText().toString();
                    String stringConfirmPassword = dialogChangePasswordBinding.editTextConfirmPassword.getText().toString();
                    if (stringNewPassword.isEmpty() || stringConfirmPassword.isEmpty() || !stringNewPassword.equals(stringConfirmPassword)) {
                        if (stringNewPassword.isEmpty())
                            dialogChangePasswordBinding.textInputLayoutNewPassword.setError(getString(R.string.required));
                        if (stringConfirmPassword.isEmpty())
                            dialogChangePasswordBinding.textInputLayoutConfirmPassword.setError(getString(R.string.required));
                        if (!stringNewPassword.equals(stringConfirmPassword))
                            ToastHelper.showCustomToast(getActivity(), "error", getString(R.string.password_dont_coincide));
                    } else {
                        user.setPassword(stringNewPassword);
                        userViewModel.changePassword(user);
                        dialog.dismiss();
                    }
                });
                Button buttonNeg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                buttonNeg.setOnClickListener(v12 -> dialog.dismiss());
            }
        });

        binding.materialButtonEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = binding.getUser();
                dialogEditUserBinding = DialogEditUserBinding.inflate(getLayoutInflater(), null, false);

                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.edit_data_personal)
                        //.setMessage("Inserte el nombre de la Estrategia")
                        .setPositiveButton(R.string.accept, null)
                        .setNegativeButton(getString(R.string.cancel), null)
                        .setView(dialogEditUserBinding.getRoot())
                        .show();

                dialogEditUserBinding.autoCompleteChangeDobUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MaterialDatePicker.Builder datePickerBuilder = MaterialDatePicker.Builder.datePicker();
                        MaterialDatePicker materialDatePicker = datePickerBuilder.build();
                        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick(Object selection) {
                                dialogEditUserBinding.autoCompleteChangeDobUser.setText(materialDatePicker.getHeaderText());
                                user.setDob(selection.toString());
                            }
                        });
                        materialDatePicker.show(getParentFragmentManager(), "DOB");
                    }
                });

                dialogEditUserBinding.editTextChangeUserName.setText(user.getUserName());
                dialogEditUserBinding.textInputLayoutChangeUserName.setEnabled(false);
                dialogEditUserBinding.editTextChangeEmailUser.setText(user.getEmail());
                dialogEditUserBinding.editTextChangeNameUser.setText(user.getFullName());

                Button buttonPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                buttonPos.setOnClickListener(v1 -> {
                    String stringNameUser = dialogEditUserBinding.editTextChangeNameUser.getText().toString();
                    String stringEmailUser = dialogEditUserBinding.editTextChangeEmailUser.getText().toString();
                    String stringDobUser = dialogEditUserBinding.autoCompleteChangeDobUser.getText().toString();

                    if (stringNameUser.isEmpty() || stringEmailUser.isEmpty() || stringDobUser.isEmpty()) {
                        if (stringNameUser.isEmpty())
                            dialogEditUserBinding.textInputLayoutChangeNameUser.setError(getString(R.string.required));
                        if (stringEmailUser.isEmpty())
                            dialogEditUserBinding.textInputLayoutChangeEmailUser.setError(getString(R.string.required));
                        if (stringDobUser.isEmpty())
                            dialogEditUserBinding.textInputLayoutChangeDobUser.setError(getString(R.string.required));
                    } else {
                        user.setFullName(stringNameUser);
                        user.setEmail(stringEmailUser);
                        userViewModel.updateUser(user);
                        dialog.dismiss();
                    }
                });
                Button buttonNeg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                buttonNeg.setOnClickListener(v12 -> dialog.dismiss());
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void loadUserImage(String url) {
        Picasso picasso = Picasso.get();
        //picasso.setIndicatorsEnabled(true);
        if (!url.isEmpty()) {
            picasso
                    .load(url)
                    .placeholder(R.drawable.ic_account_circle_gray)
                    // .resize(80,80)
                    //.centerCrop(Gravity.CENTER_VERTICAL)
                    .error(R.drawable.ic_account_circle_gray)
                    .into(binding.imageViewUser);
        } else {
            picasso
                    .load("url")
                    .error(R.drawable.ic_account_circle_gray)
                    .into(binding.imageViewUser);
        }
    }

    public void exitApp(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle(R.string.exit_confirm);
        dialog.setMessage(R.string.exit_confirm_description);
        dialog.setPositiveButton(R.string.yes, (dialog12, which) -> {
            getActivity().finish();
        });
        dialog.setNegativeButton("No", (dialog13, which) -> dialog13.dismiss());
        dialog.setNeutralButton(R.string.cancel, (dialog1, which) -> dialog1.dismiss());
        dialog.show();
    }
}