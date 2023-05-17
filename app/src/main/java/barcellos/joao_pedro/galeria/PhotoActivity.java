package barcellos.joao_pedro.galeria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;

public class PhotoActivity extends AppCompatActivity {

    String photoPath;
    // Foto selecionada na MainActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        // Pegando a toolbar do MainActivity
        Toolbar toolbar = findViewById(R.id.tbPhoto);
        setSupportActionBar(toolbar);
        // Setando a toolbar como padrão da MainActivity

        // Pegando a actionbar setada anteriormente e colocando o botaõ de voltar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        photoPath = i.getStringExtra("photo_path");
        // Pegando o caminho da foto enviado pelo MainActivity

        Bitmap bitmap = Util.getBitmap(photoPath);
        // Pegando o Bitmap da foto

        // Pegando o ImageView e colocando a foto nele
        ImageView imphoto = findViewById(R.id.imPhoto);
        imphoto.setImageBitmap(bitmap);

    }
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);

        // Criando um inflador
        MenuInflater inflater = getMenuInflater();

        // Criando as opções do menu photo_activity_tb e colocando na activity
        inflater.inflate(R.menu.photo_activity_tb, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            // Pega o id do item selecionado
            case R.id.opShare:
                // Caso o id for opShare
                sharePhoto();
                // Acionando a função de compartilhamento da foto
                return true;
            default:
                // se não for o id, irá chamar o super
                return super.onOptionsItemSelected(item);
        }
    }

    void sharePhoto(){
        // Gerando uma URI para outras apps
        Uri photoUri = FileProvider.getUriForFile(PhotoActivity.this,
                "barcellos.joao_pedro.galeria.fileprovider", new File(photoPath));
        // Criando uma intenção
        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_STREAM, photoUri);
        // Informando qual o tipo e o arquivo que queremos enviar
        i.setType("image/jpeg");
        // Executando a intenção
        startActivity(i);
    }
}