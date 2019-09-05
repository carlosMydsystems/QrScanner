package kz.zhakhanyergali.qrscanner.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
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

public class IdPedidoActivity extends AppCompatActivity {

    ImageButton ibRetornoBusquedaCliente ;
    Clientes cliente;
    Usuario usuario;
    ListView lvidPedidos;
    ProgressDialog progressDialog;
    String url;
    ArrayList<String> listaIdentificadoresStr;
    ArrayList<Identificadores> listaIdentificadores;
    Identificadores identificadores;
    String tipoMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_pedido);

        lvidPedidos = findViewById(R.id.lvidPedidos);

        usuario = (Usuario) getIntent().getSerializableExtra("Usuario");  //Se pasa el parametro del usuario
        cliente = (Clientes)getIntent().getSerializableExtra(("Cliente"));
        tipoMenu = getIntent().getStringExtra("tipoMenu");

        listarId(cliente.getCodCliente());
        ibRetornoBusquedaCliente = findViewById(R.id.ibRetornoBusquedaCliente);
        ibRetornoBusquedaCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utilitario.isOnline(getApplicationContext())){
                    Intent intent =  new Intent(IdPedidoActivity.this,BusquedaClienteActivity.class);
                    intent.putExtra("tipoMenu", ""+tipoMenu);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Cliente",cliente);
                    intent.putExtras(bundle);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("Usuario",usuario);
                    intent.putExtras(bundle1);
                    startActivity(intent);
                    finish();
                }else{
                    AlertDialog.Builder build = new AlertDialog.Builder(IdPedidoActivity.this);
                    build.setTitle("Atención .. !");
                    build.setMessage("El Servicio de Internet no esta Activo, por favor revisar");
                    build.setCancelable(false);
                    build.setNegativeButton("ACEPTAR",null);
                    build.create().show();
                }
            }
        });

        lvidPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            identificadores = new Identificadores();
            identificadores =  listaIdentificadores.get(position);

            if(identificadores.getOrigen().equals("EVE")) {
                if(tipoMenu.equals("TomaPedido")){
                    if(identificadores.getImporteTotal().equals("null")){
                        identificadores.setImporteTotal("0.0");
                    }

                    Intent intent = new Intent(IdPedidoActivity.this, TomaPedidoActivity.class);
                    intent.putExtra("indice", identificadores.getDetalle());
                    intent.putExtra("monto", listaIdentificadores.get(position).getImporteTotal());
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

                }else if(tipoMenu.equals("Consulta") || tipoMenu.equals("")) {

                    Intent intent = new Intent(IdPedidoActivity.this, ConsultasActivity.class);
                    intent.putExtra("indice", identificadores.getDetalle());
                    intent.putExtra("monto", listaIdentificadores.get(position).getImporteTotal());
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
            }else{

                Toast.makeText(IdPedidoActivity.this, "Este pedido no es del evento", Toast.LENGTH_SHORT).show();

            }
            }
        });
    }
    /** Hace la busqueda del Cliente por Codigo o por nombre **/

    private void listarId(String numero) {

        progressDialog = new ProgressDialog(IdPedidoActivity.this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        if (numero.length()<6){

            progressDialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(IdPedidoActivity.this);
            builder.setCancelable(false);
            builder.setNegativeButton("Aceptar",null);
            builder.setTitle("Atención...!");
            builder.setMessage("Se debe de ingresar un mínimo de 6 caracteres");
            builder.create().show();

        }else if(numero.contains("%%")) {

            progressDialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(IdPedidoActivity.this);
            builder.setCancelable(false);
            builder.setTitle("Atención...!");
            builder.setMessage("No debe ingresar de forma consecutiva el \"%\"");
            builder.setNegativeButton("Aceptar",null);
            builder.create().show();

        }else{

            // la Url del servicio Web // Se hace la validacion del tipo de consulta

            url = ejecutaFuncionCursorTestMovil +
                    "PKG_WEB_HERRAMIENTAS.FN_WS_LISTAR_PEDIDOS_PEN_EVE&variables='"+ numero.trim().
                    replace("%","%25").toUpperCase() +"'";
            listaIdentificadores = new ArrayList<>();
            listaIdentificadoresStr = new ArrayList<>();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    try {
                        progressDialog.dismiss();
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
                        if (success) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                identificadores = new Identificadores();
                                jsonObject = jsonArray.getJSONObject(i);
                                identificadores.setIdPedido(jsonObject.getString("ID_PEDIDO"));
                                identificadores.setCliente(jsonObject.getString("CLIENTE"));
                                identificadores.setDetalle(jsonObject.getString("DETALLE"));
                                identificadores.setFecha(jsonObject.getString("FECHA"));
                                identificadores.setSucursal(jsonObject.getString("SUCURSAL_CLI"));
                                identificadores.setOrigen(jsonObject.getString("ORIGEN"));
                                identificadores.setImporteTotal(jsonObject.getString("IMPORTE_TOTAL"));
                                identificadores.setCorrelativo(jsonObject.getString("CORRELATIVO"));
                                listaIdentificadores.add(identificadores);
                                listaIdentificadoresStr.add(identificadores.getOrigen()+" - "+identificadores.getIdPedido());
                            }

                            Utilitario.CustomListAdapter listAdapter = new
                                    Utilitario.CustomListAdapter(IdPedidoActivity.this, R.layout.custom_list, listaIdentificadoresStr);
                            lvidPedidos.setAdapter(listAdapter);

                        } else {
                            listaIdentificadoresStr.clear();
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext()
                                    , R.layout.support_simple_spinner_dropdown_item, listaIdentificadoresStr);

                            lvidPedidos.setAdapter(adapter);
                            AlertDialog.Builder builder = new AlertDialog.Builder(IdPedidoActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(IdPedidoActivity.this);
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
