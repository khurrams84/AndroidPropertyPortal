package com.mhaseeb.property.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mhaseeb.property.R;
import com.mhaseeb.property.BaseActivity;
import com.mhaseeb.property.common.utils.StringUtil;
import com.mhaseeb.property.home.HomeActivity;
import com.mhaseeb.property.login.model.LoginRequestModel;
import com.mhaseeb.property.login.presenter.LoginPresenterImp;
import com.mhaseeb.property.register.RegisterActivity;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginContract.View {
    private EditText etEmail, etPassword;
    private TextView tvSignUp;
    private Button btnLogin, btnGuest;
    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvSignUp = findViewById(R.id.tvSignUp);
        btnLogin = findViewById(R.id.btnLogin);
        btnGuest = findViewById(R.id.btnGuest);
        tvSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnGuest.setOnClickListener(this);
        presenter = new LoginPresenterImp(this, this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSignUp:
                presenter.onRegisterClick();
                break;
            case R.id.btnLogin:
                login();
                break;
            case R.id.btnGuest:
                presenter.onGuestLoginClick();
                goToHome();
                break;
        }

    }

    private void login() {
        if (validate()) {
            LoginRequestModel model = new LoginRequestModel();
            model.setEmail(etEmail.getText().toString().trim());
            model.setPassword(etPassword.getText().toString().trim());
            presenter.onLoginClick(model);
        }
    }

    /**
     * Method to validate user inputs
     *
     * @return
     */
    private boolean validate() {

        if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return false;
        }
        if (!StringUtil.isValidEmail(etEmail.getText().toString().trim())) {
            etEmail.setError("Invalid email address");
            etEmail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onRegisterClick() {
        goToRegister();
    }

    @Override
    public void onLoginSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        goToHome();
        finish();

    }

    private void goToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void goToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginFailure() {
//        Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show();
    }
}
