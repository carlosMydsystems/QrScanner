package kz.zhakhanyergali.qrscanner.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import kz.zhakhanyergali.qrscanner.Entidades.Usuario;
import kz.zhakhanyergali.qrscanner.R;

public class MenuActivity extends AppCompatActivity {

    Button btnprimero,btnsegundo,btntercero;
    TextView tvusuario;
    Usuario usuario;
    Bundle bundle;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnprimero = findViewById(R.id.btnprimer);
        btnsegundo = findViewById(R.id.btnsegundo);
        btntercero = findViewById(R.id.btntercero);
        tvusuario = findViewById(R.id.tvUsuario);

        //recibe el bundle con los datos del usuario
        usuario = (Usuario) getIntent().getSerializableExtra("Usuario");

        String cadenaAux = usuario.getCodVendedor() + " - " + usuario.getNombre();
        tvusuario.setText(cadenaAux);
        userId = getIntent().getStringExtra("userId");
        bundle = new Bundle();

        // mostrar el codigo del vendedor

        btnprimero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, BusquedaClienteActivity.class);
                intent.putExtra("tipoMenu", "TomaPedido");
                bundle.putSerializable("Usuario", usuario);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        btnsegundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuActivity.this, BusquedaClienteActivity.class);
                intent.putExtra("tipoMenu", "Consulta");
                bundle.putSerializable("Usuario", usuario);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        btntercero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}


