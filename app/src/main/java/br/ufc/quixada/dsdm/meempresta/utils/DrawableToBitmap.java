package br.ufc.quixada.dsdm.meempresta.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.VectorDrawable;
import androidx.appcompat.content.res.AppCompatResources;
import br.ufc.quixada.dsdm.meempresta.Models.enums.RequestType;
import br.ufc.quixada.dsdm.meempresta.R;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class DrawableToBitmap {

    public static BitmapDescriptor getIcon(Context mContext, int type) {
        int id;
        if (type == RequestType.REQUEST_BORROW_TOOL.getCode())
            id = R.drawable.ic_drill;
        else if (type == RequestType.REQUEST_HELP.getCode())
            id = R.drawable.ic_help;
        else if (type == RequestType.REQUEST_WORKOUT.getCode())
            id = R.drawable.ic_running;
        else if (type == RequestType.REQUEST_ASK_RIDE.getCode())
            id = R.drawable.ic_shared_car;
        else if (type == RequestType.REQUEST_SHARE_FOOD.getCode())
            id = R.drawable.ic_chicken;
        else if (type == RequestType.REQUEST_INVITE_FRIENDS.getCode())
            id = R.drawable.ic_party;
        else if (type == RequestType.REQUEST_DONATE_GIFT.getCode())
            id = R.drawable.ic_donate_gift;
        else
            id = R.drawable.ic_something;

        VectorDrawable vectorDrawable = (VectorDrawable) AppCompatResources.getDrawable(mContext, id);

        int h = vectorDrawable.getIntrinsicHeight();
        int w = vectorDrawable.getIntrinsicWidth();

        vectorDrawable.setBounds(0, 0, w/2, h/2);

        Bitmap bm = Bitmap.createBitmap(w/2, h/2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        vectorDrawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bm);
    }

}
