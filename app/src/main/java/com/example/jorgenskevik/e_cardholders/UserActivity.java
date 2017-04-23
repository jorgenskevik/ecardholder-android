package com.example.jorgenskevik.e_cardholders;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.example.jorgenskevik.e_cardholders.Variables.KVTVariables;
import com.example.jorgenskevik.e_cardholders.models.SessionManager;
import com.example.jorgenskevik.e_cardholders.models.User;
import com.example.jorgenskevik.e_cardholders.remote.UserAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The type User activity.
 */
public class UserActivity extends AppCompatActivity implements ActionSheet.ActionSheetListener {
    /**
     * The constant CAM_REQUEST_CODE.
     */
    public static final int CAM_REQUEST_CODE = 457843;
    private static int IMAGE_GALLERY_REQUEST = 20;
    /**
     * The Session manager.
     */
    SessionManager sessionManager;
    /**
     * The Expiration button.
     */
//Uri imageUri, data;
    Button expirationButton;
    /**
     * The View 2.
     */
    ImageView view2, /**
     * The Image.
     */
    image;
    /**
     * The Code button.
     */
    ImageButton codeButton;
    /**
     * The First and sir name.
     */
    TextView firstAndSirName, /**
     * The Student id.
     */
    studentId, /**
     * The Birth day.
     */
    BirthDay;
    /**
     * The First and sir name string.
     */
    String firstAndSirNameString, /**
     * The This exp date.
     */
    thisExpDate, /**
     * The Birthday string.
     */
    birthdayString, /**
     * The Student id string.
     */
    studentIDString, /**
     * The Path.
     */
    path, /**
     * The Picture.
     */
    picture, /**
     * The Extra student id.
     */
    extraStudentID, /**
     * The Date time birthday.
     */
    dateTimeBirthday, /**
     * The Date time expiration.
     */
    dateTimeExpiration, /**
     * The Build version.
     */
    buildVersion, /**
     * The First letter.
     */
    firstLetter;
    /**
     * The Expiration date string.
     */
    String expirationDateString, /**
     * The Media path.
     */
    mediaPath, /**
     * The Formatted date.
     */
    formattedDate, /**
     * The Expiration date.
     */
    expirationDate, /**
     * The Four digits.
     */
    fourDigits, /**
     * The Picture path.
     */
    picturePath, /**
     * The Authenticate string.
     */
    authenticateString, /**
     * The Username.
     */
    username, /**
     * The Email.
     */
    email, /**
     * The Id.
     */
    id, /**
     * The Student number.
     */
    studentNumber, /**
     * The Role.
     */
    role, /**
     * The Picture token.
     */
    pictureToken;
    /**
     * The Simple date format.
     */
    SimpleDateFormat simpleDateFormat;
    /**
     * The Start date.
     */
    Date startDate, /**
     * The Date.
     */
    date;
    /**
     * The Selected color.
     */
    int selectedColor, /**
     * The Selected white.
     */
    selectedWhite, /**
     * The Duration.
     */
    duration, /**
     * The Number.
     */
    number, /**
     * The Column index.
     */
    columnIndex;
    /**
     * The Target format.
     */
    DateFormat targetFormat;
    /**
     * The Intent.
     */
    Intent intent, /**
     * The Photo picker intent.
     */
    photoPickerIntent;
    /**
     * The User details.
     */
    HashMap<String, String> userDetails;
    /**
     * The Context.
     */
    Context context;
    /**
     * The Toast.
     */
    Toast toast;
    /**
     * The Picture file.
     */
    File pictureFile, /**
     * The Directory.
     */
    directory, /**
     * The My path.
     */
    myPath;
    /**
     * The User date of birth date.
     */
    java.util.Date userDateOfBirthDate, /**
     * The User expiration date.
     */
    userExpirationDate ;
    /**
     * The Date time.
     */
    DateTime dateTime, /**
     * The Date time 2.
     */
    dateTime2;
    /**
     * The Date time formatter.
     */
    DateTimeFormatter dateTimeFormatter, /**
     * The Date time formatter 2.
     */
    dateTimeFormatter2;
    /**
     * The Input stream.
     */
    InputStream inputStream;
    /**
     * The Bitmap.
     */
    Bitmap bitmap;
    /**
     * The Stream.
     */
    ByteArrayOutputStream stream;
    //Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_view);
        sessionManager = new SessionManager(getApplicationContext());
        view2 = (ImageView) findViewById(R.id.window1);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);

        //View barcode

        codeButton = (ImageButton) findViewById(R.id.imageButton3);
        firstAndSirName = (TextView) findViewById(R.id.navnstring);
        BirthDay = (TextView) findViewById(R.id.stringfdato);
        studentId = (TextView) findViewById(R.id.studentid);
        userDetails = sessionManager.getUserDetails();

        System.gc();

        firstAndSirNameString = userDetails.get(SessionManager.KEY_NAME);
        birthdayString = userDetails.get(SessionManager.KEY_BIRTHDATE);
        targetFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.GERMANY);


        studentIDString = userDetails.get(SessionManager.KEY_STUDENTNUMBER);
        path = userDetails.get(SessionManager.KEY_PATH);
        picture = userDetails.get(SessionManager.KEY_PICTURE);

        if(path == null){
            System.out.println("1");
            view2.setImageResource(R.drawable.facebookgirl);

            if(!picture.equals("")){
                System.out.println("2");
                new DownloadImageTask((ImageView) findViewById(R.id.window1))
                        .execute(picture);
            }
        }else{
            System.out.println("3");
            File imgFile = new  File(path);
            Picasso.with(getApplicationContext()).load(imgFile).resize(300,300).centerCrop().into(view2);            //loadImageFromStorage(path);
        }

        firstAndSirName.setText(firstAndSirNameString);

        extraStudentID = getResources().getString(R.string.studentnumber) + " " + studentIDString;
        studentId.setText(extraStudentID);
        BirthDay.setText(birthdayString);
        sessionManager = new SessionManager(getApplicationContext());
        expirationDateString = userDetails.get(SessionManager.KEY_EXPERATIONDATE);
        expirationButton = (Button) findViewById(R.id.button11);

        startDate = null;
        try {
            startDate = simpleDateFormat.parse(expirationDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(System.currentTimeMillis() > startDate.getTime()){
            //Ugyldig
            selectedColor = Color.rgb(254, 56, 36);
            expirationButton.setText(R.string.expired);
            expirationButton.setTextSize(30);
            expirationButton.setBackgroundColor(selectedColor);

        } else{
            //gyldig
            selectedColor = Color.rgb(132, 205, 182);
            selectedWhite = Color.rgb(255, 255, 255);
            targetFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.GERMANY);
            try {
                thisExpDate = userDetails.get(SessionManager.KEY_EXPERATIONDATE);
                date = simpleDateFormat.parse(thisExpDate);
                formattedDate = targetFormat.format(date);
                expirationButton.setTransformationMethod(null);
                expirationDate = getResources().getString(R.string.GyldigTil) + " " + formattedDate;
                expirationButton.setText(expirationDate);
                expirationButton.setTextColor(selectedWhite);
                expirationButton.setBackgroundColor(selectedColor);
                expirationButton.setTextSize(14);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        codeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, BarCodeActivity.class));
            }
        });
    }

    /**
     * Open settings.
     *
     * @param v the v
     */
    public void openSettings(View v) {
        setTheme(R.style.ActionSheetStyleiOS7);
        showActionSheet();
    }

    /**
     * Show action sheet.
     */
    public void showActionSheet() {
        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle(R.string.closeSettings)
                .setOtherButtonTitles(getResources().getString(R.string.Loggout), getResources().getString(R.string.Terms), getResources().getString(R.string.setPicture),
                        getResources().getString(R.string.updateProfile))
                .setCancelableOnTouchOutside(true).setListener(this).show();
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
        //Log out
        if (index == 0) {
            sessionManager.logoutUser();
            intent = new Intent(UserActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        //terms
        if (index == 1) {
            intent = new Intent(UserActivity.this, Terms1Activity.class);
            startActivity(intent);

        }
        //sett profilbilde
        if (index == 2) {
            userDetails = sessionManager.getUserDetails();
            fourDigits = userDetails.get(SessionManager.KEY_PICTURETOKEN);

            if (fourDigits.equals("BRUKT")) {
                context = getApplicationContext();
                duration = Toast.LENGTH_SHORT;
                toast = Toast.makeText(context, getResources().getString(R.string.DenyPicture), duration);
                toast.show();

            } else {
                System.gc();
                photoPickerIntent = new Intent(Intent.ACTION_PICK);
                pictureFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                picturePath = pictureFile.getPath();
                Uri data = Uri.parse(picturePath);
                photoPickerIntent.setDataAndType(data, "image/*");
                startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
            }
        }
        //Oppdater brukeren
        if(index == 3){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(KVTVariables.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            userDetails = sessionManager.getUserDetails();

            authenticateString = "Bearer " + userDetails.get(SessionManager.KEY_TOKEN);

            UserAPI userapi = retrofit.create(UserAPI.class);
            userapi.getUser(KVTVariables.getAcceptVersion(), authenticateString).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User user = response.body();

                        // Session Manager
                        sessionManager = new SessionManager(getApplicationContext());

                        username = user.getName();
                        email = user.getEmail();
                        studentNumber = user.getStudentNumber();
                        id = user.getId();
                        role = user.getRole();
                        pictureToken = user.getPictureToken();
                        picture = user.getPicture();

                        userDateOfBirthDate = user.getDateOfBirth();
                        userExpirationDate = user.getExpirationDate();

                        dateTime = new DateTime(userDateOfBirthDate);
                        dateTime2 = new DateTime(userExpirationDate);

                        dateTimeFormatter = DateTimeFormat.forPattern("dd-MMM-yyyy");
                        dateTimeFormatter2 = DateTimeFormat.forPattern("yyyy-MM-dd");
                        dateTimeBirthday = dateTimeFormatter.print(dateTime);
                        dateTimeExpiration = dateTimeFormatter2.print(dateTime2);

                        sessionManager.createUpdatedLoginSession(username, email, studentNumber, id, role, pictureToken, dateTimeBirthday, dateTimeExpiration, picture);

                        startDate = null;
                        try {
                            startDate = simpleDateFormat.parse(dateTimeExpiration);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if(System.currentTimeMillis() > startDate.getTime()){
                            //Ugyldig
                            selectedColor = Color.rgb(254, 56, 36);
                            expirationButton.setText(R.string.expired);
                            expirationButton.setTextSize(30);
                            expirationButton.setBackgroundColor(selectedColor);
                        }else{
                            //gyldig
                            selectedColor = Color.rgb(132, 205, 182);
                            selectedWhite = Color.rgb(255, 255, 255);
                            targetFormat = new SimpleDateFormat("dd-MMM-yyyy");
                            try {
                                date = simpleDateFormat.parse(dateTimeExpiration);
                                formattedDate = targetFormat.format(date);
                                expirationDate = getResources().getString(R.string.GyldigTil) + " " + formattedDate;
                                expirationButton.setText(expirationDate);
                                expirationButton.setTextColor(selectedWhite);
                                expirationButton.setBackgroundColor(selectedColor);
                                expirationButton.setTextSize(14);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        firstAndSirName.setText(username);
                        BirthDay.setText(dateTimeBirthday);
                        String extra = getResources().getString(R.string.studentnumber) + " " + studentNumber;
                        studentId.setText(extra);

                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.userUpdated), Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.userNotUpdated), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.userNotUpdated), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        buildVersion = Build.VERSION.RELEASE;
        firstLetter = String.valueOf(buildVersion.charAt(0));
        number = Integer.parseInt(firstLetter);
        if(number < 6){
            System.gc();
            if(data == null){
                return;
            }
            android.net.Uri imageUri = data.getData();
            String [] filePath = {MediaStore.Images.Media.DATA};
            android.database.Cursor cursor = getContentResolver().query(imageUri, filePath, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            columnIndex = cursor.getColumnIndex(filePath[0]);
            mediaPath = cursor.getString(columnIndex);
            cursor.close();

            intent = new Intent(this, PictureActivity.class);
            intent.putExtra("picture", mediaPath);
            startActivity(intent);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if (resultCode == RESULT_OK) {
                if (requestCode == IMAGE_GALLERY_REQUEST) {
                    android.net.Uri imageUri = data.getData();
                    String [] filePath = {MediaStore.Images.Media.DATA};
                    android.database.Cursor cursor = getContentResolver().query(imageUri, filePath, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();
                    columnIndex = cursor.getColumnIndex(filePath[0]);
                    mediaPath = cursor.getString(columnIndex);
                    cursor.close();

                    intent = new Intent(this, PictureActivity.class);
                    intent.putExtra("picture", mediaPath);
                    startActivity(intent);
                }
            }

        } else {
            String[] permissionRequest = {Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissionRequest, CAM_REQUEST_CODE);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAM_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                System.out.println("her?");

                Toast.makeText(this, R.string.GiveAccess, Toast.LENGTH_LONG).show();

            } else {
                System.out.println("eller kanskje her?");
                Toast.makeText(this, getResources().getString(R.string.GiveAccess), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void loadImageFromStorage(String path) {
        try {
            pictureFile = new File(path, "profile.jpg");
            bitmap = BitmapFactory.decodeStream(new FileInputStream(pictureFile));
            image = (ImageView)findViewById(R.id.window1);
            image.setImageBitmap(bitmap);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shake.
     *
     * @param v the v
     */
    public void shake(View v){
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.anime);
        findViewById(R.id.window1).startAnimation(shake);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        /**
         * The Bm image.
         */
        ImageView bmImage;

        /**
         * Instantiates a new Download image task.
         *
         * @param bmImage the bm image
         */
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            String picturePath = saveToInternalStorage(result);
            sessionManager.updatePath(picturePath);

        }
    }


    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        myPath = new File(directory, "picture.jpg");

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(myPath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    /**
     * Size of int.
     *
     * @param data the data
     * @return the int
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    protected int sizeOf(Bitmap data) {
        return data.getByteCount();
    }

    /**
     * Gets resized bitmap.
     *
     * @param bm        the bm
     * @param newWidth  the new width
     * @param newHeight the new height
     * @return the resized bitmap
     */
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    /**
     * Gets real path from uri.
     *
     * @param uri the uri
     * @return the real path from uri
     */
    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        String s = cursor.getString(idx);
        cursor.close();
        return s;
    }

    /**
     * Gets image uri.
     *
     * @param inContext the in context
     * @param inImage   the in image
     * @return the image uri
     */
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}







