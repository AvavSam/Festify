package com.example.ticketsappredesign2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {
    TextView profileName, profileEmail;
    // Change these to LinearLayout
    LinearLayout profileSettings, myTickets, changePassword, notifications;
    ImageView logoutIcon;
    FirebaseAuth auth;
    FirebaseUser currentUser;

    // Dialog untuk mengganti password
    EditText newPasswordInput;
    TextView updatePasswordButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        // Initialize Views
        profileName = view.findViewById(R.id.profileName);
        profileEmail = view.findViewById(R.id.profileEmail);
        logoutIcon = view.findViewById(R.id.logoutIcon);

        // Update view bindings to LinearLayout
        profileSettings = view.findViewById(R.id.profileSettings);
        myTickets = view.findViewById(R.id.myTickets);
        notifications = view.findViewById(R.id.notifications);
        changePassword = view.findViewById(R.id.changePassword);

        // Ambil data nama dan email dari argumen yang diteruskan dari MainActivity
        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.getString("USER_NAME");
            String email = bundle.getString("USER_EMAIL");

            // Set data ke TextView
            profileName.setText(name != null ? name : "Guest");
            profileEmail.setText(email != null ? email : "guest@example.com");
        } else {
            Log.e("ProfileFragment", "No data passed from MainActivity");
        }

        // Handle Logout
        logoutIcon.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        // Handle Menu Clicks
        profileSettings.setOnClickListener(v -> Log.d("ProfileFragment", "Profile Settings clicked"));
        myTickets.setOnClickListener(v -> Log.d("ProfileFragment", "My Tickets clicked"));
        notifications.setOnClickListener(v -> Log.d("ProfileFragment", "Notifications clicked"));

        // Handle Ganti Password
        changePassword.setOnClickListener(v -> showChangePasswordDialog());

        return view;
    }

    // Menampilkan dialog untuk mengganti password
    private void showChangePasswordDialog() {
        // Membuat dialog untuk meminta password baru
        newPasswordInput = new EditText(getContext());
        newPasswordInput.setHint("Enter new password");

        updatePasswordButton = new TextView(getContext());
        updatePasswordButton.setText("Update Password");

        updatePasswordButton.setOnClickListener(v -> {
            String newPassword = newPasswordInput.getText().toString();

            if (newPassword.isEmpty()) {
                Toast.makeText(getContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            // Perbarui password dengan Firebase Authentication
            if (currentUser != null) {
                currentUser.updatePassword(newPassword)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Failed to update password", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Menampilkan dialog atau cara lain untuk mengambil input password
        // Untuk keperluan ini, bisa menggunakan AlertDialog atau custom layout
        // Misalnya menggunakan AlertDialog untuk input password:
        new android.app.AlertDialog.Builder(getContext())
                .setTitle("Change Password")
                .setView(newPasswordInput)
                .setPositiveButton("Update", (dialog, which) -> {
                    updatePasswordButton.performClick(); // Trigger update password action
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
