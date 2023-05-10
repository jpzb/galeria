package barcellos.joao_pedro.galeria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> photos = new ArrayList<>();
    // Lista com todos os URI's das fotos


    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pegando a toolbar do MainActivity
        Toolbar toolbar = findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);
        // Setando a toolbar como padrão da MainActivity

        // Acessando o diretorório
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] files = dir.listFiles();
        // Pegando todas as fotos já salvas anteriormente e adicionando na lista de fotos
        for(int i = 0; i < files.length; i++){
            photos.add(files[i].getAbsolutePath());
        }

        mainAdapter = new MainAdapter(MainActivity.this, photos);
        // Pegando o MainAdapter

        // Pegando o RecyclerView da MainActivity e setando nele o MainAdapter
        RecyclerView rvGallery = findViewById(R.id.rvGallery);
        rvGallery.setAdapter(mainAdapter);

        // Calculando quantas columas cabem na tela
        float w = getResources().getDimension(R.dimen.itemWidth);
        int numberOfColumns = Util.calculateNoOfColumns(    MainActivity.this, w);

        // Criando o gridLayout e setando no RecycleView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, numberOfColumns);
        rvGallery.setLayoutManager(gridLayoutManager);
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