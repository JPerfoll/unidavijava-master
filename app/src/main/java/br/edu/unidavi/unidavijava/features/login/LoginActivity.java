package br.edu.unidavi.unidavijava.features.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import br.edu.unidavi.unidavijava.data.Session;
import br.edu.unidavi.unidavijava.features.home.MainActivity;
import br.edu.unidavi.unidavijava.R;

import br.edu.unidavi.unidavijava.model.Usuario;
import br.edu.unidavi.unidavijava.services.TarefaLogin;

public class LoginActivity extends AppCompatActivity {

    private TarefaLogin tarefaLogin = null;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button buttonEnter = findViewById(R.id.button_enter);
        buttonEnter.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tryLogin();
                    }
                }
        );

        Session session = new Session();
        EditText editTextEmail = findViewById(R.id.input_email);
        editTextEmail.setText(session.getEmailInSession(getApplicationContext()));

        EditText editTextPassword = findViewById(R.id.input_password);
        editTextPassword.requestFocus();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void tryLogin(){
        EditText inputEmail = findViewById(R.id.input_email);
        String emailValue = inputEmail.getText().toString();

        EditText inputPassword = findViewById(R.id.input_password);
        String passwordValue = inputPassword.getText().toString();

        showDialog();

        tarefaLogin = new TarefaLogin(getApplicationContext(), emailValue, passwordValue);
        tarefaLogin.execute();

        /*if("teste".equals(emailValue) && "teste".equals(passwordValue)){

        }else{
            Log.d("DEBUG", "NÃO DEU CERTO");
        }*/
    }

    /*private void saveEmailInSession(String emailValue) {
        SharedPreferences sharedPreferences =
                getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =
                sharedPreferences.edit();
        editor.putString(FIELD_USERNAME, emailValue);
        editor.commit();
    }*/

    /*private String getEmailInSession(){
        SharedPreferences sharedPreferences =
                getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getString(FIELD_USERNAME,"");
    }*/

    private void goToHome(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    @Subscribe
    public void onEvent(Usuario usuario){
        hideDialog();

        Session session = new Session();
        session.saveEmailInSession(getApplicationContext(), usuario.getUsername());
        session.saveProfileURLInSession(getApplicationContext(), usuario.getProfileURL());

        goToHome();
    }

    @Subscribe
    public void onEvent(Error error) {
        hideDialog();

        Snackbar snackbar = Snackbar.make(findViewById(R.id.frmLogin), error.getMessage(), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void showDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.message_wait));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }
}
