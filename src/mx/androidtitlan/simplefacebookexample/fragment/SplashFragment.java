package mx.androidtitlan.simplefacebookexample.fragment;

import mx.androidtitlan.simplefacebookexample.R;
import mx.androidtitlan.simplefacebookexample.R.layout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SplashFragment extends Fragment {

	public static final SplashFragment newInstance() {
		SplashFragment fragment = new SplashFragment();
		// Bundle bundle = new Bundle(1);
		// bundle.putString(EXTRA_URL, url);
		// fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_splash, container, false);
		Log.wtf("***", "SplashFragment created");
		return view;
	}

}
