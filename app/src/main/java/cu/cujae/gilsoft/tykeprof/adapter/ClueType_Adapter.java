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
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.ClueTypeViewModel;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.QuestionTypeViewModel;
import cu.cujae.gilsoft.tykeprof.data.AppDatabase;
import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Question_Type;
import cu.cujae.gilsoft.tykeprof.databinding.ClueTypeFragmentBinding;
import cu.cujae.gilsoft.tykeprof.util.ToastHelper;

public class ClueType_Adapter extends ListAdapter<Clue_Type, ClueType_Adapter.ViewHolder> {

    ClueTypeViewModel clueTypeViewModel;
    Activity activity;

    public ClueType_Adapter(@NonNull DiffUtil.ItemCallback<Clue_Type> diffCallback, ClueTypeViewModel clueTypeViewModel, Activity activity) {
        super(diffCallback);
        this.clueTypeViewModel = clueTypeViewModel;
        this.activity = activity;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.clue_type_itemlist, parent, false);
        return new ClueType_Adapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Clue_Type clue_type = getItem(position);

        holder.TextViewClueType.setText(clue_type.getType());
        holder.textViewGamePoints.setText(activity.getString(R.string.games_points) + ": " + clue_type.getGamePoints());
        holder.textViewCluePoints.setText(activity.getString(R.string.clue_points) + ": " + clue_type.getCluePoints());

        View view1 = activity.getLayoutInflater().inflate(R.layout.dialog_add_clue_type, null);

        holder.viewDeleteClueType.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
            dialog.setTitle(R.string.delete_clue_type);
            dialog.setMessage(R.string.confirm_delete_cluetype);

            dialog.setPositiveButton(R.string.yes, (dialog1, which) -> {
                clueTypeViewModel.deleteClueType(clue_type);
            });
            dialog.setNegativeButton("No", (dialog13, which) -> dialog13.dismiss());
            dialog.setNeutralButton(R.string.cancel, (dialog12, which) -> dialog12.dismiss());
            dialog.show();
        });

        holder.viewEditClueType.setOnClickListener(v -> {

            ViewGroup parent = (ViewGroup) view1.getParent();
            if (parent != null)
                parent.removeAllViews();

            TextInputLayout textInputLayoutClueType = view1.findViewById(R.id.textInputLayoutClueType);
            textInputLayoutClueType.setVisibility(View.GONE);

            TextInputLayout textInputLayoutGamePoints = view1.findViewById(R.id.textInputLayoutGamePoints);
            EditText editTextGamePoints = view1.findViewById(R.id.editTextGamePoints);
            editTextGamePoints.setText(clue_type.getGamePoints().toString());

            TextInputLayout textInputLayoutCluePoints = view1.findViewById(R.id.textInputLayoutCluePoints);
            EditText editTextCluePoints = view1.findViewById(R.id.editTextCluePoints);
            editTextCluePoints.setText(clue_type.getCluePoints().toString());

            // textInputLayoutClueType.setHint(clue_type.getType());
            // textInputLayoutClueType.setEnabled(false);

            AlertDialog dialog = new AlertDialog.Builder(activity)
                    .setTitle(activity.getString(R.string.edit_clue_type) + ": " + clue_type.getType())
                    //.setMessage("Inserte el nombre de la Estrategia")
                    .setPositiveButton(R.string.accept, null)
                    .setNegativeButton(R.string.cancel, null)
                    .setView(view1)
                    .show();

            Button buttonPos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            buttonPos.setOnClickListener(v1 -> {
                String stringGamePoints = editTextGamePoints.getText().toString();
                String stringCluePoints = editTextCluePoints.getText().toString();
                if (stringGamePoints.isEmpty() || stringCluePoints.isEmpty()) {
                    if (stringGamePoints.isEmpty())
                        textInputLayoutGamePoints.setError(activity.getString(R.string.required));
                    if (stringCluePoints.isEmpty())
                        textInputLayoutCluePoints.setError(activity.getString(R.string.required));
                } else {
                    clue_type.setGamePoints(Integer.valueOf(stringGamePoints));
                    clue_type.setCluePoints(Integer.valueOf(stringCluePoints));
                    clueTypeViewModel.updateClueType(clue_type);
                    dialog.dismiss();
                }
            });
            Button buttonNeg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            buttonNeg.setOnClickListener(v12 -> {
                dialog.dismiss();
            });
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView TextViewClueType;
        private final MaterialTextView textViewGamePoints;
        private final MaterialTextView textViewCluePoints;
        private final MaterialButton viewDeleteClueType;
        private final MaterialButton viewEditClueType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TextViewClueType = itemView.findViewById(R.id.TextViewClueType);
            textViewGamePoints = itemView.findViewById(R.id.textViewGamePoints);
            textViewCluePoints = itemView.findViewById(R.id.textViewCluePoints);
            viewDeleteClueType = itemView.findViewById(R.id.viewDeleteClueType);
            viewEditClueType = itemView.findViewById(R.id.viewEditClueType);
        }
    }

    public static class ClueTypeDiff extends DiffUtil.ItemCallback<Clue_Type> {

        @Override
        public boolean areItemsTheSame(@NonNull Clue_Type oldItem, @NonNull Clue_Type newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Clue_Type oldItem, @NonNull Clue_Type newItem) {
            return oldItem.getType().equals(newItem.getType());
        }
    }
}
