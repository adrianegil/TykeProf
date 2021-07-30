package cu.cujae.gilsoft.tykeprof.adapter;

import android.app.Activity;
import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.ClueTypeViewModel;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.GiftViewModel;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Grant;
import cu.cujae.gilsoft.tykeprof.data.model.Gift_Model;
import cu.cujae.gilsoft.tykeprof.repository.Gift_Repository;
import cu.cujae.gilsoft.tykeprof.repository.Gift_Type_Repository;
import cu.cujae.gilsoft.tykeprof.repository.Grant_Repository;

public class Gift_Adapter extends ListAdapter<Gift, Gift_Adapter.ViewHolder> {

    GiftViewModel giftViewModel;
    Activity activity;
    Gift_Type_Repository gift_type_repository;
    Grant_Repository grant_repository;


    public Gift_Adapter(@NonNull DiffUtil.ItemCallback<Gift> diffCallback, GiftViewModel giftViewModel, Activity activity, Application application) {
        super(diffCallback);
        this.giftViewModel = giftViewModel;
        this.activity = activity;
        this.gift_type_repository = new Gift_Type_Repository(application);
        this.grant_repository = new Grant_Repository(application);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gift_itemlist, parent, false);
        return new Gift_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Gift gift = getItem(position);
        holder.textViewGiftDecrip.setText("Descripción: " + gift.getDescrip());
        holder.layoutExpanded.setVisibility(gift.isContenExpandable() ? View.VISIBLE : View.GONE);

        View view1 = activity.getLayoutInflater().inflate(R.layout.dialog_add_gift, null);

