package com.github.mobile.ui.repo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.github.mobile.R;
import com.github.mobile.core.ResourcePager;
import com.github.mobile.ui.PagedItemFragment;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.util.List;

import static com.github.mobile.Intents.EXTRA_USER;
import static com.github.mobile.RequestCodes.REPOSITORY_VIEW;
import static com.github.mobile.ResultCodes.RESOURCE_CHANGED;

/**
 * Created by sufeizhao on 6/13/15.
 *
 * Fragment to display the parent fork for a specific repository
 *
 */
public class ParentFragment extends PagedItemFragment<Repository> {

    @Inject
    private RepositoryService service;

    private User user;
    int id = 3;
    Repository[] parentRepo;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        user = getSerializableExtra(EXTRA_USER);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_parent);
    }

    @Override
    protected ResourcePager<Repository> createPager() {
        return new ResourcePager<Repository>() {

            @Override
            protected Object getId(Repository resource) {
                parentRepo[0] = resource.getParent();
                return resource.getId();
            }

            @Override
            public PageIterator<Repository> createIterator(int page, int size) {
                return service.pageRepositories(user.getLogin(), page, size);
            }
        };
    }

    @Override
    protected int getLoadingMessage() {
        return R.string.loading_repositories;
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_repos_load;
    }

    @Override
    protected SingleTypeAdapter<Repository> createAdapter(List<Repository> items) {
//        return new UserRepositoryListAdapter(getActivity().getLayoutInflater(),
//                items.toArray(new Repository[items.size()]), user);
        return new RepositoryListAdapter<Repository>(id, getActivity().getLayoutInflater(), parentRepo) {
            @Override
            protected int[] getChildViewIds() {
                return new int[1];
            }

            @Override
            protected void update(int i, Repository repository) {

            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REPOSITORY_VIEW && resultCode == RESOURCE_CHANGED) {
            forceRefresh();
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onListItemClick(ListView list, View v, int position, long id) {
        Repository repo = (Repository) list.getItemAtPosition(position);
        startActivityForResult(RepositoryViewActivity.createIntent(repo),
                REPOSITORY_VIEW);
    }
}