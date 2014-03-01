package uk.co.stephen_robinson.uni.lufelf.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.stephen_robinson.uni.lufelf.R;
import uk.co.stephen_robinson.uni.lufelf.activities.LoginActivity;
import uk.co.stephen_robinson.uni.lufelf.activities.MainActivity;

/**
 * @author James
 * Place profile
 */
public class SplashFragment extends BaseFragment{

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    public SplashFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init
        setFragmentManager(getFragmentManager());
        rootView = inflater.inflate(R.layout.splash_screen_layout, container, false);
        setContext(rootView.getContext());
        //showActivitySpinner();
        /*
        Single nc = new Single() {
            @Override
            public void results(Hashtable result) {
                try{
                    Thread.sleep(2000);
                }catch (Exception e){

                }

            }
        };
        api.v1.loginUser("test", "test", nc);*/
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               determineAction();
            }
        }, 2000);

        return rootView;
    }
    public void determineAction(){
        if(api.v1.isLoggedIn()){
            
            Intent swapToApp=new Intent(getActivity().getApplicationContext(),MainActivity.class);
            Bundle args = new Bundle();
            args.putInt("priority",1);
            swapToApp.putExtras(args);
            startActivity(swapToApp);
        }else{
            Intent swapToLogin=new Intent(getActivity().getApplicationContext(),LoginActivity.class);
            startActivity(swapToLogin);
            getActivity().overridePendingTransition(R.anim.fade_out,0);
        }
    }
}