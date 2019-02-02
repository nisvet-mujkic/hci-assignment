package ba.fit.bookdiary.fragments;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ba.fit.bookdiary.R;
import ba.fit.bookdiary.ViewModels.CurrentlyReadingBooksViewModel;
import ba.fit.bookdiary.data.BookViewModel;
import ba.fit.bookdiary.data.BooksTestVM;
import ba.fit.bookdiary.data.KorisnikPregledVM;
import ba.fit.bookdiary.data.Storage;
import ba.fit.bookdiary.helpers.ContextProvider;
import ba.fit.bookdiary.helpers.FragmentUtils;
import ba.fit.bookdiary.helpers.MyApiRequest;
import ba.fit.bookdiary.helpers.MyConfig;
import ba.fit.bookdiary.helpers.MyGson;
import ba.fit.bookdiary.helpers.MyRunnable;
import ba.fit.bookdiary.helpers.MyUrlConnection;
import ba.fit.bookdiary.helpers.SwipeToDeleteCallback;

public class CurrentlyReadingBooksFragment extends Fragment {

    private ListView currentlyReadingListView;
    private FloatingActionButton fab;
    private BaseAdapter adapter;
    private MyAdapter myAdapter;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

    private RecyclerView rv;


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

        //populateCurrentlyReadingListView();

        popuni2();

        fab = (FloatingActionButton)view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                do_fabClick();
            }
        });

        rv = (RecyclerView) view.findViewById(R.id.list);
        rv.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                linearLayoutManager.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);

        return view;
    }


    private void popuni2(){
        MyApiRequest.get(getActivity(), "Books/CurrentlyReading", new MyRunnable<CurrentlyReadingBooksViewModel>() {
            @Override
            public void run(CurrentlyReadingBooksViewModel x){
                myAdapter = new MyAdapter(new ContextProvider() {
                    @Override
                    public Context getContext() {
                        return getActivity();
                    }
                }, x);
                rv.setAdapter(myAdapter);

                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(myAdapter, getActivity(), rv));
                itemTouchHelper.attachToRecyclerView(rv);
            }
        });
    }

    private void do_fabClick() {
        AddBookFragment addBookFragment = AddBookFragment.newInstance();
        FragmentUtils.openFragmentAsDialog(getActivity(), addBookFragment, "addBookDialog" );
    }

    private void populateCurrentlyReadingListView() {
        final BooksTestVM data = null;

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
                    view = inflater.inflate(R.layout.currently_reading_book_list_item, viewGroup, false);
                }

                BooksTestVM.Row book = data.rows.get(i);

                TextView bookTitleTextView = (TextView)view.findViewById(R.id.bookTitleTextView);
                TextView bookAuthorTextView = (TextView)view.findViewById(R.id.bookAuthorTextView);
                TextView finished = (TextView)view.findViewById(R.id.finishedReadingTextView);


                finished.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        do_finishedClick();
                    }
                });

                bookTitleTextView.setText(book.title);
                bookAuthorTextView.setText(book.author);

                return view;
            }
        };

        currentlyReadingListView.setAdapter(adapter);

        currentlyReadingListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                BooksTestVM.Row bookViewModel = data.rows.get(i);
                //do_currentlyReadingListViewLongClick(bookViewModel);
                return false;
            }
        });
    }

    private void do_finishedClick() {
        //BookReviewFragment bookReviewFragment = BookReviewFragment.newInstance();
        //FragmentUtils.openFragmentAsDialog(getActivity(), bookReviewFragment, "bookReview");
    }

    private void do_currentlyReadingListViewLongClick(final BookViewModel bookViewModel) {
        final AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        adb.setTitle("Warning");
        adb.setMessage("Do you want to delete book titled: " + bookViewModel.getTitle());

        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Storage.removeBook(bookViewModel);
                dialog.dismiss();
                adapter.notifyDataSetChanged();
            }
        });

        adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        adb.setIcon(android.R.drawable.ic_dialog_alert);
        adb.show();
    }

}
