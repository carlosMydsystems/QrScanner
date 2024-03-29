package kz.zhakhanyergali.qrscanner.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import kz.zhakhanyergali.qrscanner.Entidades.Clientes;
import kz.zhakhanyergali.qrscanner.Entidades.Identificadores;
import kz.zhakhanyergali.qrscanner.Entidades.Usuario;
import kz.zhakhanyergali.qrscanner.R;
import kz.zhakhanyergali.qrscanner.Utilitarios.Utilitario;
import static kz.zhakhanyergali.qrscanner.Activity.LoginActivity.ejecutaFuncionCursorTestMovil;

public class BusquedaClienteActivity extends AppCompatActivity {

    RadioGroup rggrupocliente;
    RadioButton rbnombre,rbcodigo;
    Button btnbuscar;
    ArrayList<Clientes> listaClientes;
    Clientes cliente;
    ListView lvclientes;
    ArrayList<String> listaCliente;
    EditText etcliente;
    String url, tipoConsulta = "Nombre",tipoMenu,QR,codigo;
    ProgressDialog progressDialog;
    Usuario usuario;
    ImageButton ibregresomenuprincipal;
    Identificadores identificadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_cliente);

        listaClientes = new ArrayList<>();
        listaCliente  =new ArrayList<>();
        rggrupocliente = findViewById(R.id.rgBuscar);
        rbnombre = findViewById(R.id.rbNombre);
        rbcodigo = findViewById(R.id.rbCodigo);
        btnbuscar = findViewById(R.id.btnBuscar);
        lvclientes = findViewById(R.id.lvidPedidos);
        etcliente = findViewById(R.id.etCliente);
        etcliente.setInputType(2);
        tipoConsulta = "Nombre";
        tipoMenu = getIntent().getStringExtra("tipoMenu");
        QR = getIntent().getStringExtra("QR");
        codigo = getIntent().getStringExtra("codigo");

        if (QR == null){
            QR = "Es nulo";
        }else {

        }

        usuario = (Usuario) getIntent().getSerializableExtra("Usuario");  //Se pasa el parametro del usuario
        ibregresomenuprincipal = findViewById(R.id.ibRetornoBusquedaCliente);
        ibregresomenuprincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(Utilitario.isOnline(getApplicationContext())){
                Intent intent =  new Intent(BusquedaClienteActivity.this,MenuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Usuario",usuario);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }else{
                AlertDialog.Builder build = new AlertDialog.Builder(BusquedaClienteActivity.this);
                build.setTitle("Atención .. !");
                build.setMessage("El Servicio de Internet no esta Activo, por favor revisar");
                build.setCancelable(false);
                build.setNegativeButton("ACEPTAR",null);
                build.create().show();
            }
            }
        });

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(Utilitario.isOnline(getApplicationContext())){
                if (etcliente.getText().toString().equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BusquedaClienteActivity.this);
                    builder.setCancelable(false);
                    builder.setMessage("Por favor ingrese un valor valido")
                            .setNegativeButton("Aceptar",null)
                            .create()
                            .show();
                }else {
                    progressDialog = new ProgressDialog(BusquedaClienteActivity.this);
                    progressDialog.setMessage("Cargando...");
                    progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    btnbuscar.setVisibility(View.GONE);
                    buscarCliente(etcliente.getText().toString(),tipoConsulta);
                }
            }else{
                progressDialog.dismiss();
                AlertDialog.Builder build = new AlertDialog.Builder(BusquedaClienteActivity.this);
                build.setTitle("Atención .. !");
                build.setMessage("El Servicio de Internet no esta Activo, por favor revisar");
                build.setCancelable(false);
                build.setNegativeButton("ACEPTAR",null);
                build.create().show();
            }
            }
        });

        etcliente.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (tipoConsulta.equals("Nombre")){

                    cliente = new Clientes();
                    identificadores = new Identificadores();
                    Intent intent = new Intent(BusquedaClienteActivity.this,MainActivity.class);
                    intent.putExtra("indice", "0");
                    intent.putExtra("monto", "0");
                    intent.putExtra("deDondeViene", "cliente");
                    intent.putExtra("tipoMenu", ""+tipoMenu);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Cliente", cliente);
                    intent.putExtras(bundle);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("Usuario", usuario);
                    intent.putExtras(bundle1);
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("Identificadores", identificadores);
                    intent.putExtras(bundle2);
                    startActivity(intent);
                    finish();

                }


                return false;
            }
        });
        lvclientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            cliente = new Clientes();
            cliente =  listaClientes.get(position);
            Intent intent =  new Intent(BusquedaClienteActivity.this,IdPedidoActivity.class);
            intent.putExtra("tipoMenu", ""+tipoMenu);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Cliente",cliente);
            intent.putExtras(bundle);
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("Usuario",usuario);
            intent.putExtras(bundle1);
            startActivity(intent);
            finish();
            }
        });

        rggrupocliente.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            //etcliente.setText("");
                switch (rggrupocliente.getCheckedRadioButtonId()){
                    case R.id.rbNombre:
                        etcliente.setInputType(2);
                        etcliente.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});
                        tipoConsulta = "Nombre";
                        break;
                    case R.id.rbCodigo:
                        etcliente.setInputType(2);
                        etcliente.setFilters(new InputFilter[] {new InputFilter.LengthFilter(11)});
                        tipoConsulta = "Codigo";
                        break;
                    case R.id.rbrazon:
                        etcliente.setInputType(1);
                        etcliente.setFilters(new InputFilter[] {new InputFilter.LengthFilter(80)});
                        tipoConsulta = "Razon";
                        break;
                }
            }
        });

        if (QR.equals("Ok")){

            etcliente.setText(codigo);


            if(Utilitario.isOnline(getApplicationContext())){
                if (etcliente.getText().toString().equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BusquedaClienteActivity.this);
                    builder.setCancelable(false);
                    builder.setMessage("Por favor ingrese un valor valido")
                        .setNegativeButton("Aceptar",null)
                        .create()
                        .show();
                }else {
                    progressDialog = new ProgressDialog(BusquedaClienteActivity.this);
                    progressDialog.setMessage("Cargando...");
                    progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    btnbuscar.setVisibility(View.GONE);
                    buscarCliente(etcliente.getText().toString(),tipoConsulta);
                }
            }else{
                progressDialog.dismiss();
                AlertDialog.Builder build = new AlertDialog.Builder(BusquedaClienteActivity.this);
                build.setTitle("Atención .. !");
                build.setMessage("El Servicio de Internet no esta Activo, por favor revisar");
                build.setCancelable(false);
                build.setNegativeButton("ACEPTAR",null);
                build.create().show();
            }
        }

    }
    /** Hace la busqueda del Cliente por Codigo o por nombre **/

    private void buscarCliente(String numero, String tipoConsulta) {

        numero = numero.trim();
        tipoConsulta = tipoConsulta.trim();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        if (numero.length()<6){
            progressDialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(BusquedaClienteActivity.this);
            builder.setCancelable(false);
            builder.setNegativeButton("Aceptar",null);
            builder.setTitle("Atención...!");
            builder.setMessage("Se debe de ingresar un mínimo de 6 caracteres");
            builder.create().show();
            btnbuscar.setVisibility(View.VISIBLE);
        }else if(numero.contains("%%")) {
            progressDialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(BusquedaClienteActivity.this);
            builder.setCancelable(false);
            builder.setTitle("Atención...!");
            builder.setMessage("No debe ingresar de forma consecutiva el \"%\"");
            builder.setNegativeButton("Aceptar",null);
            builder.create().show();
            btnbuscar.setVisibility(View.VISIBLE);
        }else{

            // la Url del servicio Web // Se hace la validacion del tipo de consulta

            if (tipoConsulta == "Nombre") {

                url = ejecutaFuncionCursorTestMovil +
                        "PKG_WEB_HERRAMIENTAS.FN_WS_CONSULTAR_CLIENTE&variables='" + numero + "||'";

            } else if(tipoConsulta == "Codigo"){

                url = ejecutaFuncionCursorTestMovil +
                        "PKG_WEB_HERRAMIENTAS.FN_WS_CONSULTAR_CLIENTE&variables='||" + numero + "'";

            }else if(tipoConsulta == "Razon"){

                url = ejecutaFuncionCursorTestMovil +
                        "PKG_WEB_HERRAMIENTAS.FN_WS_CONSULTAR_CLIENTE&variables='|"+ numero.trim().replace("%","%25").toUpperCase() +"|'";

            }
            else {

                Toast.makeText(this, "Se produjo un error", Toast.LENGTH_SHORT).show();
            }

            listaCliente = new ArrayList<>();
            listaCliente.clear();
            listaClientes.clear();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            btnbuscar.setVisibility(View.VISIBLE);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
                            if (success) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    cliente = new Clientes();
                                    jsonObject = jsonArray.getJSONObject(i);
                                    cliente.setCodCliente(jsonObject.getString("COD_CLIENTE"));
                                    cliente.setNombre(jsonObject.getString("CLIENTE"));
                                    cliente.setDireccion(jsonObject.getString("DIRECCION"));
                                    cliente.setCodFPago(jsonObject.getString("COD_FPAGO_LIMITE"));
                                    cliente.setFormaPago(jsonObject.getString("FORMA_PAGO"));
                                    cliente.setDocumentoCliente(jsonObject.getString("DOC_CLIENTE"));
                                    listaClientes.add(cliente);
                                    listaCliente.add(cliente.getCodCliente() + " - " + cliente.getNombre());
                                }

                                if (listaClientes.size() == 1){

                                    cliente = new Clientes();
                                    cliente =  listaClientes.get(0);
                                    Intent intent =  new Intent(BusquedaClienteActivity.this,IdPedidoActivity.class);
                                    intent.putExtra("tipoMenu", ""+tipoMenu);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("Cliente",cliente);
                                    intent.putExtras(bundle);
                                    Bundle bundle1 = new Bundle();
                                    bundle1.putSerializable("Usuario",usuario);
                                    intent.putExtras(bundle1);
                                    startActivity(intent);
                                    finish();

                                }else {

                                    Utilitario.CustomListAdapter listAdapter = new
                                            Utilitario.CustomListAdapter(BusquedaClienteActivity.this, R.layout.custom_list, listaCliente);
                                    lvclientes.setAdapter(listAdapter);

                                }

                            } else {
                                listaCliente.clear();
                                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext()
                                        , R.layout.support_simple_spinner_dropdown_item, listaCliente);

                                lvclientes.setAdapter(adapter);
                                AlertDialog.Builder builder = new AlertDialog.Builder(BusquedaClienteActivity.this);
                                builder.setCancelable(false);
                                builder.setMessage("No se llego a encontrar el registro")
                                        .setNegativeButton("Aceptar", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    progressDialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(BusquedaClienteActivity.this);
                    builder.setTitle("Atención ...!");
                    builder.setMessage("EL servicio no se encuentra disponible en estos momentos");
                    builder.setCancelable(false);
                    builder.setNegativeButton("Aceptar",null);
                    builder.create().show();
                }
            });

            int socketTimeout = 30000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            requestQueue.add(stringRequest);
        }
    }
    @Override
    public void onBackPressed(){

    }
}
