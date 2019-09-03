package kz.zhakhanyergali.qrscanner.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.Result;
import butterknife.BindView;
import butterknife.OnClick;
import kz.zhakhanyergali.qrscanner.Entidades.Clientes;
import kz.zhakhanyergali.qrscanner.Entidades.Identificadores;
import kz.zhakhanyergali.qrscanner.Entidades.Usuario;
import kz.zhakhanyergali.qrscanner.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    Clientes clientes;
    Usuario usuario;
    Identificadores identificadores;
    String tipoMenu,monto,indice,deDondeViene;
    ImageButton ibregresar;

    // Init ui elements
    @BindView(R.id.lightButton) ImageView flashImageView;

    //Variables
    private ZXingScannerView mScannerView;
    private boolean flashState = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.CAMERA},
                1);

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);

        clientes = (Clientes)getIntent().getSerializableExtra("Cliente");
        usuario = (Usuario) getIntent().getSerializableExtra("Usuario");
        identificadores = (Identificadores)getIntent().getSerializableExtra("Identificadores");
        tipoMenu = getIntent().getStringExtra("tipoMenu");
        monto = getIntent().getStringExtra("monto");
        indice = getIntent().getStringExtra("indice");
        deDondeViene = getIntent().getStringExtra("deDondeViene");
        ibregresar = findViewById(R.id.ibregresar);
        ibregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if (tipoMenu.equals("TomaPedido")){

                Intent intent = new Intent(MainActivity.this,TomaPedidoActivity.class);
                intent.putExtra("indice", indice);
                intent.putExtra("monto", monto);
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

            }else{

                Intent intent1 = new Intent(MainActivity.this,BusquedaClienteActivity.class);
                intent1.putExtra("tipoMenu", ""+tipoMenu);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("Usuario", usuario);
                intent1.putExtras(bundle1);
                startActivity(intent1);
                finish();
            }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(final Result rawResult) {

        // adding result to history

        clientes = (Clientes)getIntent().getSerializableExtra("Cliente");
        usuario = (Usuario) getIntent().getSerializableExtra("Usuario");
        identificadores = (Identificadores)getIntent().getSerializableExtra("Identificadores");
        tipoMenu = getIntent().getStringExtra("tipoMenu");
        monto = getIntent().getStringExtra("monto");
        deDondeViene = getIntent().getStringExtra("deDondeViene");
        Toast.makeText(this, "El indice en la lectora es: " +indice, Toast.LENGTH_SHORT).show();
        // show custom alert dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);

        View v = dialog.getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        TextView text = (TextView) dialog.findViewById(R.id.someText);
        text.setText(rawResult.getText());
        ImageView img = (ImageView) dialog.findViewById(R.id.imgOfDialog);
        img.setImageResource(R.drawable.ic_done_gr);

        Button copy = (Button) dialog.findViewById(R.id.aceptarButton);
        Button cancel = (Button) dialog.findViewById(R.id.cancelButton);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", rawResult.getText());
                clipboard.setPrimaryClip(clip);

                if (deDondeViene.equals("TomaPedido")){

                    Intent intent = new Intent(MainActivity.this,TomaPedidoActivity.class);
                    intent.putExtra("codigo",rawResult.toString());

                    Toast.makeText(MainActivity.this, "El indice es : " + indice, Toast.LENGTH_SHORT).show();
                    intent.putExtra("monto", monto);
                    intent.putExtra("QR","Ok");
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

                }else if(deDondeViene.equals("cliente")){

                    Intent intent1 = new Intent(MainActivity.this,BusquedaClienteActivity.class);
                    intent1.putExtra("tipoMenu", ""+tipoMenu);
                    intent1.putExtra("codigo",rawResult.toString());
                    intent1.putExtra("QR","Ok");
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("Usuario", usuario);
                    intent1.putExtras(bundle1);
                    startActivity(intent1);
                    finish();

                }

                dialog.dismiss();
                mScannerView.resumeCameraPreview(MainActivity.this);
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mScannerView.resumeCameraPreview(MainActivity.this);
            }
        });
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(MainActivity.this, "Permission denied to camera", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @OnClick
    void mainActivityOnClickEvents(View v) {

        switch (v.getId()) {
            case R.id.lightButton:
                if(flashState==false) {
                    v.setBackgroundResource(R.drawable.ic_flash_off);
                    Toast.makeText(getApplicationContext(), "Flashlight turned on", Toast.LENGTH_SHORT).show();
                    mScannerView.setFlash(true);
                    flashState = true;
                }else if(flashState) {
                    v.setBackgroundResource(R.drawable.ic_flash_on);
                    Toast.makeText(getApplicationContext(), "Flashlight turned off", Toast.LENGTH_SHORT).show();
                    mScannerView.setFlash(false);
                    flashState = false;
                }
                break;
        }
    }
}
