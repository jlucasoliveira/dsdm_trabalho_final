package br.ufc.quixada.dsdm.meempresta;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.ufc.quixada.dsdm.meempresta.models.enums.RequestType;

public class RequestActivity extends AppCompatActivity implements View.OnClickListener {

    private final ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_request);

        mViewHolder.btnRequest      = findViewById(R.id.btn_request);
        mViewHolder.txtFirstField   = findViewById(R.id.txt_first_field);
        mViewHolder.txtSecondField  = findViewById(R.id.txt_second_field);
        mViewHolder.editFirstField  = findViewById(R.id.edit_first_field);
        mViewHolder.editDescription = findViewById(R.id.edit_description);
        mViewHolder.seekBarDistance = findViewById(R.id.seekBar_distance);
        mViewHolder.txtSBMin        = findViewById(R.id.txt_min_radius);
        mViewHolder.txtSBMax        = findViewById(R.id.txt_max_radius);

        changeUI();

    }

    public void onClick( View v ) {}

    private void changeUI() {
        Bundle bundle = getIntent().getExtras();
        ActionBar actionBar = getSupportActionBar();

        if (bundle != null) {
            Integer actionId = bundle.getInt(RequestType.REQUEST_TYPE.name());
            if (actionId.equals(RequestType.REQUEST_BORROW_TOOL.ordinal())) {
                actionBar.setTitle(R.string.txt_title_what_you_need);
                mViewHolder.txtFirstField.setText(R.string.txt_object);
                mViewHolder.editFirstField.setHint(R.string.txt_object_hint_drilling_machine);
                mViewHolder.editDescription.setHint(R.string.txt_object_hint_description);
                mViewHolder.txtSecondField.setText(R.string.txt_object_fetch_radius);
                mViewHolder.btnRequest.setText(R.string.btn_object_asking);
            }
            else if (actionId.equals(RequestType.REQUEST_HELP.ordinal())) {
                actionBar.setTitle(R.string.txt_title_what_you_need);
                mViewHolder.txtFirstField.setText(R.string.txt_i_need);
                mViewHolder.editFirstField.setHint(R.string.txt_hint_changing_tire);
                mViewHolder.editDescription.setHint(R.string.txt_hint_description_help);
                mViewHolder.txtSecondField.setText(R.string.txt_help_request_radius);
                mViewHolder.btnRequest.setText(R.string.btn_asking_help);
            }
            else if (actionId.equals(RequestType.REQUEST_WORKOUT.ordinal())) {
                actionBar.setTitle(R.string.txt_activity);
                mViewHolder.txtFirstField.setText(R.string.txt_i_need);
                mViewHolder.editFirstField.setHint(R.string.txt_hint_want_create_running_group);
                mViewHolder.editDescription.setHint(R.string.txt_hint_workout_description);
                mViewHolder.txtSecondField.setText(R.string.txt_how_far_can_you_go);
                mViewHolder.btnRequest.setText(R.string.txt_invite_fried);
            }
            else if (actionId.equals(RequestType.REQUEST_ASK_RIDE.ordinal())) {
                actionBar.setTitle(R.string.txt_title_ask_ride);
                mViewHolder.txtFirstField.setText(R.string.txt_ask_ride_destiny);
                mViewHolder.editFirstField.setHint(R.string.txt_hint_ask_ride);
                mViewHolder.editDescription.setHint(R.string.txt_hint_ask_ride_description);
                mViewHolder.txtSecondField.setText(R.string.txt_how_far_can_you_go_find);
                mViewHolder.btnRequest.setText(R.string.txt_ask_ride);
            }
            else if (actionId.equals(RequestType.REQUEST_SHARE_FOOD.ordinal())) {
                actionBar.setTitle(R.string.txt_title_share_food);
                mViewHolder.txtFirstField.setText(R.string.txt_food);
                mViewHolder.editFirstField.setHint(R.string.txt_hint_share_food);
                mViewHolder.editDescription.setHint(R.string.txt_hint_share_food_description);
                mViewHolder.txtSecondField.setText(R.string.txt_what_distance);
                mViewHolder.btnRequest.setText(R.string.txt_share_food);
            }
            else if (actionId.equals(RequestType.REQUEST_INVITE_FRIENDS.ordinal())) {
                actionBar.setTitle(R.string.txt_what_you_wanna_do);
                mViewHolder.txtFirstField.setText(R.string.txt_invite_for);
                mViewHolder.editFirstField.setHint(R.string.txt_hint_invite_for);
                mViewHolder.editDescription.setHint(R.string.txt_hint_invite_friends_description);
                mViewHolder.txtSecondField.setText(R.string.txt_how_far_can_you_go_find);
                mViewHolder.btnRequest.setText(R.string.txt_invite_fried);
            }
            else if (actionId.equals(RequestType.REQUEST_DONATE_GIFT.ordinal())) {
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
            else if (actionId.equals(RequestType.REQUEST_OTHER.ordinal())) {
                actionBar.setTitle(R.string.txt_relevant_information);
                mViewHolder.txtFirstField.setText(R.string.txt_title);
                mViewHolder.editFirstField.setHint(R.string.txt_hint_relevant_information);
                mViewHolder.editDescription.setHint(R.string.txt_hint_other_description);
                mViewHolder.txtSecondField.setText(R.string.txt_distance_to_send_the_post);
                mViewHolder.btnRequest.setText(R.string.txt_pub);
            }
        }
    }

    private static class ViewHolder {
        TextView txtFirstField;
        TextView txtSecondField;
        EditText editFirstField;
        EditText editDescription;
        TextView txtSBMin, txtSBMax;
        SeekBar seekBarDistance;
        Button btnRequest;
    }

}