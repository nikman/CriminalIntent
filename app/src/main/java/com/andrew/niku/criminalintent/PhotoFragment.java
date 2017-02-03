package com.andrew.niku.criminalintent;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by niku on 03.02.2017.
 */

public class PhotoFragment extends DialogFragment {

    private static final String ARG_FILENAME = "file_name";

    public static PhotoFragment newInstance(String photoFileFullName) {

        Bundle args = new Bundle();
        args.putString(ARG_FILENAME, photoFileFullName);

        PhotoFragment photoFragment = new PhotoFragment();
        photoFragment.setArguments(args);

        return photoFragment;

    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);

        String photoFileName = (String) getArguments().getString(ARG_FILENAME);

        if (photoFileName == null) {
            return null;
        }

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_photo, null);

        ImageView crimePhoto = (ImageView) view.findViewById(R.id.dialog_photo_image_view);

        if (crimePhoto == null) {
            return null;
        }

        Bitmap ibm = PictureUtils.getScaledBitmap(photoFileName, getActivity());
        crimePhoto.setImageBitmap(ibm);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.crime_full_photo_title)
                .create();

    }
}
