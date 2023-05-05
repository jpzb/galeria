package barcellos.joao_pedro.galeria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pegando a toolbar do MainActivity
        Toolbar toolbar = findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);
        // Setando a toolbar como padrão da MainActivity
    }
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);

        // Criando um inflador
        MenuInflater inflater = getMenuInflater();

        // Criando as opções do menu main_activity_tb e colocando na activity
        inflater.inflate(R.menu.main_activity_tb, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            // Pega o id do item selecionado
            case R.id.opCamera:
                // Caso o id for opCamera
                dispatchTakePictureIntent();
                // Acionando a câmera do celular
                return true;
            default:
                // se não for o id, irá chamar o super
                return super.onOptionsItemSelected(item);
        }
    }
}