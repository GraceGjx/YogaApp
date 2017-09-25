package jiaxuan2.yoga.LogInSystem;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by jiaxuan2 on 4/11/17.
 */

public class LogInSystemHelper {

    /**
     * Check whether every eidtText field is filled with user information for Log In
     *
     * @param emailField EditText for email
     * @param pField     EditText for password
     * @return true if all filled, false otherwise
     */
    public boolean validateLogIn(EditText emailField, EditText pField) {
        boolean isValid = true;
        String email = emailField.getText().toString();
        if (email.isEmpty()) {
            emailField.setError("User Name Required");
            isValid = false;
        }

        String password = pField.getText().toString();
        if (password.isEmpty()) {
            pField.setError("User Name Required");
            isValid = false;
        }
        return isValid;
    }


    /**
     * Check whether every eidtText field is filled with user information for Sign up
     *
     * @param emailField EditText for email
     * @param pField     EditText for password
     * @param nameField  EditText for name
     * @return true if all filled, false otherwise
     */
    public boolean validateSignUp(EditText emailField, EditText pField, EditText nameField) {
        boolean isValid = true;
        String email = emailField.getText().toString();
        if (email.isEmpty()) {
            emailField.setError("User Name Required");
            isValid = false;
        }

        String password = pField.getText().toString();
        if (password.isEmpty()) {
            pField.setError("User Name Required");
            isValid = false;
        }

        String name = nameField.getText().toString();
        if (name.isEmpty()) {
            nameField.setError("User Name Required");
            isValid = false;
        }
        return isValid;

    }


    /**
     * Update user's display name
     *
     * @param name  String representation of name
     * @param mAuth FirbaseAuth passed from upper class
     */
    public void updateAccountName(final String name, FirebaseAuth mAuth) {
        Log.d(SignUpActivity.TAG, "updateUserName: " + name);

        UserProfileChangeRequest nameUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(name).build();
        mAuth.getCurrentUser().updateProfile(nameUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(SignUpActivity.TAG, "User name updated");
                        }
                    }
                });
    }

}
