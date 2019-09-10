package kz.zhakhanyergali.qrscanner.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import kz.zhakhanyergali.qrscanner.Entidades.Clientes;
import kz.zhakhanyergali.qrscanner.Entidades.DetallePedido;
import kz.zhakhanyergali.qrscanner.Entidades.Identificadores;
import kz.zhakhanyergali.qrscanner.Entidades.Pedido;
import kz.zhakhanyergali.qrscanner.Entidades.Producto;
import kz.zhakhanyergali.qrscanner.Entidades.Usuario;
import kz.zhakhanyergali.qrscanner.R;
import kz.zhakhanyergali.qrscanner.Utilitarios.Utilitario;
import static kz.zhakhanyergali.qrscanner.Activity.LoginActivity.ejecutaFuncionCursorTestMovil;
import static kz.zhakhanyergali.qrscanner.Activity.LoginActivity.ejecutaFuncionTestMovil;
import static kz.zhakhanyergali.qrscanner.Utilitarios.Utilitario.Soles;

public class TomaPedidoActivity extends AppCompatActivity {

    TextView tvCliente,tvStock,tvPrecio,tvDescripcionProducto,tvPrecioReal,tvTasaDscto,
            tvDodPresentacion,tvEquivalencia,tvUnidad,tvSubtotal,tvtitulodinamico,textView4,textView9,
            textView8,textView13,textView5,tvIndice,tvmonto,tvMuestraLineaDisponible;
    EditText etCodProducto,etCantidad;
    Button btnVerificar,btnAgregar,btnbuscarProducto;
    Clientes clientes;
    Usuario usuario;
    String nombreCliente,url,cadenaTituloAux,cantidad,tipoMenu,codigo,monto,QR;
    ProgressDialog progressDialog;
    ArrayList<Producto> listaProducto;
    Producto producto;
    DecimalFormat formateador,formateador1;
    ImageButton ibRegresaCliente;
    Identificadores identificadores,identificadores1;
    Double precio;
    Pedido pedido;
    Integer valida = 0;
    ArrayList<Pedido> listaPedido;
    DetallePedido detallePedido;
    ArrayList<Identificadores> listaIdentificadores;     // Actualizacion
    static String inamovible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toma_pedido);

        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.'); // Se define el simbolo para el separador decimal
        simbolos.setGroupingSeparator(',');// Se define el simbolo para el separador de los miles
        formateador = new DecimalFormat("###,##0.00",simbolos); // Se crea el formato del numero con los simbolo
        formateador1 = new DecimalFormat("###,##0.0000",simbolos); // Se crea el formato del numero con los simbolo
        monto = getIntent().getStringExtra("monto");
        QR = getIntent().getStringExtra("QR");

        if(QR == null){
            QR = "Es nulo";
        }

        identificadores = (Identificadores)getIntent().getSerializableExtra("Identificadores");
        inamovible = identificadores.getIdPedido();
        clientes = (Clientes)getIntent().getSerializableExtra("Cliente");
        usuario = (Usuario) getIntent().getSerializableExtra("Usuario");
        tipoMenu = getIntent().getStringExtra("tipoMenu");
        detallePedido = (DetallePedido) getIntent().getSerializableExtra("DetallePedido");
        nombreCliente = clientes.getCodCliente() + " - "+ clientes.getNombre();
        codigo = getIntent().getStringExtra("codigo");
        tvCliente = findViewById(R.id.tvCliente);
        tvStock = findViewById(R.id.tvStock);
        tvPrecio = findViewById(R.id.tvPrecio);
        etCodProducto = findViewById(R.id.etCodProducto);
        etCantidad = findViewById(R.id.etCantidad);
        btnVerificar = findViewById(R.id.btnVerificar);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnbuscarProducto = findViewById(R.id.btnBuscar);
        tvDescripcionProducto = findViewById(R.id.tvDescripcionProducto);
        ibRegresaCliente = findViewById(R.id.ibRegresaCliente);
        tvTasaDscto = findViewById(R.id.tvTasaDscto);
        tvPrecioReal = findViewById(R.id.tvPrecioReal);
        tvDodPresentacion = findViewById(R.id.tvDodPresentacion);
        tvtitulodinamico = findViewById(R.id.tvtitulodinamicoPedidos);
        tvEquivalencia = findViewById(R.id.tvEquivalencia);
        tvUnidad = findViewById(R.id.tvUnidad);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        textView4 = findViewById(R.id.textView4);
        textView9 = findViewById(R.id.textView9);
        textView8 = findViewById(R.id.textView8);
        textView13 = findViewById(R.id.textView13);
        textView5 = findViewById(R.id.textView5);
        tvIndice = findViewById(R.id.tvIndice);
        tvmonto = findViewById(R.id.tvmonto);
        tvMuestraLineaDisponible = findViewById(R.id.tvMuestraLineaDisponible);

       tvIndice.setText(identificadores.getCorrelativo());
        etCodProducto.setText("");

        if (codigo == null){
            etCodProducto.setText("");
        }else {
            etCodProducto.setText(codigo);
        }

        if (valida==0) {
            cadenaTituloAux = "Productos : " + identificadores.getDetalle() + "   |  Monto : " +
                    Soles + "\t" + formateador.format(Double.parseDouble(identificadores.getImporteTotal())) + "";

            tvtitulodinamico.setText(cadenaTituloAux);

            tvMuestraLineaDisponible.setText("Linea disponible  :  S/ " + formateador.format(Double.parseDouble(identificadores.getLineaDisponible())));


        }

        if(tipoMenu.equals("Actualizacion")){

            MostrarPrimerPanel(true);
            tvStock.setText(detallePedido.getStock());
            tvUnidad.setText(detallePedido.getUndMedida());
            tvPrecio.setText(formateador1.format(Double.parseDouble(detallePedido.getPrecioFinal())));
            etCodProducto.setEnabled(false);
            tvDescripcionProducto.setText(detallePedido.getArticulo());
            btnbuscarProducto.setEnabled(false);
            tvSubtotal.setText(formateador.format(Double.parseDouble(detallePedido.getSubTotal())));
            etCantidad.setText(detallePedido.getCantidad());
            etCantidad.requestFocus();

        }else{

            if(QR.equals("Ok")){
                buscarDuplicado(identificadores.getIdPedido(),etCodProducto.getText().toString());
            }
        }

        etCodProducto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

            Intent intent = new Intent(TomaPedidoActivity.this,MainActivity.class);
            intent.putExtra("monto", monto);
            intent.putExtra("deDondeViene", "TomaPedido");
            intent.putExtra("tipoMenu", ""+tipoMenu);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Cliente", clientes);
            intent.putExtras(bundle);
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("Usuario", usuario);
            intent.putExtras(bundle1);
            Bundle bundle2 = new Bundle();
            bundle2.putSerializable("Identificadores", identificadores);
            intent.putExtras(bundle2);
            startActivity(intent);
            finish();
            return false;

            }
        });
        precio = 0.0;
        cantidad = "0";

        final TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MostrarSegundoPanel(false);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        etCantidad.addTextChangedListener(textWatcher);
        if(nombreCliente.length() > 28 ) {
            nombreCliente = nombreCliente.substring(0,30);
        }
        tvCliente.setText(nombreCliente);
        ibRegresaCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tipoMenu.equals("Actualizacion")){

                    Intent intent = new Intent(TomaPedidoActivity.this, ConsultasActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("Cliente", clientes);
                    intent.putExtras(bundle1);
                    intent.putExtra("tipoMenu", ""+tipoMenu);
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("Identificadores", identificadores);
                    intent.putExtras(bundle2);
                    Bundle bundle3 = new Bundle();
                    bundle3.putSerializable("Usuario", usuario);
                    intent.putExtras(bundle3);
                    startActivity(intent);
                    finish();

                }else {

                    Intent intent = new Intent(TomaPedidoActivity.this, IdPedidoActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("Cliente", clientes);
                    intent.putExtras(bundle1);
                    intent.putExtra("tipoMenu", ""+tipoMenu);
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("Identificadores", identificadores);
                    intent.putExtras(bundle2);
                    Bundle bundle3 = new Bundle();
                    bundle3.putSerializable("Usuario", usuario);
                    intent.putExtras(bundle3);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utilitario.isOnline(getApplicationContext())){

                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(etCodProducto.getWindowToken(), 0);

                    Double cant = Double.parseDouble(etCantidad.getText().toString());
                    if (cant <= 0.00) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(TomaPedidoActivity.this);
                        builder.setTitle("Atención !");
                        builder.setMessage("Por favor ingrese una cantidad valida");
                        builder.setCancelable(false);
                        builder.setNegativeButton("Aceptar",null);
                        builder.create()
                                .show();

                    }else {

                        buscarproducto(etCantidad.getText().toString(),etCodProducto.getText().toString());
                    }
                }else{
                    AlertDialog.Builder build = new AlertDialog.Builder(TomaPedidoActivity.this);
                    build.setTitle("Atención .. !");
                    build.setMessage("El Servicio de Internet no esta Activo, por favor revisar");
                    build.setCancelable(false);
                    build.setNegativeButton("ACEPTAR",null);
                    build.create().show();
                }
            }
        });

        final TextWatcher textWatcher1 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MostrarPrimerPanel(false);
                etCantidad.setText("");
                tvPrecio.setText("");
                tvStock.setText("");
                tvUnidad.setText("");
                tvSubtotal.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        etCodProducto.addTextChangedListener(textWatcher1);
        btnbuscarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utilitario.isOnline(getApplicationContext())){

                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(etCodProducto.getWindowToken(), 0);

                    if (etCodProducto.getText().toString().equals("")) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(TomaPedidoActivity.this);
                        builder.setTitle("Atención !");
                        builder.setMessage("Por favor ingrese una cantidad valida");
                        builder.setCancelable(false);
                        builder.setNegativeButton("Aceptar",null);
                        builder.create()
                            .show();

                    }else {

                        buscarDuplicado(identificadores.getIdPedido(),etCodProducto.getText().toString());
                    }

                }else{

                AlertDialog.Builder build = new AlertDialog.Builder(TomaPedidoActivity.this);
                build.setTitle("Atención .. !");
                build.setMessage("El Servicio de Internet no esta Activo, por favor revisar");
                build.setCancelable(false);
                build.setNegativeButton("ACEPTAR",null);
                build.create().show();

                }
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            valida++;
            agregarPedido(producto.getCodigo());

            }
        });
    }

    private void buscarDuplicado(String IdPedido,String CodArticulo) {

        progressDialog = new ProgressDialog(TomaPedidoActivity.this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);
        progressDialog.create();
        progressDialog.show();

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        url = ejecutaFuncionCursorTestMovil +
                "PKG_WEB_HERRAMIENTAS.FN_WS_LISTAR_ARTICULOS_TRAMA&variables=%27"+IdPedido+"|"+CodArticulo+"%27";
        listaPedido = new ArrayList<>();

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String Mensaje = "";
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
                            Boolean condicion = false,error = false;
                            if (success) {
                                String Aux = response.replace("{", "|");
                                Aux = Aux.replace("}", "|");
                                Aux = Aux.replace("[", "|");
                                Aux = Aux.replace("]", "|");
                                Aux = Aux.replace("\"", "|");
                                Aux = Aux.replace(",", " ");
                                Aux = Aux.replace("|", "");
                                Aux = Aux.replace(":", " ");
                                String partes[] = Aux.split(" ");
                                for (String palabras : partes) {
                                    if (condicion) {
                                        Mensaje += palabras + " ";
                                    }
                                    if (palabras.equals("ERROR")) {
                                        condicion = true;
                                        error = true;
                                    }
                                }
                                if (error) {

                                    progressDialog.dismiss();

                                    AlertDialog.Builder dialog = new AlertDialog.Builder(
                                            TomaPedidoActivity.this);
                                    dialog.setCancelable(false);
                                    dialog.setMessage(Mensaje)
                                            .setNegativeButton("Regresar", null)
                                            .create()
                                            .show();
                                } else {

                                    listaPedido.clear();
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        jsonObject = jsonArray.getJSONObject(i);
                                        pedido = new Pedido();
                                        pedido.setIdPedido(jsonObject.getString("ID_PEDIDO"));
                                        pedido.setCodArticulo(jsonObject.getString("COD_ARTICULO"));
                                        pedido.setIndice(jsonObject.getString("NRO_ORDEN")); //
                                        pedido.setCantidad(jsonObject.getString("CANTIDAD"));
                                        pedido.setDescripcion(jsonObject.getString("DESCRIPCION"));
                                        pedido.setUndMedida(jsonObject.getString("UND_MEDIDA"));
                                        pedido.setPrecio(jsonObject.getString("PRECIO"));
                                        pedido.setTasaDscto(jsonObject.getString("TASA_DESCUENTO")); //
                                        pedido.setPrecioFinal(jsonObject.getString("PRECIO_FINAL"));
                                        pedido.setSubTotal(jsonObject.getString("SUBTOTAL"));
                                        pedido.setStock(jsonObject.getString("STOCK"));
                                        listaPedido.add(pedido);
                                    }

                                    tvIndice.setText(listaPedido.get(0).getIndice());
                                    progressDialog.dismiss();
                                    tvDescripcionProducto.setVisibility(View.VISIBLE);
                                    MostrarPrimerPanel(true);
                                    String descripcionProducto = listaPedido.get(0).getDescripcion();
                                    if(descripcionProducto.length() > 45 ) {
                                        descripcionProducto = descripcionProducto.substring(0,45);
                                    }

                                    tvDescripcionProducto.setText(descripcionProducto);
                                    tvPrecioReal.setText(listaPedido.get(0).getPrecio());
                                    tvTasaDscto.setText(listaPedido.get(0).getTasaDscto());
                                    tvUnidad.setText(listaPedido.get(0).getUndMedida());
                                    tvStock.setText(formateador.format(Double.valueOf(listaPedido.get(0).getStock())));
                                    tvPrecio.setText(formateador1.format(Double.parseDouble(listaPedido.get(0).getPrecioFinal())));

                                    if (listaPedido.size() == 0){
                                        if (etCantidad.getText().toString().equals("")){
                                            etCantidad.setText("1");
                                        }
                                    }else {
                                        etCantidad.setText(listaPedido.get(0).getCantidad());
                                    }

                                    etCantidad.requestFocus();
                                    tvSubtotal.setText(formateador.format(Double.parseDouble(listaPedido.get(0).getSubTotal())));
                                    tvmonto.setText("Duplicado");
                                }
                            }else {
                                tvmonto.setText("NoDuplicado");

                                Integer indice = 1 +Integer.parseInt(tvIndice.getText().toString());
                                tvIndice.setText("" + indice);
                                listaPedido.clear();
                                progressDialog.dismiss();
                                buscarproducto("1",etCodProducto.getText().toString());

                            }
                        } catch (JSONException e) { e.printStackTrace(); }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(TomaPedidoActivity.this);
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

    private void agregarPedido(String codProducto) {

        progressDialog = new ProgressDialog(TomaPedidoActivity.this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);
        progressDialog.create();
        progressDialog.show();
        progressDialog.dismiss();
        MostrarPrimerPanel(false);
        MostrarSegundoPanel(false);


        if(tipoMenu.equals("Actualizacion")){

            tvIndice.setText(detallePedido.getNroOrden());
            etCantidad.requestFocus();

        }

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

                url = ejecutaFuncionTestMovil +
                "PKG_WEB_HERRAMIENTAS.FN_WS_REGISTRA_TRAMA_MOVIL&variables='"+identificadores.getIdPedido()
                +"|D|"+tvIndice.getText().toString()+"|"+etCantidad.getText().toString()+"|"+codProducto
                +"|"+tvPrecioReal.getText().toString()+"|"+tvTasaDscto.getText().toString()+"||"
                +tvDodPresentacion.getText().toString()+"|"+tvEquivalencia.getText().toString()+"|N|"+usuario.getUser()+"'";


        monto = tvmonto.getText().toString();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response.toUpperCase().equals("OK")) {

                                progressDialog.dismiss();

                                etCantidad.setText("");
                                tvPrecio.setText("");
                                tvStock.setText("");
                                tvUnidad.setText("");
                                tvSubtotal.setText("");
                                etCodProducto.setText("");
                                actualizaCabecera(clientes.getCodCliente(),inamovible);

                                if(tipoMenu.equals("Actualizacion")){

                                    Intent intent = new Intent(TomaPedidoActivity.this, ConsultasActivity.class);
                                    Bundle bundle1 = new Bundle();
                                    bundle1.putSerializable("Cliente", clientes);
                                    intent.putExtras(bundle1);
                                    intent.putExtra("tipoMenu", ""+tipoMenu);
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putSerializable("Identificadores", identificadores);
                                    intent.putExtras(bundle2);
                                    Bundle bundle3 = new Bundle();
                                    bundle3.putSerializable("Usuario", usuario);
                                    intent.putExtras(bundle3);
                                    startActivity(intent);
                                    finish();

                                }

                            }else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(TomaPedidoActivity.this);
                                builder.setMessage("No se llego a encontrar el registro")
                                        .setNegativeButton("Aceptar",null)
                                        .create()
                                        .show();
                                progressDialog.dismiss();
                            }
                        } catch (Exception e) { e.printStackTrace(); }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(TomaPedidoActivity.this);
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

    private void actualizaCabecera(String numero, final String idPedido) {

        listaIdentificadores = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            url = ejecutaFuncionCursorTestMovil +
                    "PKG_WEB_HERRAMIENTAS.FN_WS_LISTAR_PEDIDOS_PEN_EVE&variables='"+ numero.trim().
                    replace("%","%25").toUpperCase() +"'";

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

                                        identificadores1 = new Identificadores();
                                        jsonObject = jsonArray.getJSONObject(i);
                                        identificadores1.setIdPedido(jsonObject.getString("ID_PEDIDO"));
                                        if (idPedido.equals(jsonObject.getString("ID_PEDIDO"))){
                                            identificadores1.setCliente(jsonObject.getString("CLIENTE"));
                                            identificadores1.setDetalle(jsonObject.getString("DETALLE"));
                                            identificadores1.setFecha(jsonObject.getString("FECHA"));
                                            identificadores1.setSucursal(jsonObject.getString("SUCURSAL_CLI"));
                                            identificadores1.setOrigen(jsonObject.getString("ORIGEN"));
                                            identificadores1.setImporteTotal(jsonObject.getString("IMPORTE_TOTAL"));
                                            identificadores1.setCorrelativo(jsonObject.getString("CORRELATIVO"));
                                            listaIdentificadores.add(identificadores1);
                                        }
                                    }

                                    identificadores1.setIdPedido(listaIdentificadores.get(0).getIdPedido());
                                    cadenaTituloAux = "Productos : " + listaIdentificadores.get(0).getDetalle() + "   |  Monto : " +
                                            Soles + "\t"+ formateador.format(Double.parseDouble(listaIdentificadores.get(0).getImporteTotal())) + "";
                                    tvtitulodinamico.setText(cadenaTituloAux);
                                    tvIndice.setText(listaIdentificadores.get(0).getCorrelativo());

                                } else {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(TomaPedidoActivity.this);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(TomaPedidoActivity.this);
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

    private void buscarproducto(String numero,String CodProducto) {

        progressDialog = new ProgressDialog(TomaPedidoActivity.this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);
        progressDialog.create();
        progressDialog.show();
        etCantidad.requestFocus();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        numero = numero.replace("%","%25");
        numero = numero.toUpperCase(); // se convierten los caracteres a Mayuscula

        url = ejecutaFuncionCursorTestMovil +
                "PKG_WEB_HERRAMIENTAS.FN_WS_CONSULTAR_PRODUCTO&variables='CD1|LIMA|"+CodProducto+"||"+clientes.getCodCliente()+"|||"+numero+"'";
        listaProducto = new ArrayList<>();

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    String Mensaje = "";
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
                    Boolean condicion = false,error = false;
                    if (success) {
                        String Aux = response.replace("{", "|");
                        Aux = Aux.replace("}", "|");
                        Aux = Aux.replace("[", "|");
                        Aux = Aux.replace("]", "|");
                        Aux = Aux.replace("\"", "|");
                        Aux = Aux.replace(",", " ");
                        Aux = Aux.replace("|", "");
                        Aux = Aux.replace(":", " ");
                        String partes[] = Aux.split(" ");
                        for (String palabras : partes) {
                            if (condicion) {
                                Mensaje += palabras + " ";
                            }
                            if (palabras.equals("ERROR")) {
                                condicion = true;
                                error = true;
                            }
                        }
                        if (error) {

                            progressDialog.dismiss();

                            AlertDialog.Builder dialog = new AlertDialog.Builder(
                                    TomaPedidoActivity.this);
                            dialog.setCancelable(false);
                            dialog.setMessage(Mensaje)
                                    .setNegativeButton("Regresar", null)
                                    .create()
                                    .show();
                            MostrarPrimerPanel(false);
                        } else {

                            listaProducto.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                producto = new Producto();
                                producto.setCodigo(jsonObject.getString("COD_ARTICULO"));
                                producto.setMarca(jsonObject.getString("DES_MARCA"));
                                producto.setDescripcion(jsonObject.getString("DES_ARTICULO")); //
                                producto.setStock(jsonObject.getString("STOCK_DISPONIBLE"));
                                producto.setUnidad(jsonObject.getString("UND_MEDIDA"));
                                producto.setPrecio(jsonObject.getString("PRECIO_SOLES"));
                                producto.setTasaDescuento(jsonObject.getString("TASA_DESCUENTO"));
                                producto.setPresentacion(jsonObject.getString("COD_PRESENTACION"));
                                producto.setEquivalencia(jsonObject.getString("EQUIVALENCIA"));

                                listaProducto.add(producto);
                            }

                            progressDialog.dismiss();
                            tvDescripcionProducto.setVisibility(View.VISIBLE);

                            MostrarPrimerPanel(true);

                            String descripcionProducto = listaProducto.get(0).getDescripcion();

                            if(descripcionProducto.length() > 45 ) {

                                descripcionProducto = descripcionProducto.substring(0,45);

                            }

                            etCantidad.requestFocus();
                            tvDescripcionProducto.setText(descripcionProducto);
                            tvPrecioReal.setText(listaProducto.get(0).getPrecio());
                            tvTasaDscto.setText(listaProducto.get(0).getTasaDescuento());
                            tvDodPresentacion.setText(listaProducto.get(0).getPresentacion());
                            tvEquivalencia.setText(listaProducto.get(0).getEquivalencia());
                            tvUnidad.setText(listaProducto.get(0).getUnidad());

                            BigDecimal precioBig1 = new BigDecimal(listaProducto.get(0).getPrecio());
                            precioBig1 = precioBig1.setScale(2, RoundingMode.HALF_EVEN);
                            Double Descuento = (1 - Double.valueOf(listaProducto.get(0).getTasaDescuento()) / 100);

                            if(tipoMenu.equals("Actualizacion")){

                            }else {

                                if (listaPedido.size() == 0){
                                    if (etCantidad.getText().toString().equals("")){
                                        etCantidad.setText("1");
                                    }
                                }else {
                                    etCantidad.setText(etCantidad.getText().toString());
                                    listaPedido.clear();
                                }

                            }
                            Double precioDouble = Double.valueOf(precioBig1.toString()) * Descuento ;

                            BigDecimal precioBigTotal = new BigDecimal(precioDouble.toString());
                            //precioBigTotal = precioBigTotal.setScale(2, RoundingMode.HALF_EVEN);

                            tvStock.setText(formateador.format(Double.valueOf(listaProducto.get(0).getStock())));
                            tvPrecio.setText(formateador1.format(Double.parseDouble(precioDouble.toString())));

                            BigDecimal cantidad = new BigDecimal(etCantidad.getText().toString());
                            BigDecimal subTotal = precioBigTotal.multiply(cantidad);
                            tvSubtotal.setText(formateador.format(subTotal));
                            MostrarSegundoPanel(true);
                        }
                    }else {
                        listaProducto.clear();

                        AlertDialog.Builder builder = new AlertDialog.Builder(TomaPedidoActivity.this);
                        builder.setMessage("No se llego a encontrar el registro")
                                .setNegativeButton("Aceptar",null)
                                .create()
                                .show();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) { e.printStackTrace(); }
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            progressDialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(TomaPedidoActivity.this);
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

    private void MostrarPrimerPanel(Boolean estado) {

        if(estado){

            tvDescripcionProducto.setVisibility(View.VISIBLE);
            tvStock.setVisibility(View.VISIBLE);
            tvUnidad.setVisibility(View.VISIBLE);
            tvPrecio.setVisibility(View.VISIBLE);
            tvSubtotal.setVisibility(View.VISIBLE);
            etCantidad.setVisibility(View.VISIBLE);
            textView4.setVisibility(View.VISIBLE);
            textView5.setVisibility(View.VISIBLE);
            textView8.setVisibility(View.VISIBLE);
            textView9.setVisibility(View.VISIBLE);
            textView13.setVisibility(View.VISIBLE);
            btnVerificar.setVisibility(View.VISIBLE);

        }else{

            tvDescripcionProducto.setVisibility(View.GONE);
            tvStock.setVisibility(View.GONE);
            tvUnidad.setVisibility(View.GONE);
            tvPrecio.setVisibility(View.GONE);
            tvSubtotal.setVisibility(View.GONE);
            etCantidad.setVisibility(View.GONE);
            textView4.setVisibility(View.GONE);
            textView5.setVisibility(View.GONE);
            textView8.setVisibility(View.GONE);
            textView9.setVisibility(View.GONE);
            textView13.setVisibility(View.GONE);
            btnVerificar.setVisibility(View.GONE);

        }
    }

    private void MostrarSegundoPanel(Boolean estado) {

        if(estado){
            btnAgregar.setVisibility(View.VISIBLE);
        }else{
            btnAgregar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed(){

    }

}


