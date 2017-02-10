package com.ob.myntra.ui.my;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.app.NavigationPolicy;
import com.heinrichreimersoftware.materialintro.app.OnNavigationBlockedListener;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;
import com.heinrichreimersoftware.materialintro.slide.Slide;
import com.ob.myntra.R;

public class LHomeActivity extends IntroActivity {


    private boolean scrollable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        boolean fullscreen = true;
        boolean showBack = false;
        boolean showNext = false;
        boolean skipEnabled = false;
        boolean finishEnabled = true;
        boolean getStartedEnabled = true;
        boolean pageIndicatorEnabled = true;


        super.onCreate(savedInstanceState);

           /* Enable/disable fullscreen */
        setFullscreen(fullscreen);

        setButtonBackFunction(skipEnabled ? BUTTON_BACK_FUNCTION_SKIP : BUTTON_BACK_FUNCTION_BACK);
        setButtonNextFunction(finishEnabled ? BUTTON_NEXT_FUNCTION_NEXT_FINISH : BUTTON_NEXT_FUNCTION_NEXT);
        setButtonBackVisible(showBack);
        setButtonNextVisible(showNext);
        setButtonCtaVisible(getStartedEnabled);
        setButtonCtaTintMode(BUTTON_CTA_TINT_MODE_TEXT);
        setPagerIndicatorVisible(pageIndicatorEnabled);



        // Custom intro screen first slide
        final Slide firstSlide = new FragmentSlide.Builder()
                    .background(R.color.red)
                    .backgroundDark(R.color.gray)
                    .fragment(ScreenOneSlide.newInstance())
                    .buttonCtaLabel("Do start")
                    .build();
            addSlide(firstSlide);

        // Custom intro screen second screen
        final Slide secondSlide = new FragmentSlide.Builder()
                .background(R.color.red)
                .backgroundDark(R.color.gray)
                .fragment(ScreenTwoSlide.newInstance())
                .buttonCtaLabel("Do start")
                .build();
        addSlide(secondSlide);
        /**
         * Standard slide (like Google's intros)
         */
        addSlide(new SimpleSlide.Builder()
                .title("Title two")
                .description("description")
                .image(R.drawable.ic_kids)
                .background(R.color.menu_font_color)
                .backgroundDark(R.color.colorPrimaryDark)
                .buttonCtaLabel("Hello")
                .buttonCtaClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast toast = Toast.makeText(LHomeActivity.this, "toast_button_cta", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    }
                })
                .build());

        /**
         * Standard slide (like Google's intros)
         */
        addSlide(new SimpleSlide.Builder()
                .title("Title one")
                .description("description")
                .image(R.drawable.oracle_logo)
                .background(R.color.colorPrimary)
                .backgroundDark(R.color.colorPrimaryDark)
                .buttonCtaClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast toast = Toast.makeText(LHomeActivity.this, "toast_button_cta", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    }
                })
                .build());



        addSlide(new SimpleSlide.Builder()
                .title("title_material_motion")
                .description("description_material_motion")
                .image(R.drawable.ic_women)
                .background(R.color.wait_color)
                .backgroundDark(R.color.bg_register)
                .scrollable(scrollable)
                .build());

          /* Add a navigation policy to define when users can go forward/backward */
        setNavigationPolicy(new NavigationPolicy() {
            @Override
            public boolean canGoForward(int position) {
                return true;
            }

            @Override
            public boolean canGoBackward(int position) {
                return true;
            }
        });




        /* Add a listener to detect when users try to go to a page they can't go to */
        addOnNavigationBlockedListener(new OnNavigationBlockedListener() {
            @Override
            public void onNavigationBlocked(int position, int direction) {
            }
        });

        /* Add your own page change listeners */
        addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


}
