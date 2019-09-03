package kz.zhakhanyergali.qrscanner.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import kz.zhakhanyergali.qrscanner.Entidades.Clientes;
import kz.zhakhanyergali.qrscanner.Entidades.DetallePedido;
import kz.zhakhanyergali.qrscanner.Entidades.Identificadores;
import kz.zhakhanyergali.qrscanner.Entidades.Usuario;
import kz.zhakhanyergali.qrscanner.R;
import kz.zhakhanyergali.qrscanner.Utilitarios.Utilitario;
import static kz.zhakhanyergali.qrscanner.Activity.LoginActivity.ejecutaFuncionCursorTestMovil;
import static kz.zhakhanyergali.qrscanner.Activity.LoginActivity.ejecutaFuncionTestMovil;

public class ConsultasActivity extends AppCompatActivity {

    Clientes clientes;
    Usuario usuario;
    Identificadores identificadores;
    String numeroPedido,url,fecha,monedaPedido,tipoMenu;
    ListView lvdetallepedidos,listView;
    DetallePedido detallepedido;
    ArrayList<DetallePedido> listaDetallePedido;
    ArrayList<String> listadetallemostrarPedido;
    TextView tvtitulodinamicoPedidos,tvcancel;
    ImageButton ibretornomenuConsulta;
    View mview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        tipoMenu = getIntent().getStringExtra("tipoMenu");
        clientes = (Clientes)getIntent().getSerializableExtra("Cliente");
        usuario = (Usuario) getIntent().getSerializableExtra("Usuario");
        identificadores = (Identificadores)getIntent().getSerializableExtra("Identificadores");
        numeroPedido = identificadores.getIdPedido();
        lvdetallepedidos = findViewById(R.id.lvDetallePedido);
        tvtitulodinamicoPedidos = findViewById(R.id.tvtitulodinamicoPedidos);
        ibretornomenuConsulta = findViewById(R.id.imgPromocionElegida);
        usuario = (Usuario)getIntent().getSerializableExtra("Usuario");
        fecha = getIntent().getStringExtra("fecha");
        monedaPedido = getIntent().getStringExtra("monedaPedido");
        tvcancel = findViewById(R.id.tvCancel);

        ibretornomenuConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsultasActivity.this,IdPedidoActivity.class);
                intent.putExtra("tipoMenu", "Consulta");
                Bundle bundle = new Bundle();
                bundle.putSerializable("Usuario",usuario);
                intent.putExtras(bundle);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("Cliente",clientes);
                intent.putExtras(bundle1);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("Identificadores", identificadores);
                intent.putExtras(bundle2);
                startActivity(intent);
                finish();
            }
        });

        VerificarCantidad(numeroPedido);

    }

    // Parte que hace la verificación de la cantidad ingresada

    private void VerificarCantidad(String numeroPedido) {

        final ProgressDialog progressDialog = new ProgressDialog(ConsultasActivity.this);
        progressDialog.setMessage("...Cargando");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Permite el formateo de un número en formato de comas con dos decimales

        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.'); // Se define el simbolo para el separador decimal
        simbolos.setGroupingSeparator(',');// Se define el simbolo para el separador de los miles
        final DecimalFormat formateador = new DecimalFormat("###,##0.00",simbolos); // Se crea el formato del numero con los simbolo

        // Se hace la instanciación de los dos Arraylist

        listaDetallePedido = new ArrayList<>();
        listadetallemostrarPedido = new ArrayList<>();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        url = ejecutaFuncionCursorTestMovil +
                "PKG_WEB_HERRAMIENTAS.FN_WS_LISTAR_PEDIDOS_TRAMA&variables='"+numeroPedido+"'";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    Double precioAcumulado =0.0;
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
                        if (success){
                            for(int i=0;i<jsonArray.length();i++) {
                                detallepedido = new DetallePedido();
                                jsonObject = jsonArray.getJSONObject(i);
                                detallepedido.setNroPedido(jsonObject.getString("ID_PEDIDO"));
                                detallepedido.setNroOrden(jsonObject.getString("NRO_ORDEN"));
                                detallepedido.setCodArticulo(jsonObject.getString("COD_ARTICULO"));
                                detallepedido.setTipoRegistro(jsonObject.getString("TIPO_REGISTRO"));
                                detallepedido.setCantidad(jsonObject.getString("CANTIDAD"));
                                detallepedido.setTasaDscto(jsonObject.getString("TASA_DESCUENTO"));
                                detallepedido.setPrecio(jsonObject.getString("PRECIO"));
                                detallepedido.setPrecioOrigen(jsonObject.getString("PRECIO_ORIGEN"));
                                detallepedido.setPrecioFinal(jsonObject.getString("PRECIO_FINAL"));
                                detallepedido.setSubTotal(jsonObject.getString("SUBTOTAL"));
                                detallepedido.setUndMedida(jsonObject.getString("UND_MEDIDA"));
                                detallepedido.setStock(jsonObject.getString("STOCK"));

                                if (detallepedido.getPrecio().equals("null")){
                                    detallepedido.setPrecio("0.0");
                                }

                                detallepedido.setArticulo(jsonObject.getString("ARTICULO"));

                                if(detallepedido.getNroOrden().equals("0")){

                                }else {

                                    Double Aux = (1 - Double.valueOf(detallepedido.getTasaDscto())/100) * Double.valueOf(detallepedido.getPrecio().replace(",", ""));
                                    Double cant = Double.valueOf(detallepedido.getCantidad().replace(",", ""));
                                    Double subtotal = Double.valueOf(cant*Aux);
                                    precioAcumulado = precioAcumulado + subtotal;
                                    String ss = formateador.format(Double.parseDouble(""+subtotal));

                                    listadetallemostrarPedido.add(detallepedido.getCodArticulo() + " - "
                                            + detallepedido.getArticulo() + " - "+ detallepedido.getArticulo() + "\n" + "Cant : " + detallepedido.getCantidad()+"\t\t\t\t\t\t\t\t\t\t\t\t\t"+
                                            "\n" + "Precio : "+"S/  "+ Aux + "\t\t\t\t\t\t Subtotal : S/ "+ ss );
                                    listaDetallePedido.add(detallepedido);
                                }
                            }
                            progressDialog.dismiss();

                            String cadenaTituloAux = "Productos : " + listaDetallePedido.size() + "   |  Monto : S/ " + formateador.format(precioAcumulado)+ "";
                            tvtitulodinamicoPedidos.setText(cadenaTituloAux);

                            Utilitario.CustomListAdapter listAdapter = new
                                    Utilitario.CustomListAdapter(ConsultasActivity.this, R.layout.custom_list, listadetallemostrarPedido);
                            mview = getLayoutInflater().inflate(R.layout.listview_dialog, null);
                            lvdetallepedidos.setAdapter(listAdapter);

                            lvdetallepedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                                    final AlertDialog.Builder builder = new AlertDialog.Builder(ConsultasActivity.this);
                                    builder.setCancelable(false);
                                    listView = mview.findViewById(R.id.lvopciones);
                                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                                            ConsultasActivity.this, android.R.layout.simple_list_item_1,
                                            getResources().getStringArray(R.array.opciones));
                                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

                                    listView.setAdapter(adapter);

                                   //lvbandejaproductos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                                        usuario = (Usuario) getIntent().getSerializableExtra("Usuario");

                                            switch (i) {
                                                case 0: // Editar producto
                                                        Editarproductoselecionado(position);

                                                        break;
                                                case 1: // Eliminar el Producto
                                                        final String trama1 = listaDetallePedido.get(position).getNroPedido() + "|" + listaDetallePedido.get(position).getNroOrden();
                                                        final AlertDialog.Builder builder1 = new AlertDialog.Builder(
                                                                ConsultasActivity.this);
                                                        builder1.setCancelable(false);
                                                        builder1.setMessage("Esta seguro que desea eliminar el pedido?")
                                                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    EliminarProducto(trama1);
                                                                    dialogInterface.cancel();
                                                                    salirlistview();
                                                                }
                                                            })
                                                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.cancel();
                                                                    salirlistview();
                                                                }
                                                            })
                                                            .create()
                                                            .show();
                                                        break;

                                                case 2: // Cancela la accion pero elimina la promocion

                                                    salirlistview();
                                                    break;
                                            }
                                        }
                                    });
                                    builder.setView(mview);
                                    AlertDialog dialog = builder.create();
                                    if (mview.getParent() != null)
                                        ((ViewGroup) mview.getParent()).removeView(mview); // <- fix
                                    dialog.show();
                                }
                            });

                        }else {
                            progressDialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(ConsultasActivity.this);
                            builder.setCancelable(false)
                                    .setMessage("No se llego a encontrar el registro")
                                    .setNegativeButton("Aceptar",null)
                                    .create()
                                    .show();
                        }
                    } catch (JSONException e) { e.printStackTrace(); }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.show();
                AlertDialog.Builder builder = new AlertDialog.Builder(ConsultasActivity.this);
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

    private void salirlistview() {

        Intent intent = new Intent(ConsultasActivity.this,ConsultasActivity.class);
        clientes = (Clientes)getIntent().getSerializableExtra("Cliente");
        usuario = (Usuario) getIntent().getSerializableExtra("Usuario");
        identificadores = (Identificadores)getIntent().getSerializableExtra("Identificadores");

        intent.putExtra("tipoMenu",tipoMenu);

        Bundle bundle = new Bundle();
        bundle.putSerializable("Cliente", clientes);
        intent.putExtras(bundle);

        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("Usuario", usuario);
        intent.putExtras(bundle1);

        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("Usuario", usuario);
        intent.putExtras(bundle2);

        Bundle bundle3 = new Bundle();
        bundle3.putSerializable("Identificadores", identificadores);
        intent.putExtras(bundle3);

        startActivity(intent);
        finish();
    }

    private void EliminarProducto(String trama) {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  ejecutaFuncionTestMovil +
                "PKG_WEB_HERRAMIENTAS.FN_WS_ELIMINA_TRAMA_MOVIL&variables='"+trama+"'";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("OK")){
                            // insertaCampos(listaproductoselegidos,id);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                AlertDialog.Builder builder = new AlertDialog.Builder(ConsultasActivity.this);
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

    private void Editarproductoselecionado(int position) {

        Intent intent = new Intent(ConsultasActivity.this,TomaPedidoActivity.class);
        identificadores.setIdPedido(listaDetallePedido.get(position).getNroPedido());
        intent.putExtra("codigo",listaDetallePedido.get(position).getCodArticulo());
        intent.putExtra("QR","Ok");
        intent.putExtra("tipoMenu", "Actualizacion");
        Bundle bundle = new Bundle();
        bundle.putSerializable("Cliente", clientes);
        intent.putExtras(bundle);
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("Usuario", usuario);
        intent.putExtras(bundle1);
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("Identificadores", identificadores);
        intent.putExtras(bundle2);
        Bundle bundle3 = new Bundle();
        bundle3.putSerializable("DetallePedido", listaDetallePedido.get(position));
        intent.putExtras(bundle3);
        startActivity(intent);
        finish();
    }
}

