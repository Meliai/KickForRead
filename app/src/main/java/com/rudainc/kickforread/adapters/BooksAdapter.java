package com.rudainc.kickforread.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rudainc.kickforread.R;
import com.rudainc.kickforread.models.BooksModel;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksAdapterViewHolder> {

    private final Context context;
    private ArrayList<BooksModel> mBooksData = new ArrayList<>();


    private final BooksAdapterOnClickHandler mClickHandler;
    private Cursor mCursor;

    public interface BooksAdapterOnClickHandler {
        void onClick(BooksModel booksModel, ImageView view);
    }


    public BooksAdapter(Context context, BooksAdapterOnClickHandler clickHandler) {
        this.context = context;
        mClickHandler = clickHandler;
    }


    public class BooksAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        public final ImageView mPoster;

        public BooksAdapterViewHolder(View view) {
            super(view);
//            mPoster = (ImageView) view.findViewById(R.id.iv_movie_poster);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            BooksModel booksModel = mBooksData.get(adapterPosition);
            mClickHandler.onClick(booksModel, (ImageView) v);
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
//        Picasso.with(context).load("http://image.tmdb.org/t/p/w500/" + movieItem.getPoster_path()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(moviesAdapterViewHolder.mPoster);

    }

    @Override
    public int getItemCount() {
        if (null == mBooksData) return 0;
        return mBooksData.size();
    }


    public void setBooksData(ArrayList<BooksModel> booksData) {
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
