package hr.translater.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.translater.R;

/**
 * Created by Igor on 27.1.2017..
 */

public class TranslateAdapter extends RecyclerView.Adapter<TranslateAdapter.ViewHolder> {

    private List<String> data;
    private Context context;

    public TranslateAdapter(Context context, List<String> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public TranslateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.words_fragment_item, null);
        ButterKnife.bind(this,view);

        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new TranslateAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TranslateAdapter.ViewHolder holder, int position) {
        holder.tvWord.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_word)
        TextView tvWord;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
