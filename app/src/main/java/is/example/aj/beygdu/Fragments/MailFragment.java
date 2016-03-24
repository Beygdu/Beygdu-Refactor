package is.example.aj.beygdu.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import is.example.aj.beygdu.FragmentCallback;
import is.example.aj.beygdu.R;

// TODO : implement
public class MailFragment extends Fragment {

    public static final int FRAGMENT_ID = 3;

    private Spinner spinner;
    private EditText subject;
    private EditText content;

    private FragmentCallback fragmentCallback;

    public MailFragment() {
        // Required empty public constructor
    }

    public static MailFragment newInstance() {
        MailFragment fragment = new MailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            // (Probably) Nothing needs to be saved/re-instantiated here
            // Is here as a rule of thumb
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mail, container, false);

        spinner = (Spinner) v.findViewById(R.id.mail_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.mail_mailto, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);

        subject = (EditText) v.findViewById(R.id.mail_subject);
        content = (EditText) v.findViewById(R.id.mail_content);

        Bundle bundle = getArguments();

        if(bundle != null) {
            try {
                subject.setText(bundle.getString("subject"));
                content.setText(bundle.getString("content"));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        Button cancelButton = (Button) v.findViewById(R.id.mail_cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCallback.onFragmentSwitch(-1);
            }
        });

        Button okButton = (Button) v.findViewById(R.id.mail_okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEmail()) {
                    sendEmail(spinner.getSelectedItem().toString(),
                            subject.getText().toString(),
                            content.getText().toString());
                }
                else {
                    fragmentCallback.onDebugCallback(onInvalidatedContent());
                }
            }
        });

        return v;
    }

    private boolean validateEmail() {
        return subject.getText().length() != 0 && content.getText().length() != 0;
    }

    private void sendEmail(String address, String subject, String content) {
        Intent mailIntent = new Intent(Intent.ACTION_SEND);
        mailIntent.setType("plain/text");
        mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"arnar_jons@hotmail.com" });
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        mailIntent.putExtra(Intent.EXTRA_TEXT, content);
        startActivity(Intent.createChooser(mailIntent, getContext().getResources().getString(R.string.mail_email_client)));
        fragmentCallback.onFragmentSwitch(-1);
    }

    private String onInvalidatedContent() {
        if(subject.getText().length() == 0) {
            if(content.getText().length() == 0) {
                return getContext().getResources().getString(R.string.mail_invalid_subject_content);
            }
            return getContext().getResources().getString(R.string.mail_invalid_subject);
        }

        if(content.getText().length() == 0) {
            return getContext().getResources().getString(R.string.mail_invalid_content);
        }

        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentCallback) {
            fragmentCallback = (FragmentCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentCallback");
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentCallback = null;
    }

    @Override
    public void onSaveInstanceState(Bundle instanceState) {

        // (Probably) Nothing needs to be saved before orientation switch

        super.onSaveInstanceState(instanceState);
    }
}
