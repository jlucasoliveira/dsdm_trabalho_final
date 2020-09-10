package br.ufc.quixada.dsdm.meempresta;

import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.ufc.quixada.dsdm.meempresta.models.enums.RequestType;

public class RequestActivity extends AppCompatActivity implements View.OnClickListener {

    private final ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        mViewHolder.txtFirstField = findViewById(R.id.txt_first_field);
        mViewHolder.txtSecondField = findViewById(R.id.txt_second_field);
        mViewHolder.editFirstField = findViewById(R.id.edit_first_field);
        mViewHolder.editDescription = findViewById(R.id.edit_description);
        mViewHolder.seekBarDistance = findViewById(R.id.seekBar_distance);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Integer actionId = bundle.getInt(RequestType.REQUEST_TYPE.name());
            if (actionId.equals(RequestType.REQUEST_BORROW_TOOL.ordinal())) {
                getSupportActionBar().setTitle(R.string.txt_title_what_you_need);
                mViewHolder.txtFirstField.setText(R.string.txt_object);
                mViewHolder.editFirstField.setHint(R.string.txt_object_hint_drilling_machine);
                mViewHolder.editDescription.setHint(R.string.txt_object_hint_description);
                mViewHolder.txtSecondField.setText(R.string.txt_object_fetch_radius);
            }
        }

    }

    public void onClick( View v ) {}

    private static class ViewHolder {
        TextView txtFirstField;
        TextView txtSecondField;
        EditText editFirstField;
        EditText editDescription;
        SeekBar seekBarDistance;
    }

}