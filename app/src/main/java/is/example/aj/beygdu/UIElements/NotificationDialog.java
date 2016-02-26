package is.example.aj.beygdu.UIElements;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import is.example.aj.beygdu.R;

/**
 * Created by arnar on 2/25/2016.
 */
public class NotificationDialog extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstaceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if(getArguments().getBoolean("isError")) {
            builder.setTitle(getResources().getString(R.string.error));
        }
        else {
            builder.setTitle(getResources().getString(R.string.notification));
        }

        builder.setMessage(getArguments().getString("message"));

        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
