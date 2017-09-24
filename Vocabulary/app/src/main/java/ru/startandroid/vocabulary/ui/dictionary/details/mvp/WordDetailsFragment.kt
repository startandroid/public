package ru.startandroid.vocabulary.ui.dictionary.details.mvp


import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import butterknife.BindView
import ru.startandroid.domain.model.Word
import ru.startandroid.vocabulary.R
import ru.startandroid.vocabulary.base.dagger.BaseModule
import ru.startandroid.vocabulary.base.ui.BaseFragment
import ru.startandroid.vocabulary.common.EXTRA_ACTION
import ru.startandroid.vocabulary.common.WordDetailsAction
import ru.startandroid.vocabulary.common.string
import ru.startandroid.vocabulary.ui.dictionary.details.dagger.WordDetailsDagger


class WordDetailsFragment : BaseFragment<WordDetailsContract.Presenter>(), WordDetailsContract.View {

    companion object {
        fun newInstance(action: WordDetailsAction): WordDetailsFragment {
            val fragment = WordDetailsFragment()
            val args = Bundle()
            // TODO use constant
            args.putSerializable(EXTRA_ACTION, action)
            fragment.arguments = args
            return fragment
        }
    }

    @BindView(R.id.value)
    lateinit var editTextValue: EditText

    @BindView(R.id.definition)
    lateinit var editTextDefinition: EditText

    @BindView(R.id.sample)
    lateinit var editTextSample: EditText

    @BindView(R.id.translate)
    lateinit var editTextTranslate: EditText


    override fun getLayoutId(): Int {
        return R.layout.fragment_word_details;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.attachView(this);
        presenter.viewIsReady();
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.word_details, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_save -> presenter.onSaveClick()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun fillWord(word: Word?) {
        word?.apply {
            value = editTextValue.string
            definition = editTextDefinition.string
            sample = editTextSample.string
            translate = editTextTranslate.string
        }
    }

    override fun showWord(word: Word?) {
        word?.apply {
            editTextValue.string = value
            editTextDefinition.string = definition
            editTextSample.string = sample
            editTextTranslate.string = translate
        }
    }


    override fun createModule(): BaseModule? {
        return WordDetailsDagger.WordDetailsModule(arguments.get(EXTRA_ACTION) as WordDetailsAction)
    }

    override fun closeScreen() {
        activity?.finish()
    }


}
