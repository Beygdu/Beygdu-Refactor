package is.example.aj.beygdu.UIElements;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import is.example.aj.beygdu.R;

/**
 * Created by arnar on 2/8/2016.
 */
public class CustomDialog extends DialogFragment {

    CustomDialogListener dialogListener;
    private int state;
    private String[] arguments;
    private int[] responseIds;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        state = getArguments().getInt("state");

        builder.setTitle(getArguments().getString("title"));

        arguments = getArguments().getStringArray("arguments");
        responseIds = getArguments().getIntArray("responses");

        builder.setItems(arguments, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(state == 0) {
                    dialogListener.onDialogClick(arguments[which], responseIds[which]);
                }
                else {
                    dialogListener.onDialogClick(arguments[which]);
                }
            }
        });

        builder.setNeutralButton(getActivity().getBaseContext().getResources().getString(R.string.customdialog_cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
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
        void onDialogClick(String str, int id);
        void onDialogClick(String str);
    }

}
