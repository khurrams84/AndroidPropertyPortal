package com.mhaseeb.property.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.mhaseeb.property.R;
import com.mhaseeb.property.ui.BaseActivity;
import com.mhaseeb.property.ui.common.utils.StringUtil;
import com.mhaseeb.property.ui.home.HomeActivity;
import com.mhaseeb.property.ui.register.models.RegisterRequestModel;
import com.mhaseeb.property.ui.register.presenter.RegisterPresenterImp;

public class RegisterActivity extends BaseActivity implements RegisterContract.View, View.OnClickListener {
    private EditText etFirstName, etlastName, etPhone, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;
    private RadioButton rbMale, rbFemale;
    RegisterContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        etFirstName = findViewById(R.id.etFirstName);
        etlastName = findViewById(R.id.etlastName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        presenter = new RegisterPresenterImp(this, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                if (validate()) {
                    RegisterRequestModel model = new RegisterRequestModel();
                    model.setFirstName(etFirstName.getText().toString().trim());
                    model.setLastName(etlastName.getText().toString().trim());
                    model.setPhone(etPhone.getText().toString().trim());
                    model.setEmail(etEmail.getText().toString().trim());
                    model.setPassword(etPassword.getText().toString().trim());
                    model.setGender(getSelectedGender());
                    presenter.onRegisterButtonClicked(model);
                }
                break;
        }
    }

    /**
     * Method to get selected gender from radio buttons
     *
     * @return
     */
    private String getSelectedGender() {
        if (rbMale.isChecked())
            return getString(R.string.Male);
        else
            return getString(R.string.Female);
    }

    /**
     * Method to validate user inputs
     *
     * @return
     */
    private boolean validate() {
        if (TextUtils.isEmpty(etFirstName.getText().toString().trim())) {
            etFirstName.setError(getString(R.string.code_first_required));
            etFirstName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etlastName.getText().toString().trim())) {
            etlastName.setError(getString(R.string.code_last_required));
            etlastName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etPhone.getText().toString().trim())) {
            etPhone.setError(getString(R.string.code_phone_required));
            etPhone.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
            etEmail.setError(getString(R.string.code_email_required));
            etEmail.requestFocus();
            return false;
        }
        if (!StringUtil.isValidEmail(etEmail.getText().toString().trim())) {
            etEmail.setError(getString(R.string.code_invalid_email_required));
            etEmail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
            etPassword.setError(getString(R.string.code_password_required));
            etPassword.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etConfirmPassword.getText().toString().trim())) {
            etConfirmPassword.setError(getString(R.string.code_confirm_password_required));
            etConfirmPassword.requestFocus();
            return false;
        }
        if (!etConfirmPassword.getText().toString().trim().equals(etPassword.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.code_password_notmatched), Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    @Override
    public void onRegisterSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        goToHome();
        finish();
    }

    @Override
    public void onRegisterFailure() {

    }

    private void goToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
