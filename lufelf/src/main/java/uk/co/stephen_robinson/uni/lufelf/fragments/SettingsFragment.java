package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.activities.MainActivity;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Single;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;
import uk.co.stephen_robinson.uni.lufelf.utilities.DownloadImage;

/**
 * @author James
 * Fragment for the settings
 */
public class SettingsFragment extends BaseFragment{

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        setContext(rootView.getContext());
        //showActivitySpinner();
        Switch showLocation=(Switch)rootView.findViewById(R.id.settings_location);

        boolean currentPrivacy=api.v1.currentPrivacy().equals("1")?true:false;
        showLocation.setChecked(currentPrivacy);

        showLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                showActivitySpinner();
                int privacy=b?1:0;
                Single single=new Single() {
                    @Override
                    public void results(Hashtable result) {
                        boolean error=toastMaker.isError(result.get(Message.CODE).toString(),result.get(Message.MESSAGE).toString());
                        hideActivitySpinner();
                    }
                };

                api.v1.updatePrivacy(privacy,single);
            }
        });

        TextView deleteAccount = (TextView) rootView.findViewById(R.id.setting_delete_user);
        deleteAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showActivitySpinner();
                Single sc = new Single() {
                    @Override
                    public void results(Hashtable result) {
                        boolean error=toastMaker.isError(result.get(Message.CODE).toString(),result.get(Message.MESSAGE).toString());
                        if(!error){
                            MainActivity b = (MainActivity)getActivity();
                            b.logout(null);
                        }
                        hideActivitySpinner();
                    }
                };
                api.v1.deleteUser(sc);
            }
        });

        ImageView profilePic = (ImageView)rootView.findViewById(R.id.profile_image);

        DownloadImage downloadImage = new DownloadImage(profilePic,getActivity(),DownloadImage.AVATAR,215);
        downloadImage.downloadFromServer();

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userUpload=true;
                showCameraDialog();
            }
        });

        return rootView;
    }
}