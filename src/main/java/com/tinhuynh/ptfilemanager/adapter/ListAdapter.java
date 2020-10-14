package com.tinhuynh.ptfilemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.tinhuynh.ptfilemanager.R;

import java.util.ArrayList;
import java.util.List;

//This Java file will in charge of listing files in directories

public class ListAdapter extends BaseAdapter {
    // List context
    private Context context;

    //List files
    private List<String> file_array = new ArrayList<>();

    public void setFile_array(List<String> file_array) {
        if (file_array.size() > 0) {
            this.file_array.addAll(file_array);
        }
    }

    @Override
    public int getCount() {
        return file_array.size();
    }

    @Override
    public String getItem(int i) {
        return file_array.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * To display file and directory to screen
     *
     */

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_list, parent, false);
        view.setTag(new ViewHolder((TextView) view.findViewById(R.id.file_list)));

        String file = getItem(position);
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.holder.setText(file.substring(file.lastIndexOf('/')+1));
        return view;
    }

    class ViewHolder {
        TextView holder;
        public ViewHolder (TextView holder) {
            this.holder = holder;
        }
    }
}
