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

public class FeedFragment extends Fragment implements View.OnClickListener{

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

        mViewHolder.textHelp            = view.findViewById(R.id.txt_help);
        mViewHolder.txtOther            = view.findViewById(R.id.txt_other);
        mViewHolder.txtAskRide          = view.findViewById(R.id.txt_ask_ride);
        mViewHolder.txtShareFood        = view.findViewById(R.id.txt_share_food);
        mViewHolder.txtDonateGift       = view.findViewById(R.id.txt_donate_gift);
        mViewHolder.textBorrowTool      = view.findViewById(R.id.txt_borrow_tool);
        mViewHolder.txtGroupWorkout     = view.findViewById(R.id.txt_group_workout);
        mViewHolder.txtInviteFriends    = view.findViewById(R.id.txt_invite_friend);

        mViewHolder.textHelp.setOnClickListener(this);
        mViewHolder.txtOther.setOnClickListener(this);
        mViewHolder.txtAskRide.setOnClickListener(this);
        mViewHolder.txtShareFood.setOnClickListener(this);
        mViewHolder.txtDonateGift.setOnClickListener(this);
        mViewHolder.textBorrowTool.setOnClickListener(this);
        mViewHolder.txtGroupWorkout.setOnClickListener(this);
        mViewHolder.txtInviteFriends.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), RequestActivity.class);

        switch (v.getId()){
            case R.id.txt_borrow_tool:
                intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_BORROW_TOOL.ordinal());
                break;
            case R.id.txt_help:
                intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_HELP.ordinal());
                break;
            case R.id.txt_group_workout:
                intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_WORKOUT.ordinal());
                break;
            case R.id.txt_ask_ride:
                intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_ASK_RIDE.ordinal());
                break;
            case R.id.txt_share_food:
                intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_SHARE_FOOD.ordinal());
                break;
            case R.id.txt_invite_friend:
                intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_INVITE_FRIENDS.ordinal());
                break;
            case R.id.txt_donate_gift:
                intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_DONATE_GIFT.ordinal());
                break;
            case R.id.txt_other:
                intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_OTHER.ordinal());
                break;
        }
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