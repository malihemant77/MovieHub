package com.etiennelawlor.moviehub.data.repositories.tv;

import com.etiennelawlor.moviehub.data.database.RealmUtility;
import com.etiennelawlor.moviehub.data.network.response.TelevisionShow;
import com.etiennelawlor.moviehub.data.network.response.TelevisionShowContentRatingsResponse;
import com.etiennelawlor.moviehub.data.network.response.TelevisionShowCreditsResponse;
import com.etiennelawlor.moviehub.data.network.response.TelevisionShowsResponse;
import com.etiennelawlor.moviehub.data.repositories.models.TelevisionShowsDataModel;

import io.reactivex.Maybe;

/**
 * Created by etiennelawlor on 2/13/17.
 */

public class TelevisionShowLocalDataSource implements TelevisionShowDataSourceContract.LocalDateSource {

    // region Constructors
    public TelevisionShowLocalDataSource() {
    }
    // endregion

    // region TelevisionShowDataSourceContract.LocalDateSource Methods

    @Override
    public Maybe<TelevisionShowsDataModel> getPopularTelevisionShows(int currentPage) {
        TelevisionShowsDataModel televisionShowsDataModel = RealmUtility.getTelevisionShowsDataModel(currentPage);
        if(televisionShowsDataModel == null)
            return Maybe.empty();
        else
            return Maybe.just(televisionShowsDataModel);
    }

    @Override
    public void savePopularTelevisionShows(TelevisionShowsDataModel televisionShowsDataModel) {
        RealmUtility.saveTelevisionShowsDataModel(televisionShowsDataModel);
    }

    @Override
    public Maybe<TelevisionShow> getTelevisionShow(int tvId) {
        //        Use mapper to convert from realm objects to POJOs
        return Maybe.empty();
    }

    @Override
    public void saveTelevisionShow(TelevisionShow televisionShow) {
//        Use mapper to convert from POJOs to realm objects
    }

    @Override
    public Maybe<TelevisionShowCreditsResponse> getTelevisionShowCredits(int tvId) {
        //        Use mapper to convert from realm objects to POJOs
        return Maybe.empty();
    }

    @Override
    public void saveTelevisionShowCredits(TelevisionShowCreditsResponse televisionShowCreditsResponse) {
//        Use mapper to convert from POJOs to realm objects
    }

    @Override
    public Maybe<TelevisionShowsDataModel> getSimilarTelevisionShows(int tvId) {
        //        Use mapper to convert from realm objects to POJOs
        return Maybe.empty();
    }

    @Override
    public void saveSimilarTelevisionShows(TelevisionShowsDataModel televisionShowsDataModel) {
//        Use mapper to convert from POJOs to realm objects
    }

    @Override
    public Maybe<TelevisionShowContentRatingsResponse> getTelevisionShowContentRatings(int tvId) {
        //        Use mapper to convert from realm objects to POJOs
        return Maybe.empty();
    }

    @Override
    public void saveTelevisionShowContentRatings(TelevisionShowContentRatingsResponse televisionShowContentRatingsResponse) {
//        Use mapper to convert from POJOs to realm objects
    }

    // endregion
}
