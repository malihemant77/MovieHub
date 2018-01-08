package com.etiennelawlor.moviehub.presentation.movies;

import com.etiennelawlor.moviehub.domain.models.MovieDomainModel;
import com.etiennelawlor.moviehub.domain.models.MoviesDomainModel;
import com.etiennelawlor.moviehub.domain.usecases.MoviesDomainContract;
import com.etiennelawlor.moviehub.presentation.models.MoviePresentationModel;
import com.etiennelawlor.moviehub.util.NetworkUtility;
import com.etiennelawlor.moviehub.util.rxjava.SchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by etiennelawlor on 2/9/17.
 */

public class MoviesPresenter implements MoviesPresentationContract.Presenter {

    // region Member Variables
    private final MoviesPresentationContract.View moviesView;
    private final MoviesDomainContract.UseCase moviesUseCase;
    private final SchedulerProvider schedulerProvider;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    // endregion

    // region Constructors
    public MoviesPresenter(MoviesPresentationContract.View moviesView, MoviesDomainContract.UseCase moviesUseCase, SchedulerProvider schedulerProvider) {
        this.moviesView = moviesView;
        this.moviesUseCase = moviesUseCase;
        this.schedulerProvider = schedulerProvider;
    }
    // endregion

    // region MoviesPresentationContract.Presenter Methods
    @Override
    public void onDestroyView() {
        if (compositeDisposable != null)
            compositeDisposable.clear();
    }

    @Override
    public void onLoadPopularMovies(final int currentPage) {
        if(currentPage == 1){
            moviesView.hideEmptyView();
            moviesView.hideErrorView();
            moviesView.showLoadingView();
        } else{
            moviesView.showLoadingFooter();
        }

        Disposable disposable = moviesUseCase.getPopularMovies(currentPage)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableSingleObserver<MoviesDomainModel>() {
                    @Override
                    public void onSuccess(MoviesDomainModel moviesDomainModel) {
                        if(moviesDomainModel != null){
                            List<MovieDomainModel> movieDomainModels = moviesDomainModel.getMovies();
                            int currentPage = moviesDomainModel.getPageNumber();
                            boolean isLastPage = moviesDomainModel.isLastPage();
                            boolean hasMovies = moviesDomainModel.hasMovies();
                            if(currentPage == 1){
                                moviesView.hideLoadingView();

                                if(hasMovies){
                                    moviesView.addHeader();
                                    moviesView.addMoviesToAdapter(movieDomainModels);

                                    if(!isLastPage)
                                        moviesView.addFooter();
                                } else {
                                    moviesView.showEmptyView();
                                }
                            } else {
                                moviesView.removeFooter();

                                if(hasMovies){
                                    moviesView.addMoviesToAdapter(movieDomainModels);

                                    if(!isLastPage)
                                        moviesView.addFooter();
                                }
                            }

                            moviesView.setMoviesPresentationModel(moviesDomainModel);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();

                        if(currentPage == 1){
                            moviesView.hideLoadingView();

                            if (NetworkUtility.isKnownException(throwable)) {
                                moviesView.setErrorText("Can't load data.\nCheck your network connection.");
                                moviesView.showErrorView();
                            }
                        } else {
                            if(NetworkUtility.isKnownException(throwable)){
                                moviesView.showErrorFooter();
                            }
                        }
                    }
                });

        compositeDisposable.add(disposable);
    }

    @Override
    public void onMovieClick(MoviePresentationModel movie) {
        moviesView.openMovieDetails(movie);
    }

    @Override
    public void onScrollToEndOfList() {
        moviesView.loadMoreItems();
    }
    // endregion

}
