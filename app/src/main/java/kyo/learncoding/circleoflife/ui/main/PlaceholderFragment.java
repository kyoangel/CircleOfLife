package kyo.learncoding.circleoflife.ui.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Set;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import kyo.learncoding.circleoflife.R;
import kyo.learncoding.circleoflife.model.RoleOfCircle;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = null;
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1:
                root = inflater.inflate(R.layout.fragment_role_setting, container, false);
                SetupSpinners(root);

                LoadDataFromSharePreference(root);


                break;
            case 2:
                root = inflater.inflate(R.layout.fragment_graph, container, false);
                break;
        }
        return root;
    }


    private void LoadDataFromSharePreference(View root) {
        final Gson gson = new Gson();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (preferences != null) {

            for (int i = 1; i <= 6; i++) {
                String keyName = "role" + i;
                String prefRole = preferences.getString(keyName, "");
                if (prefRole.isEmpty()) {
                    break;
                }
                RoleOfCircle roleOfCircle1 = gson.fromJson(prefRole, RoleOfCircle.class);
                int roleId = getContext().getResources().getIdentifier(keyName, "id", getContext().getPackageName());
                EditText role = (EditText) root.findViewById(roleId);
                role.setText(roleOfCircle1.RoleName);
                int spinnerId = getContext().getResources().getIdentifier("spinner" + i, "id", getContext().getPackageName());
                Spinner spinner = (Spinner) root.findViewById(spinnerId);
                spinner.setSelection(Integer.parseInt(roleOfCircle1.CurrentPoint) - 1);
            }

        }
    }

    private void SetupSpinners(View root) {
        Spinner spinner1 = (Spinner) root.findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) root.findViewById(R.id.spinner2);
        Spinner spinner3 = (Spinner) root.findViewById(R.id.spinner3);
        Spinner spinner4 = (Spinner) root.findViewById(R.id.spinner4);
        Spinner spinner5 = (Spinner) root.findViewById(R.id.spinner5);
        Spinner spinner6 = (Spinner) root.findViewById(R.id.spinner6);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.points, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        spinner4.setAdapter(adapter);
        spinner5.setAdapter(adapter);
        spinner6.setAdapter(adapter);
    }
}