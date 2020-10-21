package br.ufc.quixada.dsdm.meempresta;

import android.content.Intent;
import android.widget.Toast;
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

import java.util.ArrayList;
import java.util.List;

public class FindRequestsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

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
        mMap.setOnMarkerClickListener(this);

        String userId = offlineUser.getString(Constants.USER_ID);

        mFirestore.collection(DBCollections.USER_COLLECTION).document(userId)
            .get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    User user = task.getResult().toObject(User.class);
                    assert user != null;
                    LatLng myLocation = new LatLng(user.getLatitude(), user.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));

                    //.whereNotEqualTo("owner", userId)
                    mFirestore.collection(DBCollections.REQUEST_COLLECTION)
                    .whereEqualTo("status", RequestStatus.NEW.getCode())
                    .get().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            requests = task1.getResult().toObjects(Request.class);
                            requests.forEach(r -> {
                                mMap.addMarker(new MarkerOptions()
                                    .title(r.getTitle())
                                    .position(new LatLng(r.getLatitude(), r.getLongitude()))
                                    .icon(DrawableToBitmap.getIcon(this, r.getType()))
                                ).setTag(r);
                            });
                        }
                    });
                }
            });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Request r = (Request) marker.getTag();
        Intent intent = new Intent(this, DealActivity.class);
        intent.setAction(Constants.HELP_SOMEONE);
        intent.putExtra(Constants.REQUEST_ID, r.getId());
        intent.putExtra(Constants.REQUEST_TITLE, r.getTitle());
        intent.putExtra(Constants.REQUEST_STATUS, r.getStatus());
        intent.putExtra(Constants.REQUEST_LOAN_DATE, r.getDate());
        intent.putExtra(Constants.REQUEST_DESCRIPTION, r.getDescription());
        startActivity(intent);
        return false;
    }
}