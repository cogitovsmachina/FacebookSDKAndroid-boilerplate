package mx.androidtitlan.simplefacebookexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SelectionFragment extends Fragment {
	
	public static final SelectionFragment newInstance() {
		SelectionFragment fragment = new SelectionFragment();
		// Bundle bundle = new Bundle(1);
		// bundle.putString(EXTRA_URL, url);
		// fragment.setArguments(bundle);
		Log.i("SelectionFragment", "Created fragment");
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_selection, container,
				false);
		return view;
	}

}
