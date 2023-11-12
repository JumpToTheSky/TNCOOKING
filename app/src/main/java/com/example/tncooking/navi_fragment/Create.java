package com.example.tncooking.navi_fragment;

import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tncooking.MainActivity;
import com.example.tncooking.R;
import com.example.tncooking.recipes.Recipe;
import com.example.tncooking.recipes.RecipeAdapterRow;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class Create extends Fragment {

    int REQUEST_CODE_IMAGE = 1;
    ImageView cavatar;
    StorageReference storageReference = FirebaseStorage.getInstance("gs://tncooking-38146.appspot.com").getReference();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://tncooking-38146-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    DatabaseReference _myRef;
    Bitmap bitmap1;
    Recipe recipe = new Recipe();
    EditText cname,cintroduce,cingredients,cguide;
    Button save;
    RadioGroup kindRc;
    Uri imUri;
    String url;
    private static final int PICK_IMAGE_REQUEST = 1;

    public Create() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_create_recipe, container, false);


        cname = view.findViewById(R.id.CRecipesName);
        cingredients = view.findViewById(R.id.CIngredients);
        cintroduce = view.findViewById(R.id.CIntroduce);
        cguide = view.findViewById(R.id.CGuide);
        kindRc = view.findViewById(R.id.recipe_kind);
        cavatar = view.findViewById(R.id.cavatar);
        save = view.findViewById(R.id.save);


        cavatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, REQUEST_CODE_IMAGE);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imUri = data.getData();
            cavatar.setVisibility(View.VISIBLE);
            Picasso.with(getContext()).load(imUri).into(cavatar);
        }
//        else if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            cavatar.setVisibility(View.VISIBLE);
//            cavatar.setImageBitmap(bitmap);
//            bitmap1 = bitmap;
//        }


    }


    public void dialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Xác nhận tạo công thức ?");

//        builder.setPositiveButton("Chụp ảnh", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, REQUEST_CODE_IMAGE);
//            }
//        });

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (cname.getText().toString().trim().length() < 2){
                    Toast.makeText(getContext(), "Tên món ăn cần dài hơn 2 kí tự", Toast.LENGTH_LONG);
                return;
                }

                if (cguide.getText().toString().trim().length() == 0){
                    Toast.makeText(getContext(), "Công thức nấu ăn đang bị bỏ trống", Toast.LENGTH_LONG);
                    return;
                }

                if (cingredients.getText().toString().trim().length() == 0){
                    Toast.makeText(getContext(), "Nguyên liệu nấu ăn đang bị bỏ trống", Toast.LENGTH_LONG);
                    return;
                }
                String id = UUID.randomUUID().toString();
                String kind = null;
                switch (kindRc.getCheckedRadioButtonId()){
                    case R.id.C_Á:
                        kind = "Á";
                        break;
                    case R.id.C_Âu:
                        kind = "Âu";
                        break;
                    case R.id.C_Việt:
                        kind = "Việt";
                        break;
                    default:
                        kind = "Á";
                        break;
                }
                recipe.setName(cname.getText().toString().trim());
                recipe.setIntroduce(cintroduce.getText().toString().trim());
                recipe.setIngredient(cingredients.getText().toString().trim());
                recipe.setGuide(cguide.getText().toString().trim());
                recipe.setType(kind);


                storageReference.child(id).putFile(imUri);
                recipe.setAvatar("https://firebasestorage.googleapis.com/v0/b/tncooking-38146.appspot.com/o/"+id+"?alt=media&token=");
                mDatabase.push().setValue(recipe);

                Toast.makeText(getContext(),
                        "Tạo thành công", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }
}
