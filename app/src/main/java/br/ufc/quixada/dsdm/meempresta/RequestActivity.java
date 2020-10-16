package br.ufc.quixada.dsdm.meempresta;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.ufc.quixada.dsdm.meempresta.Models.Request;
import br.ufc.quixada.dsdm.meempresta.Models.enums.RequestStatus;
import br.ufc.quixada.dsdm.meempresta.Models.enums.RequestType;
import br.ufc.quixada.dsdm.meempresta.utils.DBCollections;
import br.ufc.quixada.dsdm.meempresta.utils.ToastMessage;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class RequestActivity extends AppCompatActivity {

    private final int IMAGE_UPLOAD = 0;

    private final ViewHolder mViewHolder = new ViewHolder();

    FirebaseUser mUser;
    StorageReference mStorage;
    FirebaseFirestore mFirestore;

    Uri requestImageUri = Uri.EMPTY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_request);

        mViewHolder.btnRequest = findViewById(R.id.btn_request);
        mViewHolder.txtSBMin = findViewById(R.id.txt_min_radius);
        mViewHolder.txtSBMax = findViewById(R.id.txt_max_radius);
        mViewHolder.imgUploadImage = findViewById(R.id.img_add_image);
        mViewHolder.txtFirstField = findViewById(R.id.txt_first_field);
        mViewHolder.txtSecondField = findViewById(R.id.txt_second_field);
        mViewHolder.editFirstField = findViewById(R.id.edit_first_field);
        mViewHolder.editDescription = findViewById(R.id.edit_description);
        mViewHolder.seekBarDistance = findViewById(R.id.seekBar_distance);

        mFirestore = FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mStorage = FirebaseStorage.getInstance().getReference();

        mViewHolder.btnRequest.setOnClickListener(this::sendRequest);
        mViewHolder.imgUploadImage.setOnClickListener(this::openImage);

        changeUI();

    }

    public void sendRequest( View v ) {
        StorageReference requestImages = null;
        Integer distance = 2 + mViewHolder.seekBarDistance.getProgress();
        String title = mViewHolder.editFirstField.getText().toString().trim();
        String description = mViewHolder.editDescription.getText().toString().trim();
        RequestType requestType = RequestType.toEnum(getIntent().getExtras().getInt(RequestType.REQUEST_TYPE.name()));

        if (requestImageUri != Uri.EMPTY)
            requestImages = mStorage.child("requests/" + requestImageUri.getLastPathSegment());

        if (title.isEmpty()) {
            mViewHolder.editFirstField.setError("Campo obrigatório!");
            return;
        }

        if (description.isEmpty()) {
            mViewHolder.editDescription.setError("Não há convencimento sem argumentação!");
            return;
        }

        Request request = new Request(null, title, description, distance,
                Timestamp.now(), requestType, RequestStatus.NEW, mUser, null);

        mViewHolder.btnRequest.setEnabled(false);

        StorageReference finalRequestImages = requestImages;
        mFirestore.collection(DBCollections.REQUEST_COLLECTION).add(request).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                if (finalRequestImages != null)
                    finalRequestImages.putFile(requestImageUri).addOnCompleteListener(taskImage -> {

                    });

                ToastMessage.showMessage(this,"Requisição realizada com sucesso!");
                finish();
            }
            else {
                ToastMessage.showMessage(this, task.getException().getMessage());
                mViewHolder.btnRequest.setEnabled(true);
            }
        });
    }

    public void openImage(View v) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
        startActivityForResult(intent, IMAGE_UPLOAD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_UPLOAD && resultCode == RESULT_OK && data != null && data.getData() != null) {

        }
    }

    private void changeUI() {
        Bundle bundle = getIntent().getExtras();
        ActionBar actionBar = getSupportActionBar();

        if (bundle != null) {
            Integer actionId = bundle.getInt(RequestType.REQUEST_TYPE.name());
            if (actionId.equals(RequestType.REQUEST_BORROW_TOOL.getCode())) {
                actionBar.setTitle(R.string.txt_title_what_you_need);
                mViewHolder.txtFirstField.setText(R.string.txt_object);
                mViewHolder.editFirstField.setHint(R.string.txt_object_hint_drilling_machine);
                mViewHolder.editDescription.setHint(R.string.txt_object_hint_description);
                mViewHolder.txtSecondField.setText(R.string.txt_object_fetch_radius);
                mViewHolder.btnRequest.setText(R.string.btn_object_asking);
            }
            else if (actionId.equals(RequestType.REQUEST_HELP.getCode())) {
                actionBar.setTitle(R.string.txt_title_what_you_need);
                mViewHolder.txtFirstField.setText(R.string.txt_i_need);
                mViewHolder.editFirstField.setHint(R.string.txt_hint_changing_tire);
                mViewHolder.editDescription.setHint(R.string.txt_hint_description_help);
                mViewHolder.txtSecondField.setText(R.string.txt_help_request_radius);
                mViewHolder.btnRequest.setText(R.string.btn_asking_help);
            }
            else if (actionId.equals(RequestType.REQUEST_WORKOUT.getCode())) {
                actionBar.setTitle(R.string.txt_activity);
                mViewHolder.txtFirstField.setText(R.string.txt_i_need);
                mViewHolder.editFirstField.setHint(R.string.txt_hint_want_create_running_group);
                mViewHolder.editDescription.setHint(R.string.txt_hint_workout_description);
                mViewHolder.txtSecondField.setText(R.string.txt_how_far_can_you_go);
                mViewHolder.btnRequest.setText(R.string.txt_invite_fried);
            }
            else if (actionId.equals(RequestType.REQUEST_ASK_RIDE.getCode())) {
                actionBar.setTitle(R.string.txt_title_ask_ride);
                mViewHolder.txtFirstField.setText(R.string.txt_ask_ride_destiny);
                mViewHolder.editFirstField.setHint(R.string.txt_hint_ask_ride);
                mViewHolder.editDescription.setHint(R.string.txt_hint_ask_ride_description);
                mViewHolder.txtSecondField.setText(R.string.txt_how_far_can_you_go_find);
                mViewHolder.btnRequest.setText(R.string.txt_ask_ride);
            }
            else if (actionId.equals(RequestType.REQUEST_SHARE_FOOD.getCode())) {
                actionBar.setTitle(R.string.txt_title_share_food);
                mViewHolder.txtFirstField.setText(R.string.txt_food);
                mViewHolder.editFirstField.setHint(R.string.txt_hint_share_food);
                mViewHolder.editDescription.setHint(R.string.txt_hint_share_food_description);
                mViewHolder.txtSecondField.setText(R.string.txt_what_distance);
                mViewHolder.btnRequest.setText(R.string.txt_share_food);
            }
            else if (actionId.equals(RequestType.REQUEST_INVITE_FRIENDS.getCode())) {
                actionBar.setTitle(R.string.txt_what_you_wanna_do);
                mViewHolder.txtFirstField.setText(R.string.txt_invite_for);
                mViewHolder.editFirstField.setHint(R.string.txt_hint_invite_for);
                mViewHolder.editDescription.setHint(R.string.txt_hint_invite_friends_description);
                mViewHolder.txtSecondField.setText(R.string.txt_how_far_can_you_go_find);
                mViewHolder.btnRequest.setText(R.string.txt_invite_fried);
            }
            else if (actionId.equals(RequestType.REQUEST_DONATE_GIFT.getCode())) {
                actionBar.setTitle(R.string.txt_what_do_you_want_to_donate);
                mViewHolder.txtFirstField.setText(R.string.txt_object);
                mViewHolder.editFirstField.setHint(R.string.txt_hint_baby_car);
                mViewHolder.editDescription.setHint(R.string.txt_hint_donate_gift_description);
                mViewHolder.txtSecondField.setVisibility(View.INVISIBLE);
                mViewHolder.seekBarDistance.setVisibility(View.INVISIBLE);
                mViewHolder.txtSBMin.setVisibility(View.INVISIBLE);
                mViewHolder.txtSBMax.setVisibility(View.INVISIBLE);
                mViewHolder.btnRequest.setText(R.string.txt_donate_gift);
            }
            else if (actionId.equals(RequestType.REQUEST_OTHER.getCode())) {
                actionBar.setTitle(R.string.txt_relevant_information);
                mViewHolder.txtFirstField.setText(R.string.txt_title);
                mViewHolder.editFirstField.setHint(R.string.txt_hint_relevant_information);
                mViewHolder.editDescription.setHint(R.string.txt_hint_other_description);
                mViewHolder.txtSecondField.setText(R.string.txt_distance_to_send_the_post);
                mViewHolder.btnRequest.setText(R.string.txt_pub);
            }
        }
        else finish();
    }

    private static class ViewHolder {
        TextView txtFirstField, txtSecondField, txtSBMin, txtSBMax;
        EditText editFirstField, editDescription;
        SeekBar seekBarDistance;
        Button btnRequest;
        ImageView imgUploadImage;
    }
}