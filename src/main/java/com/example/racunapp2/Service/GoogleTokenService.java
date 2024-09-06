package com.example.racunapp2.Service;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Service;

@Service
public class GoogleTokenService {

    private static final String CLIENT_ID = "389794052046-ljvdmptj2f68mpofulk1ja6nn6ok88fg.apps.googleusercontent.com";
    private final HttpTransport transport = new NetHttpTransport();
    private final JsonFactory jsonFactory = new JacksonFactory();

    public String getEmailFromToken(String idTokenString) throws Exception {
        GoogleIdToken idToken = GoogleIdToken.parse(jsonFactory, idTokenString);
        GoogleIdToken.Payload payload = idToken.getPayload();
        return payload.getEmail();
    }
}
