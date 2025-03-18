package com.example.languageselection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class LocationFragment extends Fragment {
    private static final String ROOT_FRAGMENT_TAG = "root_fragment";
    private Button skipButton, allowButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        skipButton = view.findViewById(R.id.skipButton);
        allowButton = view.findViewById(R.id.allowButton);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle skip button click (e.g., navigate to the next screen)
            }
        });

        allowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle allow button click (e.g., request location permission)
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set up a back press listener
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Navigate back to NotificationsFragment
                loadFragment(new NotificationsFragment(), 0);
            }
        });
    }

    public void loadFragment(Fragment fragment, int flag) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (flag == 0) {
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(ROOT_FRAGMENT_TAG);
        } else {
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
}