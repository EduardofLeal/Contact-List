package com.example.teste;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SegundaTela extends AppCompatActivity {

    EditText txtNome, txtNumero, txtEndereco, txtEmail;
    ImageView imgContato;
    ListView listaDeContatos;
    TextView txtTitulo;
    Button btnAdicionar;
    Uri imagemUri;

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

            }
        });
        builder.create().show();
    }
    public void clicaCarregarImagem(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a photo for this contact"), 1);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (resultCode == 1) {
                imagemUri = data.getData();
                imgContato.setImageURI(data.getData());
            }
        }
    }
}