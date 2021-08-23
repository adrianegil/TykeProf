package cu.cujae.gilsoft.tykeprof.util;

import android.app.Activity;

import androidx.appcompat.app.AlertDialog;

import cu.cujae.gilsoft.tykeprof.R;

public class DialogHelper {

    public static void showExitDialog(Activity activity) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle(R.string.exit_confirm);
        dialog.setMessage(R.string.exit_confirm_description);
        dialog.setPositiveButton(R.string.yes, (dialog12, which) -> {
            activity.finish();
        });
        dialog.setNegativeButton("No", (dialog13, which) -> dialog13.dismiss());
        dialog.setNeutralButton(R.string.cancel, (dialog1, which) -> dialog1.dismiss());
        dialog.show();
    }

}
