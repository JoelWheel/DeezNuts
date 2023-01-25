package com.example.appexample.DrawerFragmentClasses;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appexample.R;
import com.example.appexample.UpdateProfile;
import com.example.appexample.databinding.ActivityDrawerBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private EditText editNewTextFullName, editNewTextEmail, editNewTextPassword;
    private String firebase_email;
    private String firebase_password;
    private String firebase_username;
    private String currentUser;
    private FirebaseAuth updateAuth;
    private ProgressBar progressBar;
    private Button profile_change;

    ActivityDrawerBinding binding;
    DatabaseReference databaseReference;


    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            firebase_email = user.getEmail();
            firebase_username = user.getDisplayName();
            currentUser = user.getUid();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_changes:
                Intent intent = new Intent(ProfileFragment.this.getContext(), UpdateProfile.class);
                startActivity(intent);
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView ev = view.findViewById(R.id.text_gallery);
        TextView uv = view.findViewById(R.id.username_gallery);
        TextView pv = view.findViewById(R.id.password_gallery);

        Intent intent = getActivity().getIntent();
        firebase_email = intent.getStringExtra("email");
        firebase_password = intent.getStringExtra("password");
        firebase_username = intent.getStringExtra("fullName");

        updateAuth = FirebaseAuth.getInstance();
        profile_change = view.findViewById(R.id.profile_changes);

        ev.setText(firebase_email);
        pv.setText(firebase_password);
        uv.setText(firebase_username);

        return view;

    }


}
