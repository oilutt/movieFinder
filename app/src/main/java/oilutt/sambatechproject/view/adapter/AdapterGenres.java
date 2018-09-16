package oilutt.sambatechproject.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import oilutt.sambatechproject.R;
import oilutt.sambatechproject.model.Genre;

public class AdapterGenres extends RecyclerView.Adapter<AdapterGenres.GenreHolder> {

    private List<Genre> mListGenres;
    private Context mContext;

    public AdapterGenres(List<Genre> listGenres, Context context) {
        this.mListGenres = listGenres;
        this.mContext = context;
    }

    @NonNull
    @Override
    public GenreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_bg_accent_rounded, parent, false);
        return new GenreHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreHolder holder, int position) {
        holder.txtItem.setText(mListGenres.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mListGenres.size();
    }

    class GenreHolder extends RecyclerView.ViewHolder {

        TextView txtItem;

        public GenreHolder(View itemView) {
            super(itemView);
            txtItem = itemView.findViewById(R.id.txt_item);
        }
    }
}
