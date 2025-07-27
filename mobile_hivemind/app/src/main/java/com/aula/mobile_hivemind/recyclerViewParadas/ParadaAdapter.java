package com.aula.mobile_hivemind.recyclerViewParadas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.mobile_hivemind.R;

import java.util.List;

public class ParadaAdapter extends RecyclerView.Adapter<ParadaAdapter.MaquinaViewHolder> {

    private List<ParadaModel> listaMaquinas;

    public ParadaAdapter(List<ParadaModel> listaMaquinas) {
        this.listaMaquinas = listaMaquinas;
    }

    public static class MaquinaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome_IdMaq, tvSetprMaq, tvTempoParada;

        public MaquinaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome_IdMaq = itemView.findViewById(R.id.tvNome_IdMaq);
            tvSetprMaq = itemView.findViewById(R.id.tvSetprMaq);
            tvTempoParada = itemView.findViewById(R.id.tvTempoParada);
        }
    }

    @NonNull
    @Override
    public MaquinaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.parada_item, parent, false);
        return new MaquinaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MaquinaViewHolder holder, int position) {
        ParadaModel maquina = listaMaquinas.get(position);
        holder.tvNome_IdMaq.setText(maquina.getNome());
        holder.tvSetprMaq.setText(maquina.getSetor());
        holder.tvTempoParada.setText(maquina.getHora());
    }

    @Override
    public int getItemCount() {
        return listaMaquinas.size();
    }
}

