package ba.fit.bookdiary.fragments;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.Days;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ba.fit.bookdiary.MainActivity;
import ba.fit.bookdiary.R;
import ba.fit.bookdiary.ViewModels.AddBookViewModel;
import ba.fit.bookdiary.data.Storage;
import ba.fit.bookdiary.helpers.MyApiRequest;
import ba.fit.bookdiary.helpers.MyRunnable;

public class AddBookFragment extends DialogFragment {
    private EditText bookTitle;
    private EditText bookAuthor;
    private EditText pages;
    private EditText date;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private EditText genre;

    public static AddBookFragment newInstance() {
        AddBookFragment fragment = new AddBookFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_book, container, false);

        bookTitle = (EditText) view.findViewById(R.id.bookTitleEditText);
        bookAuthor = (EditText) view.findViewById(R.id.bookAuthorEditText);
        genre = (EditText) view.findViewById(R.id.genreEditText);
        pages = (EditText) view.findViewById(R.id.bookPagesEditText);
        date = (EditText) view.findViewById(R.id.dateEditText);
        date.setText(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));

        Button btnAdd = (Button) view.findViewById(R.id.newBookBtn);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_newBookBtnClick();
            }
        });

        view.findViewById(R.id.closeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        TextView randomQuote = (TextView) view.findViewById(R.id.randomQuoteTextView);
        randomQuote.setText(Storage.getRandomQuote());

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String selectedDate = (month + 1) + "/" + day + "/" + year;
                date.setText(selectedDate);
            }
        };

        return view;
    }

    private void do_newBookBtnClick() {
        try {
            String inputString = date.getText().toString();
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date parsedDate = formatter.parse(inputString);

            AddBookViewModel viewModel = new AddBookViewModel(bookTitle.getText().toString(),
                    bookAuthor.getText().toString(), genre.getText().toString(),
                    Integer.parseInt(pages.getText().toString()), parsedDate);

            MyApiRequest.post(getActivity(), "Books/AddBook", viewModel, new MyRunnable<Object>() {
                @Override
                public void run(Object o) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
            });

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
