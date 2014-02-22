package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.adapters.PlaceItem;
import uk.co.stephen_robinson.uni.lufelf.utilities.DownloadImage;

/**
 * @author James
 * Place profile
 */
public class PlaceSubFragment extends BaseFragment{

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceSubFragment newInstance(PlaceItem p) {
        PlaceSubFragment f=new PlaceSubFragment();
        Bundle args =new Bundle();

        args.putInt("id",p.getId());
        args.putString("name",p.getName());
        args.putString("description",p.getDescription());
        args.putString("address",p.getAddress());
        args.putString("imageurl",p.getImageUrl());
        args.putInt("creatorid",p.getCreatedByID());
        args.putString("type",p.getType());

        f.setArguments(args);
        return f;
    }

    public PlaceSubFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_place_profile, container, false);
        setContext(rootView.getContext());
        //showActivitySpinner();

        //get the args
        Bundle args = getArguments();

        //get profile image
        ImageView profile = (ImageView)rootView.findViewById(R.id.image_location);

        //get the textfields
        TextView name = (TextView)rootView.findViewById(R.id.location_name);
        TextView address = (TextView)rootView.findViewById(R.id.location_address);
        TextView description = (TextView)rootView.findViewById(R.id.location_description_box);
        TextView type = (TextView)rootView.findViewById(R.id.location_type);

        name.setText(args.getString("name"));
        address.setText(args.getString("address"));
        description.setText(args.getString("description"));
        type.setText(args.getString("type"));

        if(args.getString("imageurl")!=null){
            DownloadImage downloadImage=new DownloadImage(profile,getActivity(), args.getString("imageurl"));
            downloadImage.downloadFromServer();
        }

        return rootView;
    }
}
