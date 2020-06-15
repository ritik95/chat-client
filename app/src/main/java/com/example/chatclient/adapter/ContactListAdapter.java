package com.example.chatclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import androidx.annotation.NonNull;

import com.example.chatclient.R;
import com.example.chatclient.holders.ContactHolder;
import com.example.chatclient.model.ContactsInfo;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends ArrayAdapter {
    private List<ContactsInfo> contactListOriginal;
    private List<ContactsInfo> contactListDisplayed;
    private Context context;

    public ContactListAdapter(@NonNull Context context, int resource, @NonNull List<ContactsInfo> objects){
        super(context, resource, objects);
        this.contactListOriginal = objects;
        this.contactListDisplayed = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        ContactHolder holder = null;
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.contact_info, null);
            holder = new ContactHolder();
            holder.displayName = convertView.findViewById(R.id.displayName);
            holder.phoneNumber = convertView.findViewById(R.id.phoneNumber);
            convertView.setTag(holder);
        }else {
            holder = (ContactHolder) convertView.getTag();
        }
        ContactsInfo contactsInfo = contactListOriginal.get(position);
        holder.displayName.setText(contactsInfo.getDisplayName());
        holder.phoneNumber.setText(contactsInfo.getPhoneNumber());
        return convertView;
    }

    @Override
    public Filter getFilter(){
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<ContactsInfo> filterArrList = new ArrayList<>();
                if(contactListOriginal == null){
                    contactListOriginal = new ArrayList<>(contactListDisplayed);
                }

                if(constraint == null || constraint.length() == 0){
                    filterResults.count = contactListOriginal.size();
                    filterResults.values = contactListOriginal;
                }else {
                    constraint  = constraint.toString().toLowerCase();
                    for (ContactsInfo contact : contactListOriginal){
                        String name = contact.getDisplayName();
                        if(name.toLowerCase().startsWith(constraint.toString())){
                            filterArrList.add(new ContactsInfo(contact.getContactId(), contact.getDisplayName(), contact.getPhoneNumber()));
                        }
                    }
                    filterResults.count = filterArrList.size();
                    filterResults.values = filterArrList;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                contactListDisplayed = (ArrayList<ContactsInfo>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    @Override
    public int getCount(){
        return contactListDisplayed.size();
    }

    @Override
    public Object getItem(int position){
        return position;
    }

    @Override
    public long getItemId(int position){
        return position;
    }
}
