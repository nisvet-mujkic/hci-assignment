package ba.fit.bookdiary.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.Days;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ba.fit.bookdiary.R;
import ba.fit.bookdiary.ViewModels.CurrentlyReadingBooksViewModel;
import ba.fit.bookdiary.data.BookViewModel;
import ba.fit.bookdiary.data.BooksTestVM;
import ba.fit.bookdiary.data.KorisnikPregledVM;
import ba.fit.bookdiary.data.Storage;
import ba.fit.bookdiary.helpers.ContextProvider;
import ba.fit.bookdiary.helpers.MyApiRequest;
import ba.fit.bookdiary.helpers.MyApp;
import ba.fit.bookdiary.helpers.MyRunnable;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private CurrentlyReadingBooksViewModel model;
    private final ContextProvider mContextProvider;

    public MyAdapter(ContextProvider contextProvider, CurrentlyReadingBooksViewModel model) {
        this.model = model;
        this.mContextProvider = contextProvider;
    }

    @Override
    public int getItemCount() {
        return model.rows.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.currently_reading_book_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CurrentlyReadingBooksViewModel.Row book = model.rows.get(position);
        holder.display(book);
    }

    public void getBooks() {
        MyApiRequest.get((Activity) mContextProvider.getContext(), "Books/CurrentlyReading", new MyRunnable<CurrentlyReadingBooksViewModel>() {
            @Override
            public void run(CurrentlyReadingBooksViewModel data) {
                model = data;
                notifyDataSetChanged();
            }
        });
    }

    public int removeAt(int position) {
        int id = model.rows.get(position).bookId;

        model.rows.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, model.rows.size());

        return id;
    }

    public String getBookTitle(int position) {
        return model.rows.get(position).title;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView author;
        private final TextView dateAdded;
        private final TextView dayTextView;

        private CurrentlyReadingBooksViewModel.Row currentBook;

        public MyViewHolder(final View itemView) {
            super(itemView);

            name = ((TextView) itemView.findViewById(R.id.bookTitleTextView));
            author = ((TextView) itemView.findViewById(R.id.bookAuthorTextView));
            dateAdded = ((TextView) itemView.findViewById(R.id.addedOnTextView));
            dayTextView = ((TextView) itemView.findViewById(R.id.dayTextView));

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final AlertDialog.Builder adb = new AlertDialog.Builder(mContextProvider.getContext());
                    adb.setTitle("Warning");

                    final int position = getAdapterPosition();
                    final int bookId = model.rows.get(position).bookId;

                    model.rows.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, model.rows.size());

                    adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            MyApiRequest.delete((Activity) mContextProvider.getContext(), "Books/Delete/" + bookId, new MyRunnable<Object>() {
                                @Override
                                public void run(Object o) {
                                    getBooks();
                                }
                            });
                            dialog.dismiss();
                        }
                    });

                    adb.setMessage("Do you want to delete book titled: " + currentBook.title);

                    adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    adb.setIcon(android.R.drawable.ic_dialog_alert);
                    adb.show();
                    return false;
                }
            });
        }

        public void display(CurrentlyReadingBooksViewModel.Row book) {
            currentBook = book;
            name.setText(book.title);
            author.setText(book.author);

            Date date = null;
            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

            try {
                date = formatter.parse(book.startedReadingOn);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            dateAdded.setText(formatter.format(date));

            dayTextView.setText(getDifferenceDays(new Date(), date));
        }

        private String getDifferenceDays(Date today, Date addedOn) {
            long diff = today.getTime() - addedOn.getTime();
            return String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        }
    }
}
