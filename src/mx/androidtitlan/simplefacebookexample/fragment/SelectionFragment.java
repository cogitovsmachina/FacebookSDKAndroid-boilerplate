package mx.androidtitlan.simplefacebookexample.fragment;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.RequestBatch;
import com.facebook.Response;
import com.facebook.Session;

import mx.androidtitlan.simplefacebookexample.R;
import mx.androidtitlan.simplefacebookexample.ShareDialogActivity;
import mx.androidtitlan.simplefacebookexample.R.layout;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.os.EnvironmentCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class SelectionFragment extends Fragment implements OnClickListener {
	private static final List<String> PERMISSIONS = Arrays
			.asList("publish_actions");
	private Button publishButton;
	private boolean pendingPublishReauthorization;
	private Button shareDialogButton;
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";

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
		publishButton = (Button) view.findViewById(R.id.share_button);
		publishButton.setOnClickListener(this);
		shareDialogButton = (Button) view.findViewById(R.id.share_dialog_button);
		shareDialogButton.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View view) {
		view.getId();
		if (view.getId() == R.id.share_button) {
			publishStory();
		}
		if (view.getId() == R.id.share_dialog_button) {
			startActivity(new Intent(getActivity(), ShareDialogActivity.class));
		}
	}

	private void publishStory() {
		Session session = Session.getActiveSession();
		if (session != null) {
			List<String> permissions = session.getPermissions();
			if (!isSubsetOf(PERMISSIONS, permissions)) {
				pendingPublishReauthorization = true;
				Session.NewPermissionsRequest permissionRequest = new Session.NewPermissionsRequest(
						getActivity(), PERMISSIONS);
				session.requestNewPublishPermissions(permissionRequest);
				return;
			}
			Bundle shareParams = new Bundle();
			shareParams.putString("name", "Carrera Zurich para Android 2013");
			// shareParams.putString("caption",
			// "Checa mi ruta de entrenamiento para la 1er carrera Zurich 2013, hoy corrí "+" "+"km");
			shareParams.putString("description",
					"Checa mi ruta de entrenamiento para la 1er carrera Zurich 2013, hoy corrí "
							+ " " + "km");
			// shareParams.putString("link",
			// "http://androidtitlan.mx");
			// TODO: Use share Dialog instead

			shareParams
					.putString("picture",
							"https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");

			Request.Callback callback = new Request.Callback() {

				@Override
				public void onCompleted(Response response) {
					Log.wtf("***", response.toString());
					JSONObject graphResponse = response.getGraphObject()
							.getInnerJSONObject();
					Log.wtf("***", "");
					String postId = null;
					try {
						postId = graphResponse.getString("id");
					} catch (JSONException e) {
						Log.i("***", "JSON error " + e.getMessage());
					}
					FacebookRequestError error = response.getError();
					if (error != null) {
						Toast.makeText(getActivity(), error.getErrorMessage(),
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getActivity(), postId, Toast.LENGTH_LONG)
								.show();
					}

				}
			};

			Request request = new Request(session, "me/feed", shareParams,
					HttpMethod.POST, callback);

			RequestAsyncTask task = new RequestAsyncTask(request);
			task.execute();
		}
	}

	private boolean isSubsetOf(Collection<String> subset,
			Collection<String> superset) {
		for (String string : subset) {
			if (!superset.contains(string)) {
				return false;
			}
		}
		return true;
	}

}
