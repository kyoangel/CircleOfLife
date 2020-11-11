package kyo.learncoding.circleoflife.ui.main;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.gao.jiefly.abilitychartlibrary.AbilityChatView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
                initSpinners(root);
                setupFromSavedData(root, getSavedData());



                break;
            case 2:
                root = inflater.inflate(R.layout.fragment_graph, container, false);

                AbilityChatView chart = root.findViewById(R.id.graph);

                chart.setCount(6);
                List<RoleOfCircle> savedData = getSavedData();
                List<String> titles = savedData.stream().map(x -> x.RoleName).collect(Collectors.toList());
                chart.changeTitles(titles.toArray(new String[0]));
                List<Double> points = savedData.stream().map(x->Double.parseDouble(x.CurrentPoint)*10).collect(Collectors.toList());
                chart.setData(points);
                chart.setProertyLevel(5);
                chart.setCoverColor(Color.BLUE);
                chart.setCoverStyle(Paint.Style.FILL);
                chart.setCoverAlpha(100);
                chart.setTextColor(Color.BLACK);
                chart.setTextSize(60);
                chart.setPolygonColor(Color.DKGRAY);
                chart.setPolygonStyle(Paint.Style.FILL_AND_STROKE);
                chart.setLineColor(Color.BLACK);
                chart.setLineWidth(8);
                break;
        }
        return root;
    }

    private List<RoleOfCircle> getSavedData() {
        final Gson gson = new Gson();
        List<RoleOfCircle> roleOfCircles = new ArrayList<>();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (preferences != null) {

            for (int i = 1; i <= 6; i++) {
                String keyName = "role" + i;
                String prefRole = preferences.getString(keyName, "");
                if (prefRole.isEmpty()) {
                    break;
                }
                RoleOfCircle roleOfCircle = gson.fromJson(prefRole, RoleOfCircle.class);
                roleOfCircles.add(roleOfCircle);
            }
        }
        return roleOfCircles;
    }

    private void setupFromSavedData(View root, List<RoleOfCircle> roleOfCircles) {
            for (int i = 1; i <= 6; i++) {
                String keyName = "role" + i;
                RoleOfCircle roleOfCircle = roleOfCircles.get(i-1);
                int roleId = getContext().getResources().getIdentifier(keyName, "id", getContext().getPackageName());
                EditText role = (EditText) root.findViewById(roleId);
                role.setText(roleOfCircle.RoleName);
                int spinnerId = getContext().getResources().getIdentifier("spinner" + i, "id", getContext().getPackageName());
                Spinner spinner = (Spinner) root.findViewById(spinnerId);
                spinner.setSelection(Integer.parseInt(roleOfCircle.CurrentPoint) - 1);
            }
    }

    private void initSpinners(View root) {
        Spinner spinner1 = root.findViewById(R.id.spinner1);
        Spinner spinner2 = root.findViewById(R.id.spinner2);
        Spinner spinner3 = root.findViewById(R.id.spinner3);
        Spinner spinner4 = root.findViewById(R.id.spinner4);
        Spinner spinner5 = root.findViewById(R.id.spinner5);
        Spinner spinner6 = root.findViewById(R.id.spinner6);
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