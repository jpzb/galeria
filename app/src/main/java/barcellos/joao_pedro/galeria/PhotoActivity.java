package barcellos.joao_pedro.galeria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class PhotoActivity extends AppCompatActivity {

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

}