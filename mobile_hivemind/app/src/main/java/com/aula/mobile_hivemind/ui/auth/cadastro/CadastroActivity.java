package com.aula.mobile_hivemind.ui.auth.cadastro;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.aula.mobile_hivemind.R;
import com.aula.mobile_hivemind.databinding.ActivityCadastroBinding;

public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configura a navegação (se você estiver usando Navigation Component no cadastro também)
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.cadastroBasicoFragment, R.id.cadastroEnderecoFragment) // exemplo de fragments possíveis
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }
}
