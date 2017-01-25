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
import hr.translater.mvp.models.Word;

/**
 * Created by Igor on 24.1.2017..
 */

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.ViewHolder> {

    private final OnItemClickListener listener;
    private List<Word> data;
    private Context context;

    public WordsAdapter(Context context, List<Word> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public WordsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, null);
        ButterKnife.bind(this,view);

        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WordsAdapter.ViewHolder holder, int position) {
        holder.click(data.get(position), listener);
        holder.tvWord.setText(data.get(position).getWord());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickListener {
        void onClick(Word item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_word) TextView tvWord;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void click(final Word word, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(word);
                }
            });
        }
    }
}
