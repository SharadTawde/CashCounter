package com.unicly.cashcounter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int totalAmount = 0;
    private int totalNote = 0;
    private TextView dateTextView;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private List<Integer> noteList = new ArrayList<>();
    private NoteAdapter noteAdapter;
    private TextView totalNoteText;
    private TextView totalAmountText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }

    private void initListener() {

    }

    private void initData() {
        dateTextView.setText(new SimpleDateFormat("d MMM").format(new Date()));
        prepareNoteList();
        noteAdapter.notifyDataSetChanged();
    }

    private void prepareNoteList() {
        noteList.add(2000);
        noteList.add(500);
        noteList.add(200);
        noteList.add(100);
        noteList.add(50);
        noteList.add(20);
        noteList.add(10);
        noteList.add(5);
        noteList.add(2);
        noteList.add(1);
    }

    private void initView() {
        dateTextView = findViewById(R.id.Date);
        recyclerView = findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);
        totalNoteText = findViewById(R.id.totalNoteCount);
        totalAmountText = findViewById(R.id.totalAmount);
    }

    private void updateTotal(int note_, int total_) {

    }

    private class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.note_layout, parent, false);
            return new NoteAdapterLayout(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((NoteAdapterLayout) holder).note.setText(noteList.get(position).toString());
        }

        @Override
        public int getItemCount() {
            return noteList.size();
        }

        private class NoteAdapterLayout extends RecyclerView.ViewHolder {
            TextView note;
            TextView noteAmount;
            EditText noteValue;

            public NoteAdapterLayout(View view) {
                super(view);
                note = view.findViewById(R.id.note);
                noteAmount = view.findViewById(R.id.noteAmount);
                noteValue = view.findViewById(R.id.noteValue);
                noteValue.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.toString().isEmpty()) {
                            noteAmount.setText(String.format("%d/-", 0));
                            return;
                        }
                        int note_ = Integer.parseInt(noteValue.getText().toString());
                        int total = note_ * noteList.get(getAdapterPosition());
                        noteAmount.setText(String.format("%d/-", total));
                        updateTotal(note_, total);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }

        }
    }
}