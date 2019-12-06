package org.chat21.android.core.users.models;

import java.io.Serializable;

/**
 * Created by frontiere21 on 27/09/16.
 */
public interface IChatUser extends Serializable {
    String getId();

    void setId(String id);

    String getFullName();

    void setFullName(String fullName);

    String getEmail();

    void setEmail(String email);

//    void setPassword(String password);
//
//    String getPassword();

    String getProfilePictureUrl();

    void setProfilePictureUrl(String profilePictureUrl);

//    String getAuth();
//
//    void setAuth(String auth);

    @Override
    String toString();

    int compareTo(IChatUser another);
}