        holder.viewDeleteGift.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
            dialog.setTitle(R.string.delete_gift);
            dialog.setMessage(R.string.confirm_delete_gift);
            dialog.setPositiveButton(R.string.yes, (dialog1, which) -> {
                giftViewModel.deleteGift(gift.getId_gift());
            });
            dialog.setNegativeButton("No", (dialog13, which) -> dialog13.dismiss());
            dialog.setNeutralButton(R.string.cancel, (dialog12, which) -> dialog12.dismiss());
            dialog.show();
        });

        Gift_Type gift_type = gift_type_repository.getGiftTypeLocalbyId(gift.getId_gift_type());
        Grant grant = grant_repository.getGrantLocabyId(gift.getId_grant());

        holder.textViewGiftTypeofGift.setText(gift_type.getName());
        holder.textViewGrantofGift.setText("Otorgamiento: " + grant.getGrant_name());

        // Spinner spinner = view1.findViewById(R.id.textInputLayoutGiftTypeOfGift);

        holder.viewEditGift.setOnClickListener(v -> {

            ViewGroup parent = (ViewGroup) view1.getParent();
            if (parent != null)
                parent.removeAllViews();

            ArrayList<Gift_Type> gift_typeArrayList = (ArrayList<Gift_Type>) gift_type_repository.getAllGiftTypeLocalList();
            ArrayList<Grant> grantArrayList = (ArrayList<Grant>) grant_repository.getAllGrantLocalList();

            TextInputLayout textInputLayoutGiftTypeOfGift = view1.findViewById(R.id.textInputLayoutGiftTypeOfGift);
            AutoCompleteTextView autoCompleteGiftTypeOfGift = view1.findViewById(R.id.autoCompleteGiftTypeOfGift);
            TextInputLayout textInputLayouGrantOfGift = view1.findViewById(R.id.textInputLayouGrantOfGift);
            AutoCompleteTextView autoCompleteGrantOfGift = view1.findViewById(R.id.autoCompleteGrantOfGift);
            TextInputLayout textInputLayoutDescripOfGift = view1.findViewById(R.id.textInputLayoutDescripOfGift);
            EditText editTextDescripOfGift = view1.findViewById(R.id.editTextDescripOfGift);

            ArrayAdapter<Gift_Type> giftTypeAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, gift_typeArrayList);
            ArrayAdapter<Grant> grantAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, grantArrayList);

            autoCompleteGiftTypeOfGift.setText(gift_type.getName());
            autoCompleteGrantOfGift.setText(grant.getGrant_name());
            editTextDescripOfGift.setText(gift.getDescrip());
            // spinner.setAdapter(adapterGiftType);
            autoCompleteGiftTypeOfGift.setAdapter(giftTypeAdapter);
            autoCompleteGrantOfGift.setAdapter(grantAdapter);

            Gift_Model gift_model = new Gift_Model();
            gift_model.setId_gift(gift.getId_gift());

            AlertDialog dialog = new AlertDialog.Builder(activity)
                    .setTitle("Nuevo Regalo")
                    //.setMessage("Inserte el nombre de la Estrategia")
                    .setPositiveButton(R.string.accept, null)
                    .setNegativeButton(activity.getString(R.string.cancel), null)
                    .setView(view1)
                    .show();

            Button buttonPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            buttonPos.setOnClickListener(v1 -> {
                String stringDescrip = editTextDescripOfGift.getText().toString();
                String stringGiftTypeNameId = autoCompleteGiftTypeOfGift.getText().toString();
                String stringGrantNameId = autoCompleteGrantOfGift.getText().toString();

                if (stringDescrip.isEmpty()||stringGiftTypeNameId.isEmpty()||stringGiftTypeNameId.isEmpty()) {
                    if (stringDescrip.isEmpty())
                        textInputLayoutDescripOfGift.setError(activity.getString(R.string.required));
                    if (stringGiftTypeNameId.isEmpty())
                        textInputLayoutGiftTypeOfGift.setError(activity.getString(R.string.required));
                    if (stringGrantNameId.isEmpty())
                        textInputLayouGrantOfGift.setError(activity.getString(R.string.required));
                } else {
                    gift_model.setDescrip(stringDescrip);
                    gift_model.setId_gift_typeName(autoCompleteGiftTypeOfGift.getText().toString());
                    gift_model.setId_grant_Name(autoCompleteGrantOfGift.getText().toString());
                    giftViewModel.updateGift(gift_model);
                    autoCompleteGiftTypeOfGift.setText("");
                    autoCompleteGrantOfGift.setText("");
                    editTextDescripOfGift.setText("");
                    dialog.dismiss();
                }
            });
            Button buttonNeg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            buttonNeg.setOnClickListener(v12 -> dialog.dismiss());
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView textViewGiftTypeofGift;
        private final MaterialTextView textViewGrantofGift;
        private final MaterialTextView textViewGiftDecrip;
        private final MaterialButton viewLookGiftContent;
        private final MaterialButton viewEditGift;
        private final MaterialButton viewDeleteGift;
        private final ConstraintLayout layoutExpanded;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewGiftTypeofGift = itemView.findViewById(R.id.textViewGiftTypeofGift);
            textViewGrantofGift = itemView.findViewById(R.id.textViewGrantofGift);
            textViewGiftDecrip = itemView.findViewById(R.id.textViewGiftDecrip);
            viewLookGiftContent = itemView.findViewById(R.id.viewLookGiftContent);
            viewEditGift = itemView.findViewById(R.id.viewEditGift);
            viewDeleteGift = itemView.findViewById(R.id.viewDeleteGift);
            layoutExpanded = itemView.findViewById(R.id.layoutExpandedGiftContent);

            viewLookGiftContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gift gift = Gift_Adapter.this.getCurrentList().get(getAdapterPosition());
                    gift.setContenExpandable(!gift.isContenExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }

    public static class GiftDiff extends DiffUtil.ItemCallback<Gift> {

        @Override
        public boolean areItemsTheSame(@NonNull Gift oldItem, @NonNull Gift newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Gift oldItem, @NonNull Gift newItem) {
            return oldItem.getDescrip().equals(newItem.getDescrip());
        }
    }


}
