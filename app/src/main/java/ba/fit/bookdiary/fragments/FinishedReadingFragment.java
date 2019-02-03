package ba.fit.bookdiary.fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ba.fit.bookdiary.R;
import ba.fit.bookdiary.ViewModels.BookReviewViewModel;
import ba.fit.bookdiary.ViewModels.FinishedBooksViewModel;
import ba.fit.bookdiary.helpers.FragmentUtils;
import ba.fit.bookdiary.helpers.MyApiRequest;
import ba.fit.bookdiary.helpers.MyRunnable;

public class FinishedReadingFragment extends Fragment {
    private ListView finishedReadingListView;
    private BaseAdapter adapter;
    private SearchView searchView;

    public static FinishedReadingFragment newInstance() {
        FinishedReadingFragment fragment = new FinishedReadingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finished_reading, container, false);

        finishedReadingListView = (ListView)view.findViewById(R.id.finishedReadingListView);
        searchView = (SearchView)view.findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                do_searchViewTextChange(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                do_searchViewTextChange(query);
                return false;
            }
        });

        populateData("");

        return view;
    }

    private void populateData(String query){
        MyApiRequest.get(getActivity(), "Books/Find/" + query, new MyRunnable<FinishedBooksViewModel>() {
            @Override
            public void run(FinishedBooksViewModel viewModel){
                populateReadingList(viewModel);
            }
        });
    }

    private void do_searchViewTextChange(String query) {
        populateData(query);
    }

    private void populateReadingList(final FinishedBooksViewModel data) {
        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return data.rows.size();
            }

            @Override
            public Object getItem(int i) {
                return data.rows.get(i);
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if(view == null){
                    LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater.inflate(R.layout.finished_reading_book_list_item, viewGroup, false);
                }

                FinishedBooksViewModel.Row review = data.rows.get(i);

                TextView title = (TextView)view.findViewById(R.id.bookTitleTextView);
                TextView author = (TextView)view.findViewById(R.id.bookAuthorTextView);
                TextView dateFinished = (TextView)view.findViewById(R.id.dateFinishedTextView);
                RatingBar rating = (RatingBar)view.findViewById(R.id.bookRatingBar);

                title.setText(review.title);
                author.setText(review.author);

                Date date = null;

                DateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
                try {
                    date = formatter.parse(review.finishedReadingOn);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                dateFinished.setText(formatter.format(date));
                rating.setRating(review.mark);

                return view;
            }
        };

        finishedReadingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                do_finishedReadingListViewClick(data.rows.get(i).bookId);
            }
        });

        finishedReadingListView.setAdapter(adapter);
    }

    private void do_finishedReadingListViewClick(int id) {
        MyApiRequest.get(getActivity(), "Books/FindReview/" + id, new MyRunnable<BookReviewViewModel>() {
            @Override
            public void run(BookReviewViewModel review){
                BookReviewFragment bookReviewFragment = BookReviewFragment.newInstance(review);
                FragmentUtils.openFragmentAsDialog(getActivity(), bookReviewFragment, "reviewEdit");
            }
        });
    }
}
