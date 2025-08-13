package com.aula.mobile_hivemind.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
// import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.mobile_hivemind.R;
import com.aula.mobile_hivemind.recyclerViewParadas.ParadaAdapter;
import com.aula.mobile_hivemind.recyclerViewParadas.ParadaModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {

    private ChipGroup chipGroupSetores;
    private Chip chipTodos;

    private RecyclerView recyclerViewParadas;
    private ParadaAdapter paradaAdapter; // *** NOVO: Declare o adapter como variável de instância ***

    private List<ParadaModel> allParadasList; // Lista original completa de todas as paradas

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chipGroupSetores = view.findViewById(R.id.chipGroupSetores);
        recyclerViewParadas = view.findViewById(R.id.recyclerViewParadas);

        // 1. Obtenha TODOS os itens de parada do seu "banco de dados" inicialmente
        allParadasList = getAllParadasFromDatabase();

        // 2. Extraia os setores únicos dos itens de parada obtidos
        Set<String> uniqueSectors = new HashSet<>();
        for (ParadaModel parada : allParadasList) {
            if (parada.getSetor() != null && !parada.getSetor().trim().isEmpty()) {
                uniqueSectors.add(parada.getSetor());
            }
        }
        List<String> sectorsList = new ArrayList<>(uniqueSectors);

        // 3. Ordene os setores alfabeticamente
        Collections.sort(sectorsList);

        // 4. Crie e adicione dinamicamente os Chips com base nos setores únicos
        addChipsToChipGroup(sectorsList);

        // 5. Configure o RecyclerView com os dados iniciais (todas as paradas)
        recyclerViewParadas.setLayoutManager(new LinearLayoutManager(getContext()));
        // *** NOVO: Inicialize o adapter APENAS UMA VEZ ***
        paradaAdapter = new ParadaAdapter(new ArrayList<>(allParadasList)); // Passa uma cópia para evitar modificações diretas
        recyclerViewParadas.setAdapter(paradaAdapter);
    }

    private void addChipsToChipGroup(List<String> sectors) {
        chipGroupSetores.removeAllViews();

        chipTodos = new Chip(getContext(), null, 0);
        chipTodos.setText("Todos");
        chipTodos.setId(View.generateViewId());
        chipTodos.setCheckable(true);
        chipTodos.setChecked(true);
        chipGroupSetores.addView(chipTodos);

        for (String sectorName : sectors) {
            Chip chip = new Chip(getContext(), null, 0);
            chip.setText(sectorName);
            chip.setId(View.generateViewId());
            chip.setCheckable(true);
            chipGroupSetores.addView(chip);
        }

        chipGroupSetores.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                if (checkedIds.isEmpty()) {
                    chipTodos.setChecked(true);
                    Log.d("HomeFragment", "Nenhum chip selecionado. Re-selecionando 'Todos'.");
                    // *** NOVO: Use o método updateData do adapter ***
                    paradaAdapter.updateData(new ArrayList<>(allParadasList)); // Passa uma cópia de todos os dados
                } else {
                    int selectedChipId = checkedIds.get(0);
                    Chip selectedChip = group.findViewById(selectedChipId);
                    if (selectedChip != null) {
                        String selectedSectorName = selectedChip.getText().toString();
                        Log.d("HomeFragment", "Setor selecionado: " + selectedSectorName);

                        if (selectedSectorName.equals("Todos")) {
                            // *** NOVO: Use o método updateData do adapter ***
                            paradaAdapter.updateData(new ArrayList<>(allParadasList)); // Passa uma cópia de todos os dados
                        } else {
                            List<ParadaModel> filteredList = filterParadasBySector(selectedSectorName);
                            // *** NOVO: Use o método updateData do adapter ***
                            paradaAdapter.updateData(filteredList);
                        }
                    }
                }
            }
        });
    }

    private List<ParadaModel> getAllParadasFromDatabase() {
        List<ParadaModel> paradas = new ArrayList<>();
        paradas.add(new ParadaModel("Parada Estação Central", "Setor A", "08:00"));
        paradas.add(new ParadaModel("Parada Praça da Matriz", "Setor B", "08:15"));
        paradas.add(new ParadaModel("Parada Rua do Comércio", "Setor A", "08:30"));
        paradas.add(new ParadaModel("Parada Avenida Principal", "Setor C", "08:45"));
        paradas.add(new ParadaModel("Parada Terminal Rodoviário", "Setor D", "09:00"));
        paradas.add(new ParadaModel("Parada Mercado Público", "Setor B", "09:15"));
        paradas.add(new ParadaModel("Parada Hospital Municipal", "Setor A", "09:30"));
        paradas.add(new ParadaModel("Parada Centro de Convenções", "Setor E", "09:45"));
        paradas.add(new ParadaModel("Parada Museu Histórico", "Setor C", "10:00"));
        paradas.add(new ParadaModel("Parada Parque Urbano", "Setor D", "10:15"));
        return paradas;
    }

    private List<ParadaModel> filterParadasBySector(String sectorName) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return allParadasList.stream()
                    .filter(parada -> parada.getSetor() != null && parada.getSetor().equals(sectorName))
                    .collect(Collectors.toList());
        } else {
            List<ParadaModel> filteredList = new ArrayList<>();
            for (ParadaModel parada : allParadasList) {
                if (parada.getSetor() != null && parada.getSetor().equals(sectorName)) {
                    filteredList.add(parada);
                }
            }
            return filteredList;
        }
    }
}