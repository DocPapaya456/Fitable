package com.papaya.cameraxtutorial;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.ClassroomScopes;
import com.google.api.services.classroom.model.Course;
import com.google.api.services.classroom.model.ListCoursesResponse;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;


import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassroomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassroomFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final int REQ_ONE_TAP = 1;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private List<Course> teacherCourses;
    private RecyclerView recyclerView;
    private ClassAdapter classAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClassroomFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClassroomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassroomFragment newInstance(String param1, String param2) {
        ClassroomFragment fragment = new ClassroomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_classroom, container, false);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        oneTapClient = Identity.getSignInClient(getActivity());
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(getString(R.string.default_web_client_id))
                        .setFilterByAuthorizedAccounts(true)
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();
        RelativeLayout notGoogle = rootView.findViewById(R.id.noGoogle);
        TextView noGoogleSignIn = rootView.findViewById(R.id.noGoogleSignIn);
        boolean loggedInWithGoogle = false;
        for (UserInfo profile : currentUser.getProviderData()) {
            if (profile.getProviderId().equals("google.com")) {
                loggedInWithGoogle = true;
                break;
            }
        }
        if (loggedInWithGoogle) {
            try {
                GoogleCredentials credentials = GoogleCredentials.getApplicationDefault()
                        .createScoped(Collections.singleton(ClassroomScopes.CLASSROOM_COURSES));
                HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);
                Classroom service = new Classroom.Builder(new NetHttpTransport(),
                        GsonFactory.getDefaultInstance(),
                        requestInitializer)
                        .setApplicationName("Classroom samples")
                        .build();
                String pageToken = null;
                List<Course> courses = new ArrayList<>();
                do {
                    ListCoursesResponse response = service.courses().list()
                            .setPageSize(100)
                            .setPageToken(pageToken)
                            .execute();
                    courses.addAll(response.getCourses());
                    pageToken = response.getNextPageToken();
                } while (pageToken != null);

                if (courses.isEmpty()) {
                    System.out.println("No courses found.");
                } else {
                    for (Course course : courses) {
                        if (course.getOwnerId() == mAuth.getCurrentUser().getUid()) {
                            teacherCourses.add(course);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        } else {
            notGoogle.setVisibility(View.VISIBLE);
            noGoogleSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    oneTapClient.beginSignIn(signInRequest).addOnSuccessListener(new OnSuccessListener<BeginSignInResult>() {
                        @Override
                        public void onSuccess(BeginSignInResult beginSignInResult) {
                            try {
                                getActivity().startIntentSenderForResult(
                                        beginSignInResult.getPendingIntent().getIntentSender(),
                                        REQ_ONE_TAP, null, 0, 0, 0);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            });
        }

        recyclerView = rootView.findViewById(R.id.classroomRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        classAdapter = new ClassAdapter(getActivity(), new ArrayList<>(teacherCourses));
        recyclerView.setAdapter(classAdapter);
        


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_ONE_TAP:
                try {
                    SignInCredential signInCredential = oneTapClient.getSignInCredentialFromIntent(data);
                    String idToken = signInCredential.getGoogleIdToken();
                    if (idToken != null) {
                        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
                        mAuth.getCurrentUser().linkWithCredential(firebaseCredential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    ((MainMenu) getActivity()).restartCurrentFragment();
                                }
                            }
                        });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}