package com.aula.mobile_hivemind.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
// import android.widget.Toast; // Uncomment if you want to use Toast messages

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {

    private ChipGroup chipGroupSetores;
    private Chip chipTodos;

    private RecyclerView recyclerViewParadas;

    private List<ParadaModel> allParadasList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // If using View Binding:
        // binding = FragmentHomeBinding.inflate(inflater, container, false);
        // View view = binding.getRoot();

        // If not using View Binding (classic findViewById):
        // Make sure this matches your layout file name

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get references to your views
        // If using View Binding:
        // chipGroupSetores = binding.chipGroupSetores;
        // recyclerViewParadas = binding.recyclerViewParadas;

        // If not using View Binding:
        chipGroupSetores = view.findViewById(R.id.chipGroupSetores);
        recyclerViewParadas = view.findViewById(R.id.recyclerViewParadas);

        // 1. Fetch ALL parada items from your database initially
        allParadasList = getAllParadasFromDatabase(); // Implement this method

        // 2. Extract unique sectors from the fetched parada items
        Set<String> uniqueSectors = new HashSet<>();
        for (ParadaModel parada : allParadasList) {
            if (parada.getSetor() != null && !parada.getSetor().trim().isEmpty()) {
                uniqueSectors.add(parada.getSetor());
            }
        }
        List<String> sectorsList = new ArrayList<>(uniqueSectors);
        // Optional: Sort the sectors alphabetically
        // Collections.sort(sectorsList);

        // 3. Dynamically create and add Chips based on unique sectors
        addChipsToChipGroup(sectorsList);

        // 4. Set up the RecyclerView with initial data (all paradas)
        // We create and set the adapter here for the initial display
        recyclerViewParadas.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewParadas.setAdapter(new ParadaAdapter(allParadasList)); // Pass all data initially
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

        // Set up a listener for chip selection
        chipGroupSetores.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                if (checkedIds.isEmpty()) {
                    // This can happen if a chip is unchecked; for singleSelection, we want one always checked
                    chipTodos.setChecked(true); // Re-select "Todos"
                    Log.d("HomeFragment", "No chip selected. Re-selecting 'Todos'.");
                    // Re-create and set adapter with all data
                    recyclerViewParadas.setAdapter(new ParadaAdapter(allParadasList));
                } else {
                    int selectedChipId = checkedIds.get(0);
                    Chip selectedChip = group.findViewById(selectedChipId);
                    if (selectedChip != null) {
                        String selectedSectorName = selectedChip.getText().toString();
                        Log.d("HomeFragment", "Selected sector: " + selectedSectorName);

                        if (selectedSectorName.equals("Todos")) {
                            // Re-create and set adapter with all data
                            recyclerViewParadas.setAdapter(new ParadaAdapter(allParadasList));
                        } else {
                            // Filter the 'allParadasList' based on the selected sector
                            List<ParadaModel> filteredList = filterParadasBySector(selectedSectorName);
                            // Re-create and set adapter with filtered data
                            recyclerViewParadas.setAdapter(new ParadaAdapter(filteredList));
                        }
                        // Optional: Toast.makeText(getContext(), "Selected: " + selectedSectorName, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * Implement this method to fetch all ParadaModel objects from your database.
     * This is where your Room DAO or SQLiteOpenHelper queries would go.
     */
    private List<ParadaModel> getAllParadasFromDatabase() {
        List<ParadaModel> paradas = new ArrayList<>();
        // --- SIMULATE DATABASE FETCH ---
        // Replace with your actual database query (e.g., Room DAO call)
        paradas.add(new ParadaModel("1", "Parada Estação Central", "Setor A"));
        paradas.add(new ParadaModel("2", "Parada Praça da Matriz", "Setor B"));
        paradas.add(new ParadaModel("3", "Parada Rua do Comércio", "Setor A"));
        paradas.add(new ParadaModel("4", "Parada Avenida Principal", "Setor C"));
        paradas.add(new ParadaModel("5", "Parada Terminal Rodoviário", "Setor D"));
        paradas.add(new ParadaModel("6", "Parada Mercado Público", "Setor B"));
        paradas.add(new ParadaModel("7", "Parada Hospital Municipal", "Setor A"));
        paradas.add(new ParadaModel("8", "Parada Centro de Convenções", "Setor E"));
        paradas.add(new ParadaModel("9", "Parada Museu Histórico", "Setor C"));
        paradas.add(new ParadaModel("10", "Parada Parque Urbano", "Setor D"));
        // --- END SIMULATION ---
        return paradas;
    }

    /**
     * Filters the 'allParadasList' based on the selected sector name.
     * Assumes allParadasList is already populated.
     */
    private List<ParadaModel> filterParadasBySector(String sectorName) {
        // For Java 8 and above (Android API 24+), you can use streams:
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return allParadasList.stream()
                    .filter(parada -> parada.getSetor() != null && parada.getSetor().equals(sectorName))
                    .collect(Collectors.toList());
        } else {
            // For older Android versions (API < 24):
            List<ParadaModel> filteredList = new ArrayList<>();
            for (ParadaModel parada : allParadasList) {
                if (parada.getSetor() != null && parada.getSetor().equals(sectorName)) {
                    filteredList.add(parada);
                }
            }
            return filteredList;
        }
    }

    // If using View Binding, uncomment this:
    /*
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Clear the binding reference
    }
    */
}