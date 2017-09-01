package com.example.android.catwalk;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.catwalk.data.CatContract;

public class InsertInfoActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1000;

    private EditText mNameFieldView;
    private EditText mBreedFieldView;
    private EditText mLocationFieldView;
    private EditText mColourFieldView;
    private Spinner mGenderFieldView;

    private Uri mImageUri;

    private boolean mProductHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mProductHasChanged = true;
            return false;
        }
    };

    private String mGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_info);

        setTitle("Add A Product");

        invalidateOptionsMenu();

        mNameFieldView = (EditText) findViewById(R.id.name);
        mBreedFieldView = (EditText) findViewById(R.id.breed);
        mLocationFieldView = (EditText) findViewById(R.id.location);
        mColourFieldView = (EditText) findViewById(R.id.colour);
        mGenderFieldView = (Spinner) findViewById(R.id.gender);
        Button mImageFieldButton = (Button) findViewById(R.id.add_image);

        mNameFieldView.setOnTouchListener(mTouchListener);
        mLocationFieldView.setOnTouchListener(mTouchListener);
        mColourFieldView.setOnTouchListener(mTouchListener);
        mBreedFieldView.setOnTouchListener(mTouchListener);
        mGenderFieldView.setOnTouchListener(mTouchListener);
        mImageFieldButton.setOnTouchListener(mTouchListener);

        mImageFieldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        setupSpinner();
    }

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenderFieldView.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGenderFieldView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = CatContract.CatEntry.GENDER_MALE;
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = CatContract.CatEntry.GENDER_FEMALE;
                    } else if (selection.equals(getString(R.string.gender_nonbinary))) {
                        mGender = CatContract.CatEntry.GENDER_NONBINARY;
                    } else {
                        mGender = CatContract.CatEntry.GENDER_UNKNOWN;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = CatContract.CatEntry.GENDER_UNKNOWN;
            }
        });
    }

    private void selectImage() {
        Intent imageIntent;

        if (Build.VERSION.SDK_INT < 19) {
            imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            imageIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            imageIntent.addCategory(Intent.CATEGORY_OPENABLE);
        }

        imageIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(imageIntent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {

            if (resultData != null) {
                mImageUri = resultData.getData();
            }
        }
    }

    private void saveProduct() {
        String nameString = mNameFieldView.getText().toString();
        String breedString = mBreedFieldView.getText().toString();
        String locationString = mLocationFieldView.getText().toString();
        String colourString = mColourFieldView.getText().toString();

        if (nameString.equals("") || breedString.equals("") || locationString.equals("") || mImageUri == null){
            Toast.makeText(this, "Cannot save without required items", Toast.LENGTH_SHORT).show();
            return;
        }

        String imageString = mImageUri.toString();

        ContentValues values = new ContentValues();
        values.put(CatContract.CatEntry.COLUMN_ITEM_NAME, nameString);
        values.put(CatContract.CatEntry.COLUMN_ITEM_BREED, breedString);
        values.put(CatContract.CatEntry.COLUMN_ITEM_COLOUR, colourString);
        values.put(CatContract.CatEntry.COLUMN_ITEM_LOCATION, locationString);
        values.put(CatContract.CatEntry.COLUMN_ITEM_IMAGE, imageString);
        values.put(CatContract.CatEntry.COLUMN_ITEM_GENDER, mGender);

        Uri newUri = getContentResolver().insert(CatContract.CatEntry.CONTENT_URI, values);

        if (newUri == null) {
            Toast.makeText(this, "Save Failed",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Cat Saved",
                    Toast.LENGTH_SHORT).show();
            mProductHasChanged = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_product:
                saveProduct();
            case android.R.id.home:
                if (!mProductHasChanged) {
                    NavUtils.navigateUpFromSameTask(InsertInfoActivity.this);
                    return true;
                }

                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NavUtils.navigateUpFromSameTask(InsertInfoActivity.this);
                            }
                        };

                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (!mProductHasChanged) {
            super.onBackPressed();
            return;
        }

        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };

        showUnsavedChangesDialog(discardButtonClickListener);
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Discard Changes?");
        builder.setPositiveButton("Discard Changes", discardButtonClickListener);
        builder.setNegativeButton("Continue Editing", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
