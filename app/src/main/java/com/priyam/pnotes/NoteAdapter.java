package com.priyam.pnotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.priyam.pnotes.models.Note;

import java.util.List;

/** Class for making an adapter for showing notes list
 *  See Models - Note
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    private List<Note> noteList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view); //view from super class mentioned in onCreateViewHolder
            title = (TextView) view.findViewById(R.id.note_title);
        }
    }

    public NoteAdapter(List<Note> noteList){
        this.noteList = noteList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_listview, parent, false);  // Use where you defined listview rows

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.title.setText(note.getTitle());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

}
