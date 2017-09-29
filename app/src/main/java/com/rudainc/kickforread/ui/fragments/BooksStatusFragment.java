package com.rudainc.kickforread.ui.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rudainc.kickforread.R;
import com.rudainc.kickforread.adapters.BooksAdapter;
import com.rudainc.kickforread.database.BooksContract;
import com.rudainc.kickforread.models.BooksModel;
import com.rudainc.kickforread.ui.activities.BaseActivity;
import com.rudainc.kickforread.ui.activities.MainActivity;
import com.rudainc.kickforread.utils.KickForReadKeys;
import com.rudainc.kickforread.utils.KickForReadPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BooksStatusFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>, BooksAdapter.BooksAdapterOnClickHandler,KickForReadKeys {

    private int mPosition = RecyclerView.NO_POSITION;

    @BindView(R.id.rvBooks)
    RecyclerView rvBooks;

    @BindView(R.id.tv_no_data)
    TextView noData;

    @BindView(R.id.root)
    NestedScrollView root;


    private BooksAdapter mBooksAdapter;

    private LinearLayoutManager ll;

    private int lastFirstVisiblePosition;
    private Cursor mCursor;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_books_status, container, false);
        ButterKnife.bind(this, v);
        getActivity().getSupportLoaderManager().initLoader(ID_LOADER_BOOKS, null, this);
        ( (MainActivity)getActivity()).setToolbarText(getString(R.string.title_book_status));


        if (savedInstanceState != null) {
            final int pos = savedInstanceState.getInt(SCROLL_POSITION);
            Log.i(SCROLL_POSITION, pos +"");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    root.smoothScrollTo(0, pos);
                }
            }, 200);
        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getContext().getContentResolver().notifyChange(BooksContract.BookEntry.CONTENT_URI,null);
    }

    private void setUI(){
        rvBooks.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBooksAdapter = new BooksAdapter(getActivity(), this);
        rvBooks.setAdapter(mBooksAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {


        switch (loaderId) {

            case ID_LOADER_BOOKS:
                /* URI for all rows of weather data in our weather table */
                Uri booksQuery = BooksContract.BookEntry.CONTENT_URI;
                /* Sort order: Ascending by date */
                String sortOrder = "_id" + " DESC";
                /*
                 * A SELECTION in SQL declares which rows you'd like to return. In our case, we
                 * want all weather data from today onwards that is stored in our weather table.
                 * We created a handy method to do that in our WeatherEntry class.
                 */


                return new CursorLoader(getActivity(),
                        booksQuery,
                        null,
                        null,
                        null,
                        sortOrder);

            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursor = data;
        if (!BaseActivity.getAllBooks(data).isEmpty()) {
            setUI();
            mBooksAdapter.setBooksData(BaseActivity.getAllBooks(data));
        }
        else {
            ((BaseActivity)getActivity()).showSnackBar(getString(R.string.list_empty),true);
            rvBooks.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }
        if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
        rvBooks.smoothScrollToPosition(mPosition);

    }

    /**
     * Called when a previously created loader is being reset, and thus making its data unavailable.
     * The application should at this point remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        /*
         * Since this Loader's data is now invalid, we need to clear the Adapter that is
         * displaying the data.
         */
        mCursor = null;
    }

    @Override
    public void onClick(BooksModel booksModel, int view) {
        if (view == CLICK_BOOK_FINISHED) {
            BaseActivity.updateBook(booksModel);
            getContext().getContentResolver().notifyChange(BooksContract.BookEntry.CONTENT_URI,null);
            KickForReadPreferences.setBookAdded(getActivity(), false);
            ((BaseActivity)getActivity()).showSnackBar(getString(R.string.did_it),true);
        }

        //For future details and edit book
//        else {
//            Intent intent = new Intent(getActivity(), BookDetailsActivity.class);
//            intent.putExtra(BOOK_DATA, booksModel);
//            startActivity(intent);
//        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat(SCROLL_POSITION, rvBooks.getY());
    }
}
