package jiaxuan2.yoga.MainFunctions;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import jiaxuan2.yoga.LogInSystem.LogInSystemHelper;
import jiaxuan2.yoga.R;

public class SettingActivity extends AppCompatActivity {

    private EditText newNameEdit;
    private EditText newPasswordEdit;
    private String newName;
    private String newPassword;

    private FirebaseAuth mAuth;
    private LogInSystemHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mHelper = new LogInSystemHelper();
        mAuth = FirebaseAuth.getInstance();

        //Check if the user is updating the username
        newNameEdit = (EditText) findViewById(R.id.newNameEditText);

        findViewById(R.id.nameSaveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newName = newNameEdit.getText().toString();
                if(!newName.isEmpty()){
                    mHelper.updateAccountName(newName, mAuth);
                    Toast.makeText(SettingActivity.this, "Username updated",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(SettingActivity.this, "Please enter new user name",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Check if the user is updating the password
        newPasswordEdit = (EditText) findViewById(R.id.newPasswordEditText);

        findViewById(R.id.passwordSaveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPassword = newPasswordEdit.getText().toString();
                if(!newPassword.isEmpty()){
                    mAuth.getCurrentUser().updatePassword(newPassword).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SettingActivity.this, "Password updated",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else{
                    Toast.makeText(SettingActivity.this, "Please enter new password",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
