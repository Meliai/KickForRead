package com.rudainc.kickforread.ui.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.ads.InterstitialAd;
import com.rudainc.kickforread.R;
import com.rudainc.kickforread.adapters.BooksAdapter;
import com.rudainc.kickforread.database.BooksContract;
import com.rudainc.kickforread.models.BooksModel;
import com.rudainc.kickforread.ui.activities.BookDetailsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BooksStatusFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>, BooksAdapter.BooksAdapterOnClickHandler  {

    private int mPosition = RecyclerView.NO_POSITION;

    @BindView(R.id.rvBooks)
    RecyclerView rvBooks;

//    @BindView(R.id.tv_no_data)
//    TextView noData;

    private BooksAdapter mBooksAdapter;

    private LinearLayoutManager ll;
    private InterstitialAd mInterstitialAd;
    private int lastFirstVisiblePosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_books_status, container, false);
        ButterKnife.bind(this, v);
//        getSupportLoaderManager().initLoader(ID_LOADER, null, this);

        mBooksAdapter = new BooksAdapter(getActivity(), this);
        rvBooks.setAdapter(mBooksAdapter);

        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {


        switch (loaderId) {

            case ID_LOADER:
                /* URI for all rows of weather data in our weather table */
                Uri movieQueryUri = BooksContract.BookEntry.CONTENT_URI;
                /* Sort order: Ascending by date */
//                String sortOrder = BooksContract.BookEntry.C + " ASC";
                /*
                 * A SELECTION in SQL declares which rows you'd like to return. In our case, we
                 * want all weather data from today onwards that is stored in our weather table.
                 * We created a handy method to do that in our WeatherEntry class.
                 */


                return new CursorLoader(getActivity(),
                        movieQueryUri,
                        null,
                        null,
                        null,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mBooksAdapter.clearList();
//        if (!getAllFavoritesMovies(data).isEmpty())
//            mBooksAdapter.setMoviesData(getAllFavoritesMovies(data));
//        else {
//            rvBooks.setVisibility(View.GONE);
//            noData.setText(getResources().getString(R.string.no_favorite));
//            noData.setVisibility(View.VISIBLE);
////            noData.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
//        }
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
        mBooksAdapter.swapCursor(null);
    }

    @Override
    public void onClick(BooksModel booksModel, ImageView view) {
        Intent intent = new Intent(getActivity(), BookDetailsActivity.class);
//        intent.putExtra(EXTRA_DATA, movieItem);
        startActivity(intent);
    }
}
