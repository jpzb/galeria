package barcellos.joao_pedro.galeria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toolbar;

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
}