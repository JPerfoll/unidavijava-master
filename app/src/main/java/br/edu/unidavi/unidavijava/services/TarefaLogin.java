package br.edu.unidavi.unidavijava.services;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.edu.unidavi.unidavijava.model.Usuario;
import br.edu.unidavi.unidavijava.R;

public class TarefaLogin extends WebTaskBase {

    private static String SERVICE_URL = "login";
    private String user;
    private String password;

    public TarefaLogin(Context context, String user, String password){
        super(context, SERVICE_URL);
        this.user = user;
        this.password = password;
    }

    @Override
    public String getRequestBody() {
        Map<String,String> requestMap = new HashMap<>();
        requestMap.put("user", user);
        requestMap.put("password", password);

        JSONObject json = new JSONObject(requestMap);
        String jsonString = json.toString();

        return  jsonString;
    }

    @Override
    public void handleResponse(String response) {
        Usuario usuario = new Usuario();
        try {
            JSONObject responseAsJSON = new JSONObject(response);

            String name = responseAsJSON.getString("name");
            usuario.setName(name);
            String profile_url = responseAsJSON.getString("profile_url");
            usuario.setProfileURL(profile_url);
            String token = responseAsJSON.getString("token");
            usuario.setToken(token);
            String username = responseAsJSON.getString("username");
            usuario.setUsername(username);

            EventBus.getDefault().post(usuario);
        } catch (JSONException e) {
            if(!isSilent()){
                EventBus.getDefault().post(new Error(getContext().getString(R.string.label_error_invalid_response)));
            }
        }
    }
}
