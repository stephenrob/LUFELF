package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import uk.co.stephen_robinson.uni.lufelf.adapters.UserItem;
import uk.co.stephen_robinson.uni.lufelf.utilities.DownloadImage;
import uk.co.stephen_robinson.uni.lufelf.R;

/**
 * @author James
 * Creates a fragment of a friends profile.
 */
public class FriendsSubFragment extends BaseFragment{

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FriendsSubFragment newInstance(UserItem user) {
        Bundle args = new Bundle();
        args.putString("name",user.getName());
        args.putString("username",user.getEmailAdd());
        args.putString("lib_no",user.getLibraryNo());
        args.putInt("user_id",user.getId());
        args.putString("description",user.getDescription());
        return new FriendsSubFragment();
    }

    public FriendsSubFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_friend_profile, container, false);
        setContext(rootView.getContext());
        //showActivitySpinner();


        ImageView imageView=(ImageView)rootView.findViewById(R.id.image_friend);
        DownloadImage downloadImage = new DownloadImage(imageView,getActivity(),"http://148.88.32.47/avatars/215.jpg");

        downloadImage.downloadFromServer();

        return rootView;
    }
}