package com.example.volleyexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UserAdapter.ItemRowActionListener {

    RecyclerView recyclerView;
    String url = "https://jaladhi-server.herokuapp.com/getAllUsers";
    List<User> userList = new ArrayList<>();
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        userAdapter = new UserAdapter(this, userList);
        recyclerView.setAdapter(userAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addItem) {
            UserInputFragment userInputFragment = new UserInputFragment();
            userInputFragment.show(getSupportFragmentManager(), "User_Input");
        }
        return super.onOptionsItemSelected(item);
    }

    public void showUsers(View view) {
        JsonRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Users");

                    userList.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        User user = new User();
                        user.setUserId(jsonObject.getInt("userId"));
                        user.setName(jsonObject.getString("userName"));
                        user.setEmail(jsonObject.getString("emailId"));
                        user.setPassword(jsonObject.getString("password"));
                        user.setPhoneNum(jsonObject.getString("phNumber"));
                        userList.add(user);
                    }
                    Collections.sort(userList, User.UserIdComparator);
                    userAdapter.setUserList(userList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.getMessage());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onEditButtonClick(int position) {
        UserEditFragment userInputFragment = UserEditFragment.createInstance(userList.get(position));
        userInputFragment.show(getSupportFragmentManager(), "User_Edit");
    }

    @Override
    public void onDeleteButtonClick(int position) {
        deleteUser(userList.get(position));
    }

    private void deleteUser(final User user) {

        String url = "https://jaladhi-server.herokuapp.com/deleteUser/" + user.getUserId();
        JsonRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(MainActivity.this, "Delete Success", Toast.LENGTH_LONG).show();
                userList.remove(user);
                userAdapter.setUserList(userList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", "Error " + error.getMessage());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
