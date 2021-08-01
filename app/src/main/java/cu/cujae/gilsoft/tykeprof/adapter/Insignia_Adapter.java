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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.GiftViewModel;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.InsigniaViewModel;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift;
import cu.cujae.gilsoft.tykeprof.data.entity.Insignia;
import cu.cujae.gilsoft.tykeprof.data.entity.Professional_Rol;
import cu.cujae.gilsoft.tykeprof.data.model.Insignia_Model;
import cu.cujae.gilsoft.tykeprof.repository.Gift_Type_Repository;
import cu.cujae.gilsoft.tykeprof.repository.Grant_Repository;
import cu.cujae.gilsoft.tykeprof.repository.Professional_Rol_Repository;

public class Insignia_Adapter extends ListAdapter<Insignia, Insignia_Adapter.ViewHolder> {

    private Professional_Rol_Repository professional_rol_repository;
    InsigniaViewModel insigniaViewModel;
    Activity activity;


    public Insignia_Adapter(@NonNull DiffUtil.ItemCallback<Insignia> diffCallback, InsigniaViewModel insigniaViewModel, Activity activity, Application application) {
        super(diffCallback);
        this.insigniaViewModel = insigniaViewModel;
        this.activity = activity;
        this.professional_rol_repository = new Professional_Rol_Repository(application);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.insignia_itemlist, parent, false);
        return new Insignia_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Insignia insignia = getItem(position);

        holder.textViewInsigniaName.setText(insignia.getName());
        holder.textViewAdvancePointsOfInsignia.setText("Puntos de Avance: "+ insignia.getAdvance_points());
        holder.textViewGrantPointsOfInsignia.setText("Puntos a Otorgar; " + insignia.getGrant_points());
        holder.layoutExpanded.setVisibility(insignia.isContenExpandable() ? View.VISIBLE : View.GONE);

