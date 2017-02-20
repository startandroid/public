package ru.startandroid.vocabulary.dictionary.recordlist.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.startandroid.vocabulary.R;
import ru.startandroid.vocabulary.data.record.Record;
import ru.startandroid.vocabulary.utils.click.Click;
import rx.Observable;
import rx.subjects.PublishSubject;

public class RecordAdapter extends RecyclerView.Adapter<RecordHolder> {

    private List<Record> data = new ArrayList<>();
    private Context context;
    private final PublishSubject<Click<Record>> onRecordClickSubject = PublishSubject.create();

    public RecordAdapter(Context context) {
        this.context = context;
        setHasStableIds(true);
    }

    public void setData(Collection<Record> records) {
        data.clear();
        data.addAll(records);
        notifyDataSetChanged();
    }

    @Override
    public RecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.record_list_item, parent, false);
        RecordHolder recordHolder = new RecordHolder(view);
        recordHolder.getClicks().subscribe(onRecordClickSubject);
        return recordHolder;
    }

    @Override
    public void onBindViewHolder(RecordHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private Record getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    public Observable<Click<Record>> getRecordClicks() {
        return onRecordClickSubject.asObservable();
    }
}
