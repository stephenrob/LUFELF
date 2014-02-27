package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.adapters.PlaceItem;
import uk.co.stephen_robinson.uni.lufelf.utilities.DownloadImage;

/**
 * @author James
 * Place profile
 */
public class PlaceProfileFragment extends BaseFragment{
    private LatLng finish;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceProfileFragment newInstance(PlaceItem p) {
        PlaceProfileFragment f=new PlaceProfileFragment();
        Bundle args =new Bundle();

        args.putInt("id",p.getId());
        args.putString("name", p.getName());
        args.putString("description",p.getDescription());
        args.putString("address",p.getAddress());
        args.putString("imageurl",p.getImageUrl());
        args.putInt("creatorid", p.getCreatedByID());
        args.putDouble("lat",p.getLocation().latitude);
        args.putDouble("long",p.getLocation().longitude);
        args.putInt("icon",p.getIcon());

        f.setArguments(args);
        return f;
    }

    public PlaceProfileFragment() {
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
        ImageView type = (ImageView)rootView.findViewById(R.id.location_type);
        TextView locationText = (TextView)rootView.findViewById(R.id.place_location);

        name.setText(args.getString("name"));
        address.setText(args.getString("address"));
        description.setText(args.getString("description"));
        type.setImageDrawable(getResources().getDrawable(args.getInt("icon")));

        finish=new LatLng(args.getDouble("lat"),args.getDouble("long"));
        Log.e("INSTANCEOF", finish.latitude + " " + finish.longitude);
        locationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapToNavigateTo(finish);
            }
        });

        if(args.getString("imageurl")!=null){
            DownloadImage downloadImage=new DownloadImage(profile,getActivity(),DownloadImage.PLACE,args.getInt("id"));
            downloadImage.downloadFromServer();
        }

        return rootView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getFragmentManager().beginTransaction().remove(this).commit();
    }
}
