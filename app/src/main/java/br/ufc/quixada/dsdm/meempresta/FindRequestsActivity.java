package br.ufc.quixada.dsdm.meempresta;

import android.content.Intent;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;

import br.ufc.quixada.dsdm.meempresta.Models.Request;
import br.ufc.quixada.dsdm.meempresta.Models.User;
import br.ufc.quixada.dsdm.meempresta.Models.enums.RequestStatus;
import br.ufc.quixada.dsdm.meempresta.utils.Constants;
import br.ufc.quixada.dsdm.meempresta.utils.DBCollections;
import br.ufc.quixada.dsdm.meempresta.utils.DrawableToBitmap;
import br.ufc.quixada.dsdm.meempresta.utils.OfflineUser;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FindRequestsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private OfflineUser offlineUser;
    private FirebaseFirestore mFirestore;
    private List<Request> requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_requests);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        offlineUser = new OfflineUser(this);
        mFirestore = FirebaseFirestore.getInstance();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setMinZoomPreference(10);
        mMap.setOnInfoWindowClickListener(this);

        String userId = offlineUser.getString(Constants.USER_ID);

        mFirestore.collection(DBCollections.USER_COLLECTION).document(userId)
            .get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    User user = task.getResult().toObject(User.class);
                    assert user != null;
                    LatLng myLocation = new LatLng(user.getLocation().getLatitude(), user.getLocation().getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));

                    mFirestore.collection(DBCollections.REQUEST_COLLECTION)
                    .whereNotEqualTo("owner", userId)
                    .whereEqualTo("status", RequestStatus.NEW.getCode())
                    .get().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            task1.getResult().toObjects(Request.class).forEach(r -> {
                                mMap.addMarker(new MarkerOptions()
                                    .title(r.getTitle())
                                    .position(new LatLng(r.getLocation().getLatitude(),  r.getLocation().getLongitude()))
                                    .icon(DrawableToBitmap.getIcon(this, r.getType()))
                                ).setTag(r);
                            });
                        }
                    });
                }
            });
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Request r = (Request) marker.getTag();

        Intent intent = new Intent(this, DealActivity.class);
        intent.setAction(Constants.HELP_SOMEONE);
        intent.putExtra(Constants.REQUEST_ID, r.getId());
        intent.putExtra(Constants.REQUEST_TITLE, r.getTitle());
        intent.putExtra(Constants.REQUEST_OWNER, r.getOwner());
        intent.putExtra(Constants.REQUEST_STATUS, r.getStatus());
        intent.putExtra(Constants.REQUEST_LOAN_DATE, r.getDate());
        intent.putExtra(Constants.REQUEST_DESCRIPTION, r.getDescription());
        startActivity(intent);
    }
}