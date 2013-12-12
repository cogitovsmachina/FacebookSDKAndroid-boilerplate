package mx.androidtitlan.simplefacebookexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.UserSettingsFragment;

public class MainActivity extends ActionBarActivity {
	private boolean isResumed;
	private UiLifecycleHelper uiHelper;
	private FragmentManager fragmentManager;
	private Fragment selection = SelectionFragment.newInstance();
	private Fragment splash = SplashFragment.newInstance();
	private Fragment logout = new UserSettingsFragment();

	private Session.StatusCallback callback = new Session.StatusCallback() {

		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		uiHelper = new UiLifecycleHelper(this, callback);
		setupFragmentManager();

	}

	private void fragmentSwitcher(Fragment which) {
		if (getSupportFragmentManager().findFragmentByTag("CURRENT_FRAGMENT")
				.equals(which)) {
		} else {
			fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.main_layout, which, "CURRENT_FRAGMENT")
					.commit();
		}
	}

	private void setupFragmentManager() {
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.main_layout);
		if (fragment == null) {
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(R.id.main_layout, splash, "CURRENT_FRAGMENT");
			ft.commit();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
		isResumed = true;
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
		isResumed = false;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {

		if (isResumed) {
			if (state.isOpened()) {
				fragmentSwitcher(selection);
			} else if (state.isClosed()) {
				fragmentSwitcher(splash);
			}
		}
	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			fragmentSwitcher(selection);
		} else {
			fragmentSwitcher(splash);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settings:
			fragmentSwitcher(logout);
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

}
