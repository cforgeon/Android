package com.example.user.localizone;

/**
 * Created by amandine on 12/25/15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;


public class NotificationAdapter extends ArrayAdapter<Notification> {

        //Notifications est la liste des models à afficher
        public NotificationAdapter(Context context, List<Notification> Notifications) {
            super(context, 0, Notifications);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.notification,parent, false);
            }

            NotificationViewHolder viewHolder = (NotificationViewHolder) convertView.getTag();
            if(viewHolder == null){
                viewHolder = new NotificationViewHolder();
                viewHolder.latitude = (TextView) convertView.findViewById(R.id.notifLatitude);
                viewHolder.longitude = (TextView) convertView.findViewById(R.id.notifLongitude);
                viewHolder.date = (TextView) convertView.findViewById(R.id.notifDate);
                convertView.setTag(viewHolder);
            }

            //getItem(position) va récupérer l'item [position] de la List<Notification> Notifications
            Notification Notification = getItem(position);

            //il ne reste plus qu'à remplir notre vue
            viewHolder.latitude.setText(Notification.getLatitude());
            viewHolder.longitude.setText(Notification.getLongitude());
            viewHolder.date.setText(Notification.getDate());

            return convertView;
        }

        private class NotificationViewHolder {
            public TextView latitude;
            public TextView longitude;
            public TextView date;
        }
}
