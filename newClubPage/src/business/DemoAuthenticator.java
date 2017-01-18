package business;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.security.GeneralSecurityException;
import javax.security.auth.login.LoginException;

public final class DemoAuthenticator {

    private static DemoAuthenticator authenticator = null;

    // A user storage which stores <username, password>
    private final Map<String, String> usersStorage = new HashMap();


    // An authentication token storage which stores <auth_token, username>.
    private final Map<String, String> authorizationTokensStorage = new HashMap();

    private DemoAuthenticator() {
        // The usersStorage pretty much represents a user table in the database
        usersStorage.put( "username1", "passwordForUser1" );
        usersStorage.put( "username2", "passwordForUser2" );
        usersStorage.put( "username3", "passwordForUser3" );

    }

    public static DemoAuthenticator getInstance() {
        if ( authenticator == null ) {
            authenticator = new DemoAuthenticator();
        }

        return authenticator;
    }

    public String login( String username, String password ) throws LoginException {

        if (usersStorage.containsKey( username ) ) {
            String passwordMatch = usersStorage.get( username );

            if ( passwordMatch.equals( password ) ) {

                /**
                 * Once all params are matched, the authToken will be
                 * generated and will be stored in the
                 * authorizationTokensStorage. The authToken will be needed
                 * for every REST API invocation and is only valid within
                 * the login session
                 */
                String authToken = UUID.randomUUID().toString();
                authorizationTokensStorage.put(authToken, username );

                return authToken;
            }
        }

        throw new LoginException( "Don't Come Here Again!" );
    }

    public boolean isAuthTokenValid( String authToken ) {

        if ( authorizationTokensStorage.containsKey( authToken ) ) {
                return true;

        }

        return false;
    }


    public void logout(String authToken ) throws GeneralSecurityException {

            if ( authorizationTokensStorage.containsKey( authToken ) ) {

                /**
                 * When a client logs out, the authentication token will be
                 * remove and will be made invalid.
                 */
                authorizationTokensStorage.remove( authToken );
                return;
            }
            
        

        throw new GeneralSecurityException( "Authorization token match." );
    }
}