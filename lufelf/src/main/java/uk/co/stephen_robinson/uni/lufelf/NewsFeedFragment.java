package uk.co.stephen_robinson.uni.lufelf;

        import android.app.Activity;
        import android.app.ActionBar;
        import android.app.Fragment;
        import android.app.FragmentManager;
        import android.app.FragmentTransaction;
        import android.content.Context;
        import android.graphics.drawable.ColorDrawable;
        import android.os.Build;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.support.v4.widget.DrawerLayout;
        import android.view.Window;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;
        import android.graphics.Color;
/**
 * Created by James on 30/01/2014.
 */
public class NewsFeedFragment extends BaseFragment{

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static NewsFeedFragment newInstance() {
        return new NewsFeedFragment();
    }

    public NewsFeedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setFragmentManager(getFragmentManager());
        View rootView = inflater.inflate(R.layout.fragment_news_feed, container, false);
        return rootView;
    }
}
