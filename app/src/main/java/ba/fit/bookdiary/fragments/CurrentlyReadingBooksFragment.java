package ba.fit.bookdiary.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ba.fit.bookdiary.R;
import ba.fit.bookdiary.data.BookViewModel;
import ba.fit.bookdiary.data.Storage;

public class CurrentlyReadingBooksFragment extends Fragment {

    private ListView currentlyReadingListView;
    private FloatingActionButton fab;
    private BaseAdapter adapter;

    public static CurrentlyReadingBooksFragment newInstance() {
        CurrentlyReadingBooksFragment fragment = new CurrentlyReadingBooksFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currently_reading_books, container, false);

        currentlyReadingListView = (ListView)view.findViewById(R.id.currentlyReadingListView);

        populateCurrentlyReadingListView();

        fab = (FloatingActionButton)view.findViewById(R.id.fab);

        return view;
    }

    private void populateCurrentlyReadingListView() {
        final List<BookViewModel> data = Storage.getBooks();

        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public Object getItem(int i) {
                return data.get(i);
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if(view == null){
                    LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater.inflate(R.layout.currently_reading_book_list_item, viewGroup, false);
                }

                BookViewModel book = data.get(i);

                TextView bookTitleTextView = (TextView)view.findViewById(R.id.bookTitleTextView);


                bookTitleTextView.setText(book.getTitle());

                return view;
            }
        };

        currentlyReadingListView.setAdapter(adapter);
    }

}
