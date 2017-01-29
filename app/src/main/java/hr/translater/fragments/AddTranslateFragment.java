package hr.translater.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import hr.translater.R;
import hr.translater.mvp.models.TranslateData;
import hr.translater.mvp.presenters.WordsPresenter;
import hr.translater.mvp.views.AddView;
import hr.translater.networking.Service;
import okhttp3.ResponseBody;

/**
 * Created by Igor on 26.1.2017..
 */

public class AddTranslateFragment extends Fragment implements AddView {

    @BindView(R.id.progresss)
    ProgressBar progressBar;

    @BindView(R.id.lang_from_spinner)
    Spinner langFromSpinner;
    @BindView(R.id.lang_to_spinner)
    Spinner langToSpinner;

    @BindView(R.id.et_word)
    EditText etWord;
    @BindView(R.id.et_translation)
    EditText etTranslation;

    String langFrom;
    String langTo;

    private Activity activity;

    private Service service;

    public AddTranslateFragment(Service service){
        this.service = service;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = context instanceof Activity ? (Activity) context : null;
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_translate_fragment,container,false);
        ButterKnife.bind(this,root);

        removeWait();

        return root;
    }

    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String appErrorMessage) {
        Toast.makeText(activity.getApplicationContext(), appErrorMessage,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addTranslateSuccess(ResponseBody translateResponse) {
        Toast.makeText(activity.getApplicationContext(), "Translate added success!",
                Toast.LENGTH_SHORT).show();

        etTranslation.setText("");
        etWord.setText("");
    }

    @OnClick(R.id.btn_add)
    public void onClick() {
        WordsPresenter wordsPresenter = new WordsPresenter(service,this);
        TranslateData translateData = new TranslateData(langFrom.toLowerCase(),langTo.toLowerCase(),etWord.getText().toString(),etTranslation.getText().toString());
        wordsPresenter.addTranslation(translateData);
    }

    @OnItemSelected(R.id.lang_from_spinner)
    public void spinnerLangFromItemSelected(Spinner spinner, int position) {
        langFrom = spinner.getItemAtPosition(position).toString();
    }

    @OnItemSelected(R.id.lang_to_spinner)
    public void spinnerLangToItemSelected(Spinner spinner, int position) {
        langTo = spinner.getItemAtPosition(position).toString();
    }
}
