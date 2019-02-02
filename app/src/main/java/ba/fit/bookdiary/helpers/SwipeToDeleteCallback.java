package ba.fit.bookdiary.helpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import ba.fit.bookdiary.R;
import ba.fit.bookdiary.ViewModels.CurrentlyReadingBooksViewModel;
import ba.fit.bookdiary.data.BookViewModel;
import ba.fit.bookdiary.data.Storage;
import ba.fit.bookdiary.fragments.MyAdapter;

public  class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private MyAdapter mAdapter;
    private Context context;
    private RecyclerView recyclerView;

    private Drawable icon;
    private final ColorDrawable background;
    private CurrentlyReadingBooksViewModel.Row currentBook;

    public SwipeToDeleteCallback(MyAdapter adapter, Context context, RecyclerView recyclerView) {
        super(0,ItemTouchHelper.LEFT);
        this.context = context;
        this.recyclerView = recyclerView;
        mAdapter = adapter;
        icon =  context.getResources().getDrawable(R.drawable.ic_checked);
        background = new ColorDrawable(context.getResources().getColor(R.color.primary));
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        String bookTitle = mAdapter.getBookTitle(position);
        int id = mAdapter.removeAt(position);
        moveBookToArchive(id);
        Snackbar.make(recyclerView, bookTitle + " has been moved to finished with reading list", Snackbar.LENGTH_LONG).show();
    }

    private void moveBookToArchive(int bookId){
        MyApiRequest.patch((Activity)context, "Books/MarkAsFinished/" + bookId, new MyRunnable<Object>() {
            @Override
            public void run(Object o){
                mAdapter.getBooks();
            }
        });
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20; //so background is behind the rounded corners of itemView

        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if (dX > 0) { // Swiping to the right
            int iconLeft = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
            int iconRight = itemView.getLeft() + iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getLeft(), itemView.getTop(),
                    itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom());
        } else if (dX < 0) { // Swiping to the left
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0);
        }

        background.draw(c);
        icon.draw(c);
    }
}