package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView alsoKnownTvTextView;
    private TextView ingredientsTvTextView;
    private TextView originTvTextView;
    private TextView descriptionTvTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownTvTextView = findViewById(R.id.also_known_tv);
        ingredientsTvTextView = findViewById(R.id.ingredients_tv);
        originTvTextView = findViewById(R.id.origin_tv);
        descriptionTvTextView = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        Picasso.with(this).load(sandwich.getImage()).into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        String originString = sandwich.getPlaceOfOrigin();
        if (originString.isEmpty()) {
            originTvTextView.setText(R.string.no_data_to_display);
        } else{
            originTvTextView.setText(originString);
        }

        String descriptionString = sandwich.getDescription();
        if (descriptionString.isEmpty()) {
            descriptionTvTextView.setText(R.string.no_data_to_display);
        } else {
            descriptionTvTextView.setText(descriptionString);
        }

        List<String> alsoKnownAsStringList = sandwich.getAlsoKnownAs();
        if (alsoKnownAsStringList.size() == 0) {
            alsoKnownTvTextView.setText(R.string.no_data_to_display);
        } else {
            alsoKnownTvTextView.setText(listBuilder(alsoKnownAsStringList));
        }

        List<String> ingredientsStringList = sandwich.getIngredients();
        if (ingredientsStringList.size() == 0) {
            ingredientsTvTextView.setText(R.string.no_data_to_display);
        } else {
            ingredientsTvTextView.setText(listBuilder(ingredientsStringList));
        }
    }

    private StringBuilder listBuilder(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i)).append("\n");
        }
        return stringBuilder;
    }
}
