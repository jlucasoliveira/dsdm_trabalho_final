package br.ufc.quixada.dsdm.meempresta.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.ufc.quixada.dsdm.meempresta.DealActivity;
import br.ufc.quixada.dsdm.meempresta.Models.enums.RequestStatus;
import br.ufc.quixada.dsdm.meempresta.R;
import br.ufc.quixada.dsdm.meempresta.Models.Request;
import br.ufc.quixada.dsdm.meempresta.Models.enums.RequestType;

import java.text.SimpleDateFormat;
import java.util.List;

public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Request> mRequests;

    public RecordListAdapter(Context context, List<Request> requests) {
        this.mContext = context;
        this.mRequests = requests;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordListAdapter.ViewHolder holder, int position) {
        Request request = mRequests.get(position);
        holder.txtTitle.setText('[' + RequestType.toEnum(request.getType()).getDescription() + "] "
                + request.getTitle());
        holder.txtDescription.setText(request.getDescription());
        holder.txtLoanDate.setText(
                new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm").format(request.getData().toDate())
        );

        if (request.getStatus().equals(RequestStatus.DONE.getCode())) holder.imgDone.setVisibility(View.VISIBLE);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, DealActivity.class);
            intent.putExtra("r_id", request.getId());
            intent.putExtra("r_title", request.getTitle());
            intent.putExtra("r_description", request.getDescription());
            intent.putExtra("r_date", request.getData());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mRequests.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgDone;
        TextView txtTitle;
        TextView txtLoanDate;
        TextView txtDescription;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDone = itemView.findViewById(R.id.img_done_request);
            txtTitle = itemView.findViewById(R.id.txt_title_record);
            txtLoanDate = itemView.findViewById(R.id.loan_date_record);
            txtDescription = itemView.findViewById(R.id.txt_description_record);
        }

    }
}
