package com.example.volleyexample;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserEditFragment extends DialogFragment {

    EditText etUserName, etEmail, etPassword, etPhoneNum;
    Button btnRegister;

    String url = "https://jaladhi-server.herokuapp.com/updateUser";
    Bundle userBundle;
    User user;

    public static UserEditFragment createInstance(User user) {
        UserEditFragment userEditFragment = new UserEditFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        userEditFragment.setArguments(bundle);
        return userEditFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.ThemeOverlay_AppCompat_Dialog_Alert);
        userBundle = getArguments();
        user = (User) userBundle.getSerializable("user");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_user_input, container, false);
        etUserName = view.findViewById(R.id.etUserName);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        etPhoneNum = view.findViewById(R.id.etPhoneNum);
        btnRegister = view.findViewById(R.id.btnRegister);
        etUserName.setText(user.getName());
        etEmail.setText(user.getEmail());
        etPassword.setText(user.getPassword());
        etPhoneNum.setText(user.getPhoneNum());
        btnRegister.setText("Edit User Data");
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editRegistrationData();
            }
        });
        return view;
    }

    private void editRegistrationData() {
        JSONObject jsonObject = new JSONObject();
        int id = user.getUserId();
        String name = etUserName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String phoneNum = etPhoneNum.getText().toString();

        try {
            jsonObject.put("userId", id);
            jsonObject.put("userName", name);
            jsonObject.put("emailId", email);
            jsonObject.put("password", password);
            jsonObject.put("phNumber", phoneNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity(), "Edit Success", Toast.LENGTH_LONG).show();
                        Log.v("Edit", response.toString());
                        dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("Volley Error", "Error " + error.getMessage());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
