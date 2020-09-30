package br.ufc.quixada.dsdm.meempresta.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.ufc.quixada.dsdm.meempresta.R;
import br.ufc.quixada.dsdm.meempresta.models.Record;

import java.util.List;

public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.ViewHolder> {

    // ~~TEMP~~
    static List<Record> records = Record.getRecords();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordListAdapter.ViewHolder holder, int position) {
        holder.bind(records.get(position));
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtObjectName;
        TextView txtObjectOwner;
        TextView txtLoanDate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtObjectName = itemView.findViewById(R.id.object_name);
            txtObjectOwner = itemView.findViewById(R.id.object_owner);
            txtLoanDate = itemView.findViewById(R.id.loan_date);
        }

        public void bind(Record record) {
            txtObjectName.setText(record.getObjectName());
            txtObjectOwner.setText(record.getObjectOwner());
            txtLoanDate.setText(record.getData().toString());
        }

    }
}
