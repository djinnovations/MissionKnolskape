package dj.missionknolskape.main.modulecore.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import dj.missionknolskape.main.R;
import dj.missionknolskape.main.uiutils.TypefaceHelper;
import dj.missionknolskape.main.utils.MyPrefManager;

/**
 * Created by User on 25-10-2016.
 */
public class PersonDetailFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person_detail, container, false);
    }


    @Bind(R.id.input_layout_name)
    TextInputLayout input_layout_name;
    @Bind(R.id.input_layout_email)
    TextInputLayout input_layout_email;
    @Bind(R.id.input_layout_phone)
    TextInputLayout input_layout_phone;
    @Bind(R.id.input_layout_id)
    TextInputLayout input_layout_id;

    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.email)
    EditText email;
    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.customerId)
    EditText customerId;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        TypefaceHelper.setFont(name, phone, customerId, email);
    }

    public boolean canSaveAndProceed(){
        if (validateName() && validateEmail() && validateId()){
            saveData();
            return true;
        }
        return false;
    }

    private void saveData() {
        //// TODO: 25-10-2016
        MyPrefManager pref = MyPrefManager.getInstance();
        pref.setName(name.getText().toString());
        pref.setPhone(phone.getText().toString());
        pref.setCustId(customerId.getText().toString());
        pref.setEmail(email.getText().toString());
    }


    private boolean validateEmail() {
        String id = email.getText().toString().trim();
        if (id.isEmpty() || !isValidEmail(id)) {
            input_layout_email.setError("Enter Email");
            requestFocus(email);
            return false;
        } else {
            input_layout_email.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateId() {
        if (customerId.getText().toString().trim().isEmpty()) {
            input_layout_id.setError("Enter Customer Id");
            requestFocus(customerId);
            return false;
        } else {
            input_layout_id.setErrorEnabled(false);
        }
        return true;
    }


    private boolean validateName() {
        if (name.getText().toString().trim().isEmpty()) {
            input_layout_name.setError("Enter Name");
            requestFocus(name);
            return false;
        } else {
            input_layout_name.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePhone() {
        if (phone.getText().toString().trim().isEmpty()) {
            input_layout_phone.setError("Enter Name");
            requestFocus(phone);
            return false;
        } else {
            input_layout_phone.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
