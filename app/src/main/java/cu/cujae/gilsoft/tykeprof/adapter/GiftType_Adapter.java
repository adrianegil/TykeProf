package cu.cujae.gilsoft.tykeprof.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.GiftTypeViewModel;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.QuestionTypeViewModel;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Question_Type;

public class GiftType_Adapter extends ListAdapter<Gift_Type, GiftType_Adapter.ViewHolder> {

    GiftTypeViewModel giftTypeViewModel;
    Context context;

    public GiftType_Adapter(@NonNull DiffUtil.ItemCallback<Gift_Type> diffCallback, GiftTypeViewModel giftTypeViewModel, Context context) {
        super(diffCallback);
        this.giftTypeViewModel = giftTypeViewModel;
        this.context = context;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gift_type_itemlist, parent, false);
        return new GiftType_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Gift_Type gift_type = getItem(position);
        holder.materialTextViewGiftType.setText(gift_type.getName());
        holder.viewDeleteGiftType.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("Eliminar Tipo de Regalo");
            dialog.setMessage("Desea realmente eliminar este Tipo de Regalo");
            dialog.setPositiveButton(R.string.yes, (dialog1, which) -> {
                giftTypeViewModel.deleteGiftType(gift_type);
            });
            dialog.setNegativeButton("No", (dialog13, which) -> dialog13.dismiss());
            dialog.setNeutralButton(R.string.cancel, (dialog12, which) -> dialog12.dismiss());
            dialog.show();
        });
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView materialTextViewGiftType;
        private final MaterialButton viewDeleteGiftType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            materialTextViewGiftType = itemView.findViewById(R.id.materialTextViewGiftType);
            viewDeleteGiftType = itemView.findViewById(R.id.viewDeleteGiftType);
        }
    }

    public static class GiftTypeDiff extends DiffUtil.ItemCallback<Gift_Type> {

        @Override
        public boolean areItemsTheSame(@NonNull Gift_Type oldItem, @NonNull Gift_Type newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Gift_Type oldItem, @NonNull Gift_Type newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}
