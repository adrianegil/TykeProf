package cu.cujae.gilsoft.tykeprof.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.GiftViewModel;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.ProfessionalRolViewModel;
import cu.cujae.gilsoft.tykeprof.data.entity.Career;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Grant;
import cu.cujae.gilsoft.tykeprof.data.entity.Professional_Rol;
import cu.cujae.gilsoft.tykeprof.data.entity.Topic;
import cu.cujae.gilsoft.tykeprof.repository.Career_Repository;
import cu.cujae.gilsoft.tykeprof.repository.Gift_Type_Repository;
import cu.cujae.gilsoft.tykeprof.repository.Grant_Repository;
import cu.cujae.gilsoft.tykeprof.repository.Topic_Repository;

public class ProfessionalRol_Adapter extends ListAdapter<Professional_Rol, ProfessionalRol_Adapter.ViewHolder> {

    private ProfessionalRolViewModel professionalRolViewModel;
    private Activity activity;
    private Career_Repository career_repository;
    private Topic_Repository topic_repository;

    public ProfessionalRol_Adapter(@NonNull DiffUtil.ItemCallback<Professional_Rol> diffCallback, ProfessionalRolViewModel professionalRolViewModel, Activity activity, Application application) {
        super(diffCallback);
        this.professionalRolViewModel = professionalRolViewModel;
        this.activity = activity;
        this.career_repository = new Career_Repository(application);
        this.topic_repository = new Topic_Repository(application);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.professional_rol_itemlist, parent, false);
        return new ProfessionalRol_Adapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Professional_Rol professional_rol = getItem(position);
        holder.textViewNameOfProfessRol.setText(professional_rol.getName());
        holder.layoutExpanded.setVisibility(professional_rol.isContenExpandable() ? View.VISIBLE : View.GONE);

        holder.viewDeleteProfessRol.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
            dialog.setTitle(R.string.delete_profess_rol);
            dialog.setMessage(R.string.confirm_delete_profess_rol);
            dialog.setPositiveButton(R.string.yes, (dialog1, which) -> {
                professionalRolViewModel.deleteProfessionalRol(professional_rol.getId_profess_rol());
            });
            dialog.setNegativeButton("No", (dialog13, which) -> dialog13.dismiss());
            dialog.setNeutralButton(R.string.cancel, (dialog12, which) -> dialog12.dismiss());
            dialog.show();
        });

        Topic topic = topic_repository.getTopicbyId(professional_rol.getId_topic());
        Career career = career_repository.getCareerbyId(professional_rol.getId_career());
        holder.textViewCareerOfProfessRol.setText(activity.getString(R.string.career) + ": " + career.getName());
        holder.textViewTopicOfProfessRol.setText(activity.getString(R.string.topic) + ": " + topic.getName());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView textViewNameOfProfessRol;
        private final MaterialTextView textViewCareerOfProfessRol;
        private final MaterialTextView textViewTopicOfProfessRol;
        private final MaterialButton viewLookProfessRolContent;
        private final MaterialButton viewDeleteProfessRol;
        private final ConstraintLayout layoutExpanded;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameOfProfessRol = itemView.findViewById(R.id.textViewNameOfProfessRol);
            textViewCareerOfProfessRol = itemView.findViewById(R.id.textViewCareerOfProfessRol);
            textViewTopicOfProfessRol = itemView.findViewById(R.id.textViewTopicOfProfessRol);
            viewLookProfessRolContent = itemView.findViewById(R.id.viewLookProfessRolContent);
            viewDeleteProfessRol = itemView.findViewById(R.id.viewDeleteProfessRol);
            layoutExpanded = itemView.findViewById(R.id.layoutExpandedProfessRolContent);

            viewLookProfessRolContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Professional_Rol professional_rol = ProfessionalRol_Adapter.this.getCurrentList().get(getAdapterPosition());
                    professional_rol.setContenExpandable(!professional_rol.isContenExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }

    public static class ProfessionalRolDiff extends DiffUtil.ItemCallback<Professional_Rol> {

        @Override
        public boolean areItemsTheSame(@NonNull Professional_Rol oldItem, @NonNull Professional_Rol newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Professional_Rol oldItem, @NonNull Professional_Rol newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}
