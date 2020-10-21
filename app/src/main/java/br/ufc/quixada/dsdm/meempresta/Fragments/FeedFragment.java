package br.ufc.quixada.dsdm.meempresta.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.ufc.quixada.dsdm.meempresta.FindRequestsActivity;
import br.ufc.quixada.dsdm.meempresta.Models.Request;
import br.ufc.quixada.dsdm.meempresta.Models.User;
import br.ufc.quixada.dsdm.meempresta.Models.enums.RequestStatus;
import br.ufc.quixada.dsdm.meempresta.R;
import br.ufc.quixada.dsdm.meempresta.RequestActivity;
import br.ufc.quixada.dsdm.meempresta.Models.enums.RequestType;
import br.ufc.quixada.dsdm.meempresta.utils.Constants;
import br.ufc.quixada.dsdm.meempresta.utils.DBCollections;
import br.ufc.quixada.dsdm.meempresta.utils.DrawableToBitmap;
import br.ufc.quixada.dsdm.meempresta.utils.OfflineUser;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;


public class FeedFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback,
        GoogleMap.OnMapClickListener {

    GoogleMap gMap;

    OfflineUser offlineUser;
    FirebaseFirestore mFirestore;
    FusedLocationProviderClient locationClient;

    private final ViewHolder mViewHolder = new ViewHolder();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feed, container, true);

        mViewHolder.textHelp = view.findViewById(R.id.txt_help);
        mViewHolder.txtOther = view.findViewById(R.id.txt_other);
        mViewHolder.txtAskRide = view.findViewById(R.id.txt_ask_ride);
        mViewHolder.mapFind = view.findViewById(R.id.map_find_neighbors);
        mViewHolder.txtShareFood = view.findViewById(R.id.txt_share_food);
        mViewHolder.txtDonateGift = view.findViewById(R.id.txt_donate_gift);
        mViewHolder.textBorrowTool = view.findViewById(R.id.txt_borrow_tool);
        mViewHolder.txtGroupWorkout = view.findViewById(R.id.txt_group_workout);
        mViewHolder.txtInviteFriends = view.findViewById(R.id.txt_invite_friend);

        mViewHolder.textHelp.setOnClickListener(this);
        mViewHolder.txtOther.setOnClickListener(this);
        mViewHolder.txtAskRide.setOnClickListener(this);
        mViewHolder.txtShareFood.setOnClickListener(this);
        mViewHolder.txtDonateGift.setOnClickListener(this);
        mViewHolder.textBorrowTool.setOnClickListener(this);
        mViewHolder.txtGroupWorkout.setOnClickListener(this);
        mViewHolder.txtInviteFriends.setOnClickListener(this);

        offlineUser = new OfflineUser(getContext());
        mFirestore = FirebaseFirestore.getInstance();
        mViewHolder.mapFind.onCreate(savedInstanceState);
        locationClient = LocationServices.getFusedLocationProviderClient(getContext());

        mViewHolder.mapFind.getMapAsync(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewHolder.mapFind.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mViewHolder.mapFind.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewHolder.mapFind.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mViewHolder.mapFind.onLowMemory();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), RequestActivity.class);

        switch (v.getId()){
            case R.id.txt_borrow_tool:
                intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_BORROW_TOOL.getCode());
                break;
            case R.id.txt_help:
                intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_HELP.getCode());
                break;
            case R.id.txt_group_workout:
                intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_WORKOUT.getCode());
                break;
            case R.id.txt_ask_ride:
                intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_ASK_RIDE.getCode());
                break;
            case R.id.txt_share_food:
                intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_SHARE_FOOD.getCode());
                break;
            case R.id.txt_invite_friend:
                intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_INVITE_FRIENDS.getCode());
                break;
            case R.id.txt_donate_gift:
                intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_DONATE_GIFT.getCode());
                break;
            case R.id.txt_other:
                intent.putExtra(RequestType.REQUEST_TYPE.name(), RequestType.REQUEST_OTHER.getCode());
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMinZoomPreference(12);
        gMap.setMaxZoomPreference(12);
        gMap.setOnMapClickListener(this);

        String userId = offlineUser.getString(Constants.USER_ID);
        mFirestore.collection(DBCollections.USER_COLLECTION).document(userId).get()
        .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User user = task.getResult().toObject(User.class);
                assert user != null;
                LatLng myLocation = new LatLng(user.getLocation().getLatitude(), user.getLocation().getLongitude());
                gMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));

                mFirestore.collection(DBCollections.REQUEST_COLLECTION)
                .whereNotEqualTo("owner", userId)
                .whereEqualTo("status", RequestStatus.NEW.getCode())
                .get().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        task1.getResult().toObjects(Request.class).forEach(r -> {
                            gMap.addMarker(new MarkerOptions()
                                    .title(r.getTitle())
                                    .icon(DrawableToBitmap.getIcon(getContext(), r.getType()))
                                    .position(new LatLng(r.getLocation().getLatitude(), r.getLocation().getLongitude()))
                            );
                        });
                    }
                });
            }
        });
    }

    @Override
    public void onMapClick(LatLng latLng) {
        startActivity(new Intent(getContext(), FindRequestsActivity.class));
    }

    private static class ViewHolder {
        TextView textBorrowTool, textHelp, txtGroupWorkout, txtAskRide,
                txtShareFood, txtInviteFriends, txtDonateGift, txtOther;
        MapView mapFind;
    }
}