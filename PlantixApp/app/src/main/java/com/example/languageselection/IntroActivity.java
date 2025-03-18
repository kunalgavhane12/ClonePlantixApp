package com.example.languageselection;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class IntroActivity extends AppCompatActivity {

    private static final String ROOT_FRAGMENT_TAG = "root_fragment";
    private ViewFlipper viewFlipper;
    private Button skipButton, nextButton;
    private TextView dot1, dot2, dot3, dot4;
    private Handler handler = new Handler();
    private Runnable slideRunnable;
    private LinearLayout introContainer;
    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        viewFlipper = findViewById(R.id.viewFlipper);
        skipButton = findViewById(R.id.skipButton);
        nextButton = findViewById(R.id.nextButton);

        dot1 = findViewById(R.id.dot1);
        dot2 = findViewById(R.id.dot2);
        dot3 = findViewById(R.id.dot3);
        dot4 = findViewById(R.id.dot4);

        introContainer = findViewById(R.id.intro_container);
        fragmentContainer = findViewById(R.id.fragment_container);

        // Start the ViewFlipper to automatically change slides every 5 seconds
        viewFlipper.setFlipInterval(5000);  // 5 seconds interval
        viewFlipper.startFlipping();

        // Skip button functionality: Show the NotificationsFragment and hide the intro content
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the intro content
                introContainer.setVisibility(View.GONE);
                // Show the fragment container
                fragmentContainer.setVisibility(View.VISIBLE);
                // Load the NotificationsFragment
                loadFragment(new NotificationsFragment(), 0);
            }
        });

        // Next button functionality: Change the slide
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewFlipper.getDisplayedChild() < viewFlipper.getChildCount() - 1) {
                    viewFlipper.showNext();
                    updateDotColors(viewFlipper.getDisplayedChild()); // Update dot color after changing slide
                } else {
                    // Hide the intro content
                    introContainer.setVisibility(View.GONE);
                    // Show the fragment container
                    fragmentContainer.setVisibility(View.VISIBLE);
                    // Load the NotificationsFragment
                    loadFragment(new NotificationsFragment(), 0);
                }
            }
        });

        // Initialize the dot colors periodically
        slideRunnable = new Runnable() {
            @Override
            public void run() {
                int currentPosition = viewFlipper.getDisplayedChild();
                updateDotColors(currentPosition);
                handler.postDelayed(this, 1000);  // Re-run every 1 second
            }
        };
        handler.postDelayed(slideRunnable, 1000);
    }

    private void updateDotColors(int position) {
        // Reset all dots to gray
        dot1.setBackgroundResource(R.drawable.gray_dot);
        dot2.setBackgroundResource(R.drawable.gray_dot);
        dot3.setBackgroundResource(R.drawable.gray_dot);
        dot4.setBackgroundResource(R.drawable.gray_dot);

        // Set the active dot to blue based on the current slide position
        switch (position) {
            case 0:
                dot1.setBackgroundResource(R.drawable.blue_dot);
                break;
            case 1:
                dot2.setBackgroundResource(R.drawable.blue_dot);
                break;
            case 2:
                dot3.setBackgroundResource(R.drawable.blue_dot);
                break;
            case 3:
                dot4.setBackgroundResource(R.drawable.blue_dot);
                break;
        }
    }

    public void loadFragment(Fragment fragment, int flag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (flag == 0) {
            fragmentTransaction.add(R.id.fragment_container, fragment);
            fragmentManager.popBackStack(ROOT_FRAGMENT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentTransaction.addToBackStack(ROOT_FRAGMENT_TAG);
        } else {
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(slideRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(slideRunnable, 1000);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);

        // If the current fragment is NotificationsFragment, go back to intro slides
        if (currentFragment instanceof NotificationsFragment) {
            // Hide the fragment container
            fragmentContainer.setVisibility(View.GONE);
            // Show the intro container
            introContainer.setVisibility(View.VISIBLE);
            // Reset the ViewFlipper to the first slide
            viewFlipper.setDisplayedChild(0);
            updateDotColors(0); // Update the dot colors
        } else {
            // For other fragments (e.g., LocationFragment), let the fragment back stack handle the back press
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }
}