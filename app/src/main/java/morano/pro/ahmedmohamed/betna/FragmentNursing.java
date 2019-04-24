package morano.pro.ahmedmohamed.betna;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentNursing extends Fragment {

    // The view that will be connected with the fragment
    View nursingView;

    Button nursingStartNowButton;

    public FragmentNursing() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout that will be connected with the fragment
        nursingView = inflater.inflate(R.layout.fragment_nursing, container, false);


        nursingStartNowButton = nursingView.findViewById(R.id.nursing_start_now_button);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/cairo_regular.ttf");
        nursingStartNowButton.setTypeface(typeface);

        nursingStartNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nursingIntent = new Intent(getActivity(), SendOrderForNursing.class);
                startActivity(nursingIntent);
            }
        });

        return nursingView;


    }
}