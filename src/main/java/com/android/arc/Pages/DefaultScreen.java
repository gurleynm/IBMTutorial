package com.android.arc.Pages;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.arc.R;
import com.android.arc.utils.Utility;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DefaultScreen extends Fragment implements View.OnClickListener{
    private Button registerButton;
    private Button loginButton;
    private boolean loggedIn;

    public DefaultScreen(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.default_screen, container, false);
        registerButton = rootView.findViewById(R.id.register_button);
        loginButton = rootView.findViewById(R.id.login_button);
        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        loggedIn = sharedPref.getInt(getString(R.string.logged), 0) != 0;
        if(loggedIn){
            StartPage startPage = new StartPage();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, startPage).addToBackStack(null).commit();
        }
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(Utility.isConnectedToNetwork(getContext())) {
            switch (v.getId()) {
                case R.id.register_button:
                    RegisterPage registerPage = new RegisterPage();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, registerPage).addToBackStack(null).commit();
                    break;
                case R.id.login_button:
                    LoginPage loginPage = new LoginPage();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, loginPage).addToBackStack(null).commit();
                    break;
            }
        }
    }


}
