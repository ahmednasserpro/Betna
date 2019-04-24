package morano.pro.ahmedmohamed.betna;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;


public class FragmentMaintenance extends Fragment {

    // The view that will be connected with the fragment
    View maintenanceView;

    // The ViewFlipper object
    ViewFlipper viewFlipper;

    List<ServiceContent> contents;

    // The constructor and it is empty as we won't any specific initialization
    public FragmentMaintenance() {
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        // Inflate the layout that will be connected with the fragment
        maintenanceView = inflater.inflate(R.layout.fragment_maintenance, container,
                false);

        // Add images resources in array
        int[] imagesFlipper = {
                R.drawable.service_plumber_image,
                R.drawable.service_painter_image,
                R.drawable.service_electrician_image,
                R.drawable.service_carpenter_image,
                R.drawable.service_cleaner_image,
                R.drawable.service_flooring_image,
                R.drawable.service_ac_image,
                R.drawable.service_pestcontrol_image };

        // Find the view to connect it the viewFlipper object
        viewFlipper = maintenanceView.findViewById(R.id.image_flipper);
        for (int u : imagesFlipper)
            autoFlipperImages(u);


        contents = new ArrayList<>();

        contents.add(
                new ServiceContent(R.drawable.electrician_icon,
                        R.drawable.service_electrician_image,
                        getString(R.string.electrician),
                        getString(R.string.electrician_description)));

        contents.add(new ServiceContent(
                R.drawable.plumber_icon,
                R.drawable.service_plumber_image,
                getString(R.string.plumber),
                getString(R.string.plumber_description)));

        contents.add(new ServiceContent(
                R.drawable.painter_icon,
                R.drawable.service_painter_image,
                getString(R.string.painter),
                getString(R.string.painter_description)));

        contents.add(new ServiceContent(
                R.drawable.carpenter_icon,
                R.drawable.service_carpenter_image,
                getString(R.string.carpenter),
                getString(R.string.carpenter_description)));

        contents.add(new ServiceContent(
                R.drawable.blacksmith_icon,
                R.drawable.service_blacksmith_image,
                getString(R.string.blacksmith),
                getString(R.string.blacksmith_description)));

        contents.add(new ServiceContent(
                R.drawable.cleaner_icon,
                R.drawable.service_cleaner_image,
                getString(R.string.cleaner),
                getString(R.string.cleaner_description)));

        contents.add(new ServiceContent(
                R.drawable.flooring_icon,
                R.drawable.service_flooring_image,
                getString(R.string.flooring),
                getString(R.string.flooring_description)));

        contents.add(new ServiceContent(
                R.drawable.plaster_icon,
                R.drawable.service_plasterer_image,
                getString(R.string.plasterer),
                getString(R.string.plasterer_description)));

        contents.add(new ServiceContent(
                R.drawable.marble_icon,
                R.drawable.service_marble_image,
                getString(R.string.marble),
                getString(R.string.marble_description)));

        contents.add(new ServiceContent(
                R.drawable.gypsum_icon,
                R.drawable.service_gypsum_image,
                getString(R.string.gypsum_board),
                getString(R.string.gypsum_board_description)));

        contents.add(new ServiceContent(
                R.drawable.storge_icon,
                R.drawable.service_storge_image,
                getString(R.string.wood_sprayer),
                getString(R.string.wood_sprayer_description)));

        contents.add(new ServiceContent(
                R.drawable.alumital_icon,
                R.drawable.service_alumital_image,
                getString(R.string.alumital),
                getString(R.string.alumital_description)));

        contents.add(new ServiceContent(
                R.drawable.ac_icon,
                R.drawable.service_ac_image,
                getString(R.string.air_conditioner),
                getString(R.string.air_conditioner_description)));

        contents.add(new ServiceContent(
                R.drawable.pestcontrol_icon,
                R.drawable.service_pestcontrol_image,
                getString(R.string.pest_control),
                getString(R.string.pest_control_description)));

        contents.add(new ServiceContent(
                R.drawable.packer_icon,
                R.drawable.service_packer_image,
                getString(R.string.packer_mover),
                getString(R.string.packer_mover_description)));

        contents.add(new ServiceContent(
                R.drawable.homesecurity_icon,
                R.drawable.service_home_security_image,
                getString(R.string.home_security),
                getString(R.string.home_security_description)));

        contents.add(new ServiceContent(
                R.drawable.finishingworks_icon,
                R.drawable.service_finishing_works_image,
                getString(R.string.finishing_works),
                getString(R.string.finishing_works_description)));


        RecyclerView myRecycler = (RecyclerView) maintenanceView.findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), contents);
        myRecycler.setNestedScrollingEnabled(false);
        myRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        myRecycler.setAdapter(adapter);


        return maintenanceView;

    }


    public void autoFlipperImages(int image) {

        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(image);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);

    }


}