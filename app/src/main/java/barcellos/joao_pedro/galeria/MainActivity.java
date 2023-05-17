package barcellos.joao_pedro.galeria;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.Manifest;

public class MainActivity extends AppCompatActivity {

    List<String> photos = new ArrayList<>();
    // Lista com todos os URI's das fotos

    static int RESULT_TAKE_PICTURE = 1;

    static int RESULT_REQUEST_PERMISSION = 2;

    String currentPhotoPath;

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

        // ArrayList de permissões
        List<String> permissions = new ArrayList<>();
        // Adicionando a permissão de câmera
        permissions.add(Manifest.permission.CAMERA);
        // Chamando a função
        checkForPermissions(permissions);
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

    public void startPhotoActivity(String photoPath){
        // Criando a intenção da PhotoActivity
        Intent i = new Intent(MainActivity.this, PhotoActivity.class);
        // Enviando a foto que foi selecioanda para a intent
        i.putExtra("photo_path", photoPath);
        // Iniciando a Intent
        startActivity(i);
    }

    public void dispatchTakePictureIntent(){
        File f = null;
        // Criando um arquivo vazio
        try{
            f = createImageFile();
        }catch (IOException e){
            // Caso o arquivo não possa ser criado
            Toast.makeText(MainActivity.this, "Não foi possível criar o arquivo.", Toast.LENGTH_LONG).show();
            return;
        }

        currentPhotoPath = f.getAbsolutePath();
        // Salvando o local do arquivo da foto

        // Verificando se a foto é nula
        if(f != null){
            // Gerando um URI para o arquivo
            Uri fUri = FileProvider.getUriForFile(MainActivity.this, "barcellos.joao_pedro.galeria.fileprovider", f);
            // Intent para chamar o app de câmera
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Passando a URI
            i.putExtra(MediaStore.EXTRA_OUTPUT, fUri);
            // Iniciando o app de câmersa
            startActivityForResult(i, RESULT_TAKE_PICTURE);
        }
    }

    private File createImageFile() throws IOException{
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        // Utilizando a data para fazer nomes de arquivos diferentes
        String imageFileName = "JPEG_" + timestamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File f = File.createTempFile(imageFileName, ".jpg", storageDir);
        return f;
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_TAKE_PICTURE){
            // Se a foto foi tirada
            if(resultCode == Activity.RESULT_OK){
                // Adicionando o local da foto na lista de fotos
                photos.add(currentPhotoPath);
                // Avisando o MainAdapter que uma nova foto foi inserida
                mainAdapter.notifyItemInserted(photos.size() - 1);
            }else{
                File f = new File(currentPhotoPath);
                // Se não foi tirada foto, o arquivo será excluído
                f.delete();
            }
        }
    }

    public void checkForPermissions(List<String> permissions){
        List<String> permissionsNotGranted = new ArrayList<>();

        for(String permission : permissions){
            // Percorrendo a lista de permissões e verificando se não foi confirmada
            if(!hasPermission(permission)){
                // Colocando a permissão não confirmada na lista com outras não confirmadas
                permissionsNotGranted.add(permission);
            }
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(permissionsNotGranted.size() > 0){
                // Requisitando as permissões não confirmadas
                requestPermissions(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]), RESULT_REQUEST_PERMISSION);
            }
        }
    }

    private boolean hasPermission(String permission){
        // Verificando se uma permissão já foi permitida
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return ActivityCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        final List<String> permissionsRejected = new ArrayList<>();
        if(requestCode == RESULT_REQUEST_PERMISSION){
            for(String permission : permissions){
                // Verificando se cada permissão foi confirmada
                if(!hasPermission(permission)){
                    // Colocando a permissão não confirmada na lista com outras não confirmadas
                    permissionsRejected.add(permission);
                }
            }
        }

        if(permissionsRejected.size() > 0){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(shouldShowRequestPermissionRationale(permissionsRejected.get(0))){
                    // Informando ao usuário que a permissão é necessária para o funcionamento do app
                    new AlertDialog.Builder(MainActivity.this).setMessage("Para usar essa " +
                            "app é preciso conceder essas permissões").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Requisitando novamente as permissões não confirmadas
                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), RESULT_REQUEST_PERMISSION);
                        }
                    }).create().show();
                }
            }
        }
    }
}