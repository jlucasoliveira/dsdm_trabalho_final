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
import br.ufc.quixada.dsdm.meempresta.utils.Constants;
import br.ufc.quixada.dsdm.meempresta.utils.OfflineUser;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordListAdapter.ViewHolder holder, int position) {
        Request request = mRequests.get(position);
        String userID = new OfflineUser(mContext).getString(Constants.USER_ID);
        holder.txtTitle.setText('[' + RequestType.toEnum(request.getType()).getDescription() + "] "
                + request.getTitle());
        holder.txtDescription.setText(request.getDescription());
        holder.txtLoanDate.setText(
                new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm").format(request.getDate().toDate())
        );

        if (!request.getOwner().equals(userID))
            holder.imgStatus.setImageResource(R.drawable.ic_lifesaver);
        else if (request.getStatus().equals(RequestStatus.NEW.getCode()))
            holder.imgStatus.setImageResource(R.drawable.ic_baseline_new_24);
        else if (request.getStatus().equals(RequestStatus.DONE.getCode()))
            holder.imgStatus.setImageResource(R.drawable.ic_baseline_done_24);
        else if (request.getStatus().equals(RequestStatus.CANCELED.getCode()))
            holder.imgStatus.setImageResource(R.drawable.ic_baseline_cancel_24);
        else if (request.getStatus().equals(RequestStatus.PENDING.getCode()))
            holder.imgStatus.setImageResource(R.drawable.ic_baseline_pending_24);


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, DealActivity.class);
            intent.putExtra(Constants.REQUEST_ID, request.getId());
            intent.putExtra(Constants.REQUEST_TITLE, request.getTitle());
            intent.putExtra(Constants.REQUEST_OWNER, request.getOwner());
            intent.putExtra(Constants.REQUEST_STATUS, request.getStatus());
            intent.putExtra(Constants.REQUEST_LOAN_DATE, request.getDate());
            intent.putExtra(Constants.REQUEST_DESCRIPTION, request.getDescription());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mRequests.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgStatus;
        TextView txtTitle;
        TextView txtLoanDate;
        TextView txtDescription;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgStatus = itemView.findViewById(R.id.img_status_request);
            txtTitle = itemView.findViewById(R.id.txt_title_record);
            txtLoanDate = itemView.findViewById(R.id.loan_date_record);
            txtDescription = itemView.findViewById(R.id.txt_description_record);
        }

    }
}
