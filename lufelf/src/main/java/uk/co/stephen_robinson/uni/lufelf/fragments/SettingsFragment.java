package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Hashtable;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.activities.MainActivity;
import uk.co.stephen_robinson.uni.lufelf.adapters.UserItem;
import uk.co.stephen_robinson.uni.lufelf.api.Network.callbacks.Single;
import uk.co.stephen_robinson.uni.lufelf.api.v1.xml.Message;
import uk.co.stephen_robinson.uni.lufelf.utilities.CustomMessages;
import uk.co.stephen_robinson.uni.lufelf.utilities.DialogCallback;
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
                DialogCallback dialogCallback = new DialogCallback() {
                    @Override
                    public void messageComplete(int result) {
                        Log.e("BUTTON","DIALOG CLALED");
                        switch(result){
                            case 1:
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
                                break;
                            case -1:
                                break;
                        }
                    }
                };
                CustomMessages.showMessage("Delete Account","Are you sure you want to delete your account?","Delete Account","Cancel",context,dialogCallback);

            }
        });

        ImageView profilePic = (ImageView)rootView.findViewById(R.id.profile_image);

        DownloadImage downloadImage = new DownloadImage(profilePic,getActivity(),DownloadImage.AVATAR,Integer.valueOf(api.v1.currentUserId()));
        downloadImage.downloadFromServer(isNetworkAvailable());

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userUpload=true;
                showCameraDialog();
            }
        });

        Button loginButton=(Button)rootView.findViewById(R.id.settings_logout_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogCallback dialogCallback=new DialogCallback() {
                    @Override
                    public void messageComplete(int result) {
                        if(result==1){
                            MainActivity mainActivity = (MainActivity)getActivity();
                            mainActivity.logout(null);
                        }
                    }
                };
                CustomMessages.showMessage("Logout","Are you sure you want to logout?","Yes","No",context,dialogCallback);
            }
        });

        TextView viewProfile = (TextView)rootView.findViewById(R.id.settings_view_profile);
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showActivitySpinner();
                Single sc = new Single() {
                    @Override
                    public void results(Hashtable result) {
                        if(!toastMaker.isError(result.get(Message.CODE).toString(),result.get(Message.MESSAGE).toString())){
                            final String name = result.get("name")==null?"":String.valueOf(result.get("name"));
                            final String description = result.get("description")==null?"My description...":String.valueOf(result.get("description"));
                            final String libno = result.get("lib_no")==null?"":String.valueOf(result.get("lib_no"));
                            final String username = result.get("username")==null?"":String.valueOf(result.get("username"));
                            final int id = result.get("user_id")==null?0:Integer.valueOf(String.valueOf(result.get("user_id")));

                            UserItem u = new UserItem(name,description,libno,username,id,false);

                            fragmentManager.beginTransaction().add(R.id.container, FriendProfileFragment.newInstance(u), "SettingsSubView").addToBackStack(null).commit();

                        }
                        hideActivitySpinner();
                    }
                };

                api.v1.getUserByID(String.valueOf(api.v1.currentUserId()),sc);
            }
        });

        return rootView;
    }
}