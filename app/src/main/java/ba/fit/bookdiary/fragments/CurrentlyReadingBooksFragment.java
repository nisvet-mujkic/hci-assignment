package ba.fit.bookdiary.fragments;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import ba.fit.bookdiary.R;
import ba.fit.bookdiary.ViewModels.CurrentlyReadingBooksViewModel;
import ba.fit.bookdiary.data.BookViewModel;
import ba.fit.bookdiary.data.BooksTestVM;
import ba.fit.bookdiary.data.Storage;
import ba.fit.bookdiary.helpers.ContextProvider;
import ba.fit.bookdiary.helpers.FragmentUtils;
import ba.fit.bookdiary.helpers.MyApiRequest;
import ba.fit.bookdiary.helpers.MyRunnable;
import ba.fit.bookdiary.helpers.SwipeToDeleteCallback;

public class CurrentlyReadingBooksFragment extends Fragment {

    private FloatingActionButton floatingActionButton;
    private BaseAdapter adapter;
    private MyAdapter myAdapter;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    private RecyclerView recyclerView;

    public static CurrentlyReadingBooksFragment newInstance() {
        CurrentlyReadingBooksFragment fragment = new CurrentlyReadingBooksFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currently_reading_books, container, false);

        populateData();

        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                do_fabClick();
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }

    private void populateData(){
        MyApiRequest.get(getActivity(), "Books/CurrentlyReading", new MyRunnable<CurrentlyReadingBooksViewModel>() {
            @Override
            public void run(CurrentlyReadingBooksViewModel result){
                myAdapter = new MyAdapter(new ContextProvider() {
                    @Override
                    public Context getContext() {
                        return getActivity();
                    }
                }, result);
                recyclerView.setAdapter(myAdapter);

                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(myAdapter, getActivity(), recyclerView));
                itemTouchHelper.attachToRecyclerView(recyclerView);
            }
        });
    }

    private void do_fabClick() {
        AddBookFragment addBookFragment = AddBookFragment.newInstance();
        FragmentUtils.openFragmentAsDialog(getActivity(), addBookFragment, "addBookDialog" );
    }
}
