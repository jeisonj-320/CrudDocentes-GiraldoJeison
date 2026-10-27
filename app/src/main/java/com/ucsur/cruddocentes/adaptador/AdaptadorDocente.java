package com.ucsur.cruddocentes.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ucsur.cruddocentes.R;
import com.ucsur.cruddocentes.modelo.Docente;

import java.util.List;

public class AdaptadorDocente extends BaseAdapter {
    private final Context context;
    private List<Docente> lista;

    public AdaptadorDocente(Context context, List<Docente> lista) {
        this.context = context;
        this.lista = lista;
    }

    public void actualizarLista(List<Docente> nueva) {
        this.lista = nueva;
        notifyDataSetChanged();   // refresca el ListView
    }

    @Override public int getCount() { return lista.size(); }
    @Override public Object getItem(int pos) { return lista.get(pos); }
    @Override public long getItemId(int pos) { return lista.get(pos).getCodigo(); }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        if (convertView == null) {   // reutiliza vistas para ahorrar memoria
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_docente, parent, false);
        }
        Docente d = lista.get(pos);
        ((TextView) convertView.findViewById(R.id.tvCodigo))
                .setText("Código: " + d.getCodigo());
        ((TextView) convertView.findViewById(R.id.tvNombre))
                .setText(d.getNombre() + " " + d.getPaterno() + " " + d.getMaterno());
        ((TextView) convertView.findViewById(R.id.tvDetalle))
                .setText(d.getSexo() + " · S/ " + d.getSueldo());
        return convertView;
    }
}