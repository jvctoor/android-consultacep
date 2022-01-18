package com.example.consultacepvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextInputEditText edtCEP, edtRua, edtBairro, edtCidade, edtEstado;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);

        edtCEP = findViewById(R.id.edt_cep);
        edtRua = findViewById(R.id.edt_rua);
        edtBairro = findViewById(R.id.edt_bairro);
        edtCidade = findViewById(R.id.edt_cidade);
        edtEstado = findViewById(R.id.edt_uf);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        edtCEP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String num = edtCEP.getText().toString();
                consultarCEP(num);
            }
        });
    }

    private void consultarCEP(String cep) {
        String base = "https://viacep.com.br/ws/";
        String end = "/json";
        String url = base + cep + end;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String cep = response.getString("cep");
                            String rua = response.getString("logradouro");
                            String bairro = response.getString("bairro");
                            String cidade = response.getString("localidade");
                            String estado = response.getString("uf");
                            String ddd = response.getString("ddd");
                            edtRua.setText(rua);
                            edtCidade.setText(cidade);
                            edtBairro.setText(bairro);
                            edtEstado.setText(estado);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);
    }
}