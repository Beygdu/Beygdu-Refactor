package is.example.aj.beygdu.UIElements;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by arnar on 2/8/2016.
 */
public class CustomDialog extends DialogFragment {

    CustomDialogListener dialogListener;
    private int[] responseIds;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(getArguments().getString("title"));

        responseIds = getArguments().getIntArray("responses");

        builder.setItems(getArguments().getStringArray("arguments"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogListener.onDialogClick(responseIds[which]);
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            dialogListener = (CustomDialogListener) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " needs to implement a DialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dialogListener = null;
    }

    public interface CustomDialogListener {
        void onDialogClick(int position);
    }

}
