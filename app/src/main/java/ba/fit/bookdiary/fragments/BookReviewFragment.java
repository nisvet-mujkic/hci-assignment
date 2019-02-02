package ba.fit.bookdiary.fragments;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import ba.fit.bookdiary.R;
import ba.fit.bookdiary.ViewModels.BookReviewPostViewModel;
import ba.fit.bookdiary.ViewModels.BookReviewViewModel;
import ba.fit.bookdiary.helpers.FragmentUtils;
import ba.fit.bookdiary.helpers.MyApiRequest;

public class BookReviewFragment extends DialogFragment {

    public static final String KEY = "book_review";
    private BookReviewViewModel review;
    private TextView title;
    private TextView author;
    private TextView genre;
    private EditText reviewEditText;
    private EditText quoteEditText;
    private RatingBar mark;
    private Button saveReviewBtn;

    public static BookReviewFragment newInstance(BookReviewViewModel bookReview) {
        BookReviewFragment fragment = new BookReviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY, bookReview);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            review = (BookReviewViewModel) getArguments().getSerializable(KEY);
        }

        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_book_review, container, false);

        Button share = (Button)view.findViewById(R.id.shareBookBtn);

        title = (TextView)view.findViewById(R.id.bookTitleTextView);
        author = (TextView)view.findViewById(R.id.bookAuthorTextView);
        genre = (TextView)view.findViewById(R.id.bookGenreTextView);

        reviewEditText = (EditText)view.findViewById(R.id.reviewEditText);
        quoteEditText = (EditText)view.findViewById(R.id.quoteEditText);
        mark = (RatingBar) view.findViewById(R.id.bookRatingBar);

        title.setText(review.title);
        author.setText(review.author);
        genre.setText(review.genre);

        reviewEditText.setText(review.review);
        quoteEditText.setText(review.quoteToRemember);
        mark.setRating(review.mark);


        view.findViewById(R.id.closeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        saveReviewBtn = (Button)view.findViewById(R.id.saveReviewBtn);

        saveReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_saveReviewBtnClick();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
            }
        });

        return view;
    }

    private void do_saveReviewBtnClick() {
        BookReviewPostViewModel postViewModel = new BookReviewPostViewModel();
        postViewModel.bookId = review.bookId;
        postViewModel.mark = (int)mark.getRating();
        postViewModel.quoteToRemember = quoteEditText.getText().toString();
        postViewModel.review = reviewEditText.getText().toString();

        MyApiRequest.post(getActivity(), "Books/BookReview", postViewModel, null);
        FinishedReadingFragment finishedReadingFragment = FinishedReadingFragment.newInstance();

        FragmentUtils.replaceFragment(getActivity(), R.id.placeholder, finishedReadingFragment);
    }

    private void share() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

}
