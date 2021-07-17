package cu.cujae.gilsoft.tykeprof.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import cu.cujae.gilsoft.tykeprof.R;

//CLASE DE AYUDA PARA LA GESTIÓN DE TOASTS PERSONALIZADOS
public class ToastHelper {


    public static void showCustomToast(AppCompatActivity activity, String type, String message) {
        Toast toast = new Toast(activity);
        toast.setDuration(Toast.LENGTH_SHORT);
        View custom_toast = activity.getLayoutInflater().inflate(R.layout.custom_toast, null);
        CardView cardView = custom_toast.findViewById(R.id.cardViewCustomToast);
        MaterialTextView textView = custom_toast.findViewById(R.id.textViewToast);
        ImageView imageView = custom_toast.findViewById(R.id.imageViewIconToast);

        switch (type) {
            case "success": {
                textView.setText(message);
            }
            break;
            case "error": {
                cardView.setCardBackgroundColor(activity.getResources().getColor(R.color.colorRedTyke));
                imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_error_red));
                textView.setText(message);
            }
            break;
            case "warning": {
                cardView.setCardBackgroundColor(activity.getResources().getColor(R.color.colorYellowTyke));
                imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_warning_yellow));
                textView.setText(message);
            }
            break;
        }
        toast.setView(custom_toast);
        toast.show();
    }
}
