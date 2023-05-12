package barcellos.joao_pedro.galeria;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter {

    MainActivity mainActivity;
    List<String> photos; // Lista de strings com os URI's das fotos

    public MainAdapter(MainActivity mainActivity, List<String> photos){
        this.mainActivity = mainActivity;
        this.photos = photos;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        // Pegando o ImageView
        ImageView imPhoto = holder.itemView.findViewById(R.id.imItem);

        // Colocando na variável w o valor setado no dimen.xml
        int w = (int) mainActivity.getResources().getDimension(R.dimen.itemWidth);

        // Colocando na variável h o valor setado no dimen.xml
        int h = (int) mainActivity.getResources().getDimension(R.dimen.itemHeight);

        // Pegando a imagem, colocando o tamanho de w e h e carregando em um bitmap
        Bitmap bitmap = Util.getBitmap(photos.get(position), w, h);

        // Colocando o Bitmap no ImageView
        imPhoto.setImageBitmap(bitmap);
        // Quando o usuário clicar em uma imagem
        imPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navegando para a PhotoActivity
                mainActivity.startPhotoActivity(photos.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