        holder.viewDeleteInsignia.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
            dialog.setTitle(R.string.delete_insignia);
            dialog.setMessage(R.string.confirm_delete_insignia);
            dialog.setPositiveButton(R.string.yes, (dialog1, which) -> {
                insigniaViewModel.deleteInsignia(insignia.getId_insignia());
            });
            dialog.setNegativeButton("No", (dialog13, which) -> dialog13.dismiss());
            dialog.setNeutralButton(R.string.cancel, (dialog12, which) -> dialog12.dismiss());
            dialog.show();
        });

        Professional_Rol professional_rol = professional_rol_repository.getProfessRolLocalbyId(insignia.getId_profess_rol());

        holder.textViewProfessRolOfInsignia.setText("Rol Profesional: "+ professional_rol.getName());

        holder.viewEditInsignia.setOnClickListener(v -> {

            View view1 = activity.getLayoutInflater().inflate(R.layout.dialog_add_insignia, null);

            /*ViewGroup parent = (ViewGroup) view1.getParent();
            if (parent != null)
                parent.removeAllViews();*/

            TextInputLayout textInputLayoutNewInsignia = view1.findViewById(R.id.textInputLayoutNewInsignia);
            EditText editTextInsigniaName = view1.findViewById(R.id.editTextInsigniaName);

            TextInputLayout textInputLayoutProfessRolToInsignia = view1.findViewById(R.id.textInputLayoutProfessRolToInsignia);
            AutoCompleteTextView autoCompleteProfessRolToInsignia = view1.findViewById(R.id.autoCompleteProfessRolToInsignia);

            editTextInsigniaName.setText(insignia.getName());

            Slider sliderAdvancePoints = view1.findViewById(R.id.sliderAdvancePoints);
            sliderAdvancePoints.addOnChangeListener(new Slider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    sliderAdvancePoints.setValue((int) slider.getValue());
                }
            });
            sliderAdvancePoints.setValue(insignia.getAdvance_points());

            Slider sliderGrantPoints = view1.findViewById(R.id.sliderGrantPoints);
            sliderGrantPoints.addOnChangeListener(new Slider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    sliderGrantPoints.setValue((int) slider.getValue());
                }
            });
            sliderGrantPoints.setValue(insignia.getGrant_points());

           ArrayList professional_rols = (ArrayList<Professional_Rol>) professional_rol_repository.getAllProfessionalRolList();

            ArrayAdapter<Professional_Rol> professRolAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, professional_rols);
            autoCompleteProfessRolToInsignia.setAdapter(professRolAdapter);
            autoCompleteProfessRolToInsignia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Professional_Rol professional_rol = (Professional_Rol) autoCompleteProfessRolToInsignia.getAdapter().getItem(position);
                    insignia.setId_profess_rol(professional_rol.getId_profess_rol());
                    insignia.setProfessional_rol(professional_rol);
                }
            });

            AlertDialog dialog = new AlertDialog.Builder(activity)
                    .setTitle(R.string.new_insignia)
                    .setPositiveButton(R.string.accept, null)
                    .setNegativeButton(activity.getString(R.string.cancel), null)
                    .setView(view1)
                    .show();

            Button buttonPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            buttonPos.setOnClickListener(v1 -> {
                String stringInsigniaName = editTextInsigniaName.getText().toString();
                String stringProfessRol = autoCompleteProfessRolToInsignia.getText().toString();

                if (stringInsigniaName.isEmpty() || stringProfessRol.isEmpty()) {
                    if (stringInsigniaName.isEmpty())
                        textInputLayoutNewInsignia.setError(activity.getString(R.string.required));
                    if (stringProfessRol.isEmpty())
                        textInputLayoutProfessRolToInsignia.setError(activity.getString(R.string.required));
                } else {
                    insignia.setName(stringInsigniaName);
                    insignia.setAdvance_points((int) sliderAdvancePoints.getValue());
                    insignia.setGrant_points((int) sliderGrantPoints.getValue());
                    insigniaViewModel.updateInsignia(insignia);
                    dialog.dismiss();
                }
            });
            Button buttonNeg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            buttonNeg.setOnClickListener(v12 -> {
                //editTextInsigniaName.setText("");
                //autoCompleteProfessRolToInsignia.setText("");
                sliderAdvancePoints.setValue(1);
                sliderGrantPoints.setValue(1);
                dialog.dismiss();
            });
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView textViewInsigniaName;
        private final MaterialTextView textViewProfessRolOfInsignia;
        private final MaterialTextView textViewGrantPointsOfInsignia;
        private final MaterialTextView textViewAdvancePointsOfInsignia;

        private final MaterialButton viewLookInsigniaContent;
        private final MaterialButton viewEditInsignia;
        private final MaterialButton viewDeleteInsignia;
        private final ConstraintLayout layoutExpanded;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewInsigniaName = itemView.findViewById(R.id.textViewInsigniaName);
            textViewProfessRolOfInsignia = itemView.findViewById(R.id.textViewProfessRolOfInsignia);
            textViewGrantPointsOfInsignia = itemView.findViewById(R.id.textViewGrantPointsOfInsignia);
            textViewAdvancePointsOfInsignia = itemView.findViewById(R.id.textViewAdvancePointsOfInsignia);
            viewLookInsigniaContent = itemView.findViewById(R.id.viewLookInsigniaContent);
            viewEditInsignia = itemView.findViewById(R.id.viewEditInsignia);
            viewDeleteInsignia = itemView.findViewById(R.id.viewDeleteInsignia);
            layoutExpanded = itemView.findViewById(R.id.layoutExpandedInsigniaContent);

            viewLookInsigniaContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Insignia insignia = Insignia_Adapter.this.getCurrentList().get(getAdapterPosition());
                    insignia.setContenExpandable(!insignia.isContenExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }

    public static class InsigniaDiff extends DiffUtil.ItemCallback<Insignia> {

        @Override
        public boolean areItemsTheSame(@NonNull Insignia oldItem, @NonNull Insignia newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Insignia oldItem, @NonNull Insignia newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }

}
