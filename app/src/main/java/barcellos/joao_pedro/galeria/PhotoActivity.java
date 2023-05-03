package barcellos.joao_pedro.galeria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toolbar;

public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        // Pegando a toolbar do MainActivity
        Toolbar toolbar = findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);
        // Setando a toolbar como padrão da MainActivity
    }
}