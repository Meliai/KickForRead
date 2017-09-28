package com.rudainc.kickforread.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rudainc.kickforread.R;
import com.rudainc.kickforread.models.BooksModel;
import com.rudainc.kickforread.utils.HelpUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksAdapterViewHolder> {

    private final Context context;
    private ArrayList<BooksModel> mBooksData = new ArrayList<>();


    private final BooksAdapterOnClickHandler mClickHandler;
    private Cursor mCursor;

    public interface BooksAdapterOnClickHandler {
        void onClick(BooksModel booksModel, CardView view);
    }


    public BooksAdapter(Context context, BooksAdapterOnClickHandler clickHandler) {
        this.context = context;
        mClickHandler = clickHandler;
    }


    public class BooksAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mTitle;
        public final TextView mAuthor;
        public final TextView mCategory;
        public final TextView mStartedDay;
        public final TextView mIsFinished;

        public BooksAdapterViewHolder(View view) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.title);
            mAuthor = (TextView) view.findViewById(R.id.author);
            mCategory = (TextView) view.findViewById(R.id.category);
            mStartedDay = (TextView) view.findViewById(R.id.start_day);
            mIsFinished = (TextView) view.findViewById(R.id.is_finished);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            BooksModel booksModel = mBooksData.get(adapterPosition);
            mClickHandler.onClick(booksModel, (CardView) v);
        }
    }

    @Override
    public BooksAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_book;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new BooksAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BooksAdapterViewHolder booksAdapterViewHolder, int position) {
        BooksModel booksItem = mBooksData.get(position);
        Log.i("BOOK",booksItem.getTitle());

        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        booksAdapterViewHolder.mTitle.setText(booksItem.getTitle());
        booksAdapterViewHolder.mAuthor.setText(booksItem.getAuthor());
        booksAdapterViewHolder.mCategory.setText(booksItem.getCategory());
        booksAdapterViewHolder.mStartedDay.setText(dateFormat.format((HelpUtil.setCalendarDate(Long.valueOf(booksItem.getStart_date())*1000)).getTime()));
        booksAdapterViewHolder.mIsFinished.setText(booksItem.getIsFinished());
    }

    @Override
    public int getItemCount() {
        if (null == mBooksData) return 0;
        return mBooksData.size();
    }


    public void setBooksData(ArrayList<BooksModel> booksData) {
        Log.i("BOOKS", booksData.toString());
        this.mBooksData.clear();
        mBooksData = booksData;
        notifyDataSetChanged();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }


    public void updateBooksList(ArrayList<BooksModel> list) {
        this.mBooksData.addAll(list);
        notifyDataSetChanged();
    }

    public ArrayList<BooksModel> getBooksData() {
        return mBooksData;
    }

    public void clearList() {
        this.mBooksData.clear();
        notifyDataSetChanged();
    }
}
