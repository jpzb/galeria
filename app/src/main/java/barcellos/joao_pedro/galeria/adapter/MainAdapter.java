package barcellos.joao_pedro.galeria.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import barcellos.joao_pedro.galeria.activity.MainActivity;
import barcellos.joao_pedro.galeria.R;
import barcellos.joao_pedro.galeria.model.Util;

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
        LayoutInflater inflater = LayoutInflater.from(mainActivity);
        // Inflador que irá ler os itens do layout de MainActivity e cria-los
        View v = inflater.inflate(R.layout.list_item, parent, false);
        // Elementos criados pelo inflador serão armazenados na View v
        return new MyViewHolder(v);
        // v será armazenado no objeto MyViewHolder e será retornado pela função
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        // Pegando o ImageView
        View v = holder.itemView;
        ImageView imPhoto = v.findViewById(R.id.imItem);

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
        return photos.size();
    }
}
