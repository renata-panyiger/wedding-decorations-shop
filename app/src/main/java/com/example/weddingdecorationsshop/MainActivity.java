package com.example.weddingdecorationsshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity
        extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 99;

    private EditText emailEditText;
    private EditText passwordEditText;
    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        preferences= getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();

        Button guestButton = findViewById(R.id.guestLoginButton);
        new RandomAsyncTask(guestButton).execute();

        getSupportLoaderManager().restartLoader(0, null, this);

        Log.i(LOG_TAG, "onCreate");
    }

    public void login(View view) {
        String userName = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (userName.isEmpty() || password.isEmpty()) {
            Log.d(LOG_TAG, "Username or password is empty.");
            return;
        }

        mAuth.signInWithEmailAndPassword(userName, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Log.d(LOG_TAG, "User login successfully");
                startShopping();
            } else {
                Log.d(LOG_TAG, "User login fail");
                Toast.makeText(MainActivity.this, "User login fail: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Log.i(LOG_TAG, "Logged in: " + userName + ", password: " + password);
    }

    public void guestLogin(View view) {
        mAuth.signInAnonymously().addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Log.d(LOG_TAG, "Guest user login successfully");
                startShopping();
            } else {
                Log.d(LOG_TAG, "Guest user login fail");
                Toast.makeText(MainActivity.this, "User login fail: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        // TODO: regisztracio megvalositasa

        startActivity(intent);
    }

    private void startShopping() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userName", emailEditText.getText().toString());
        editor.putString("password", passwordEditText.getText().toString());
        editor.apply();

        Log.i(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG, "onRestart");
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new RandomAsyncLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setText(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {}
}