package com.android.arc.Pages;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.arc.R;
import com.android.arc.Remote.ArcService;
import com.android.arc.model.ApiUtils;
import com.android.arc.model.User;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends Fragment implements View.OnClickListener{

    private Button login;
    private ArcService mService;
    private EditText Username;
    private EditText Password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.login_page, container, false);
        login = rootView.findViewById(R.id.login_button);
        Username = rootView.findViewById(R.id.username);
        Password = rootView.findViewById(R.id.password);
        login.setOnClickListener(this);
        mService = ApiUtils.getArcService();

        return rootView;
    }

    @Override
    public void onClick(View view) {
        final String user = Username.getText().toString();

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        mService.getUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    if (validate(response.body().getPassword())) {
                        editor.putInt(getString(R.string.logged), 1);
                        editor.commit();
                        enter();
                    }
                    else
                        Username.setText("Wrong Password");
                }catch(Exception e){
                    Username.setText("Incorrect credentials");
                    editor.putInt(getString(R.string.logged), 0);
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private boolean validate(String passW){
        return passW.equals(Password.getText().toString());
    }

    private void enter(){
        StartPage startPage = new StartPage();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, startPage).addToBackStack(null).commit();
    }
}
