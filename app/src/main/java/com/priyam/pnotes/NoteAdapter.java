package com.priyam.pnotes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.priyam.pnotes.models.Note;

import java.util.List;

/** Class for making an adapter for showing notes list
 *  See Models - Note
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    private List<Note> noteList;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, time, descr;

        MyViewHolder(View view) {
            super(view); //view from super class mentioned in onCreateViewHolder
            title = (TextView) view.findViewById(R.id.note_title);
            time = (TextView) view.findViewById(R.id.note_timestamp);
            descr = (TextView) view.findViewById(R.id.note_description);
        }
    }

    public NoteAdapter(List<Note> noteList){
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_listview, parent, false);  // Use where you defined listview rows

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.title.setText(note.getTitle());
        holder.time.setText(note.getDate());
        holder.descr.setText(note.getDescription());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    // For updating the views

}
