package cu.cujae.gilsoft.tykeprof.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.GrantViewModel;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.QuestionTypeViewModel;
import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Grant;

public class Grant_Adapter extends ListAdapter<Grant, Grant_Adapter.ViewHolder> {

    GrantViewModel grantViewModel;
    Activity activity;

    public Grant_Adapter(@NonNull DiffUtil.ItemCallback<Grant> diffCallback, GrantViewModel grantViewModel, Activity activity) {
        super(diffCallback);
        this.grantViewModel = grantViewModel;
        this.activity = activity;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grant_itemlist, parent, false);
        return new Grant_Adapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Grant grant = getItem(position);

        holder.TextViewGrantName.setText(grant.getGrant_name());
        holder.textViewGrantPoints.setText(activity.getString(R.string.points) + ": " + grant.getCant_points());

        holder.viewDeleteGrant.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
            dialog.setTitle(R.string.delete_grant);
            dialog.setMessage(R.string.confirm_delete_grant);
            dialog.setPositiveButton(R.string.yes, (dialog1, which) -> {
                grantViewModel.deleteGrant(grant);
            });
            dialog.setNegativeButton("No", (dialog13, which) -> dialog13.dismiss());
            dialog.setNeutralButton(R.string.cancel, (dialog12, which) -> dialog12.dismiss());
            dialog.show();
        });

        View view1 = activity.getLayoutInflater().inflate(R.layout.dialog_add_grant, null);

        holder.viewEditGrant.setOnClickListener(v -> {

            ViewGroup parent = (ViewGroup) view1.getParent();
            if (parent != null)
                parent.removeAllViews();

            TextInputLayout textInputLayoutNewGrant = view1.findViewById(R.id.textInputLayoutNewGrant);
            textInputLayoutNewGrant.setHint(grant.getGrant_name());
            textInputLayoutNewGrant.setEnabled(false);

            Slider slider = view1.findViewById(R.id.sliderGrantPoints);
            slider.setValue(grant.getCant_points());
            slider.addOnChangeListener(new Slider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    slider.setValue(Integer.valueOf((int) slider.getValue()));
                }
            });

            AlertDialog dialog = new AlertDialog.Builder(activity)
                    .setTitle(R.string.edit_grant)
                    .setPositiveButton(R.string.accept, null)
                    .setNegativeButton(R.string.cancel, null)
                    .setView(view1)
                    .show();

            Button buttonPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            buttonPos.setOnClickListener(v1 -> {
                grant.setCant_points((int) slider.getValue());
                grantViewModel.updateGrant(grant);
                dialog.dismiss();
            });
            Button buttonNeg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            buttonNeg.setOnClickListener(v12 -> {
                dialog.dismiss();
            });
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView TextViewGrantName;
        private final MaterialTextView textViewGrantPoints;
        private final MaterialButton viewEditGrant;
        private final MaterialButton viewDeleteGrant;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TextViewGrantName = itemView.findViewById(R.id.TextViewGrantName);
            textViewGrantPoints = itemView.findViewById(R.id.textViewGrantPoints);
            viewEditGrant = itemView.findViewById(R.id.viewEditGrant);
            viewDeleteGrant = itemView.findViewById(R.id.viewDeleteGrant);
        }
    }

    public static class GrantDiff extends DiffUtil.ItemCallback<Grant> {

        @Override
        public boolean areItemsTheSame(@NonNull Grant oldItem, @NonNull Grant newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Grant oldItem, @NonNull Grant newItem) {
            return oldItem.getGrant_name().equals(newItem.getGrant_name());
        }
    }

}
