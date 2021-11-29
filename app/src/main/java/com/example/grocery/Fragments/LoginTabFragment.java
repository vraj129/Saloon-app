package com.example.grocery.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.grocery.MainActivity;
import com.example.grocery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginTabFragment extends Fragment {

    EditText email,password;
    Button logbtn;
    FirebaseAuth auth;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.logintab_fragment,container,false);
        BindView(root);
        auth = FirebaseAuth.getInstance();
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
        return root;
    }

    private void loginUser() {
        String user_email = email.getText().toString();
        String user_pass = password.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(TextUtils.isEmpty(user_email))
        {
            Toast.makeText(getActivity(), "Email is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            if(!user_email.trim().matches(emailPattern))
            {
                Toast.makeText(getActivity(), "Email is Invalid", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(TextUtils.isEmpty(user_pass))
        {
            Toast.makeText(getActivity(), "Password is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(user_pass.length() < 6)
        {
            Toast.makeText(getActivity(), "Password Lenght should be Greater Than 6", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(user_email,user_pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity(), MainActivity.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Login Unsuccessful"+task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void BindView(ViewGroup root)
    {
        email = root.findViewById(R.id.user_mail);
        password = root.findViewById(R.id.user_pass);
        logbtn = root.findViewById(R.id.login);
    }
}
