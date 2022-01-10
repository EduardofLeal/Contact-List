package com.example.teste;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class SegundaTela extends AppCompatActivity {

    EditText txtNome, txtNumero, txtEndereco, txtEmail;
    ImageView imgContato;
    ListView listaDeContatos;
    TextView txtTitulo;
    Button btnAdicionar;
    Uri imagemUri;
    boolean fotoCamera;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_tela);

        txtNome = (EditText) findViewById(R.id.txtNome);
        txtNumero = (EditText) findViewById(R.id.txtCell);
        txtEndereco = (EditText) findViewById(R.id.txtEndereco);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        listaDeContatos = (ListView) findViewById(R.id.listaDeContatos);
        imgContato = (ImageView) findViewById(R.id.imgFoto);

        btnAdicionar = (Button) findViewById(R.id.btnSalva);

    }
    public void mudaFoto(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(SegundaTela.this);
        builder.setTitle("Photo");
        builder.setMessage("Take a picture or choose it from your library!");
        builder.setPositiveButton("Library", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clicaCarregarImagem();
            }
        });
        builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clicaTirarFoto();
            }
        });
        builder.create().show();
    }
    public void clicaCarregarImagem(){
        fotoCamera = false;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a photo for this contact"), 1);
    }

    public void clicaTirarFoto(){
        fotoCamera = true;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(fotoCamera == true){
            InputStream stream = null;
            if(requestCode == 0 && resultCode == RESULT_OK){
                try
                {
                    if(bitmap != null){
                        bitmap.recycle();
                    }
                    stream = getContentResolver().openInputStream(data.getData());
                    bitmap = BitmapFactory.decodeStream(stream);
                    imgContato.setImageBitmap(reziseImage(this, bitmap, 120, 120));
                    imgContato.setRotation(90);
                    imagemUri = data.getData();
                    imgContato.setImageURI(data.getData());
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    if(stream != null){
                        try
                        {
                            stream.close();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        else{
            if(resultCode == RESULT_OK){
                if(requestCode == 1){
                    imagemUri = data.getData();
                    imgContato.setImageURI(data.getData());
                }
            }
        }
    }

    public Bitmap reziseImage(Context context, Bitmap bmpOriginal, float newWidth, float newHeight){
        Bitmap novoBmp = null;
        int w = bmpOriginal.getWidth();
        int h = bmpOriginal.getHeight();

        float densityFactor = context.getResources().getDisplayMetrics().density;
        float novoW = newWidth;
        float novoH = newHeight;

        float scaleW = novoW / w;
        float scaleH = novoH / h;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleW, scaleH);

        novoBmp = Bitmap.createBitmap(bmpOriginal, 0, 0, w, h, matrix, true);
        return novoBmp;
    }

    public void salvaContato(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(SegundaTela.this);
        builder.setTitle("Add contact");
        builder.setMessage("Are you sure you sure you want do this?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(SegundaTela.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

}