package com.ob.myntra.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.ob.myntra.R;

/***
 * user management
 * https://firebase.google.com/docs/auth/android/manage-users
 */
public class UserProfileActivity extends AppCompatActivity {

    private SimpleDraweeView msdvProfilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        msdvProfilePicture = (SimpleDraweeView)findViewById(R.id.upa_sdvProfilePhoto);
        Uri uri = Uri.parse("https://lh3.googleusercontent.com/-ugWSehnlNbk/VS5YHS9jsaI/AAAAAAAAABQ/J003UYuTTBU11N1u_sdy8239N1fzYif2wCEw/w139-h140-p/DSC_7845.JPG");
        msdvProfilePicture.setImageURI(uri);

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Hemant Vitthal")
                .setPhotoUri(Uri.parse("https://lh3.googleusercontent.com/-ugWSehnlNbk/VS5YHS9jsaI/AAAAAAAAABQ/J003UYuTTBU11N1u_sdy8239N1fzYif2wCEw/w139-h140-p/DSC_7845.JPG"))
                .build();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
          //  boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }
    }
}
