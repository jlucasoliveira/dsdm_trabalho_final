package br.ufc.quixada.dsdm.meempresta.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.ufc.quixada.dsdm.meempresta.R;
import br.ufc.quixada.dsdm.meempresta.RequestActivity;
import br.ufc.quixada.dsdm.meempresta.models.enums.RequestType;

public class FeedFragment extends Fragment {

    Intent intent;
    private final ViewHolder mViewHolder = new ViewHolder();

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, true);
        intent = new Intent(getContext(), RequestActivity.class);

        mViewHolder.textBorrowTool = view.findViewById(R.id.txt_borrow_tool);
        mViewHolder.textHelp = view.findViewById(R.id.txt_help);

        mViewHolder.textBorrowTool.setOnClickListener(this::onClickRequestBorrowTool);
        // Inflate the layout for this fragment
        return view;
    }

    public void onClickRequestBorrowTool(View v) {
        intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_BORROW_TOOL.ordinal());
        startActivity(intent);
    }

    public void onClickRequestHelp(View v) {
        intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_HELP.ordinal());
        startActivity(intent);
    }

    public void onClickRequestGroupWorkout(View v) {
        intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_WORKOUT.ordinal());
        startActivity(intent);
    }

    public void onClickRequestAskRide(View v) {
        intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_ASK_RIDE.ordinal());
        startActivity(intent);
    }

    public void onClickRequestShareFood(View v) {
        intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_SHARE_FOOD.ordinal());
        startActivity(intent);
    }

    public void onClickRequestInviteFriends( View v) {
        intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_INVITE_FRIENDS.ordinal());
        startActivity(intent);
    }
    public void onClickRequestDonateGift( View v) {
        intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_DONATE_GIFT.ordinal());
        startActivity(intent);
    }

    public void onClickRequestOther( View v) {
        intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_OTHER.ordinal());
        startActivity(intent);
    }

    private static class ViewHolder {
        TextView textBorrowTool;
        TextView textHelp;
        TextView txtGroupWorkout;
        TextView txtAskRide;
        TextView txtShareFood;
        TextView txtInviteFriends;
        TextView txtDonateGift;
        TextView txtOther;
    }
}