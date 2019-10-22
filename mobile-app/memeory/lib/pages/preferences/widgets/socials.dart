import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:flutter_facebook_login/flutter_facebook_login.dart';
import 'package:flutter_signin_button/flutter_signin_button.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:memeory/api/profile.dart';
import 'package:memeory/cache/repository/socials_repo.dart';
import 'package:memeory/components/message/messages.dart';
import 'package:memeory/model/user.dart';
import 'package:memeory/util/consts/consts.dart';
import 'package:memeory/util/i18n/i18n.dart';
import 'package:pedantic/pedantic.dart';

final FirebaseAuth _auth = FirebaseAuth.instance;
final GoogleSignIn _googleSignIn = GoogleSignIn();
final FacebookLogin _facebookSignIn = FacebookLogin();

class SocialPreferences extends StatefulWidget {
  @override
  _SocialPreferencesState createState() => _SocialPreferencesState();
}

class _SocialPreferencesState extends State<SocialPreferences> {
  ProviderAuth _googleProfile;
  ProviderAuth _facebookProfile;

  @override
  void initState() {
    unawaited(_refreshProfiles());
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Container(
            child: SignInButton(
              Buttons.Google,
              onPressed: () async {
                await _providerSignIn(GOOGLE_PROVIDER, context);
              },
              text: _googleProfile?.displayName == null
                  ? t(context, "auth_google")
                  : _googleProfile.displayName,
            ),
          ),
          Container(
            child: SignInButton(
              Buttons.Facebook,
              onPressed: () async {
                await _providerSignIn(FACEBOOK_PROVIDER, context);
              },
              text: _facebookProfile?.displayName == null
                  ? t(context, "auth_facebook")
                  : _facebookProfile.displayName,
            ),
          ),
        ],
      ),
    );
  }

  _refreshProfiles() async {
    var googleProfile = await getSocialsAccount(GOOGLE_PROVIDER);
    var facebookProfile = await getSocialsAccount(FACEBOOK_PROVIDER);

    setState(() {
      _facebookProfile = facebookProfile;
      _googleProfile = googleProfile;
    });
  }

  _providerSignIn(String provider, BuildContext context) async {
    try {
      AuthCredential credential = await getAuthCredential(provider);

      final FirebaseUser profile =
          (await _auth.signInWithCredential(credential)).user;

      if (profile == null) throw new FlutterError(EMPTY);

      var providerAuth = ProviderAuth.fromFirebaseUser(profile);
      await setSocialsAccount(provider, providerAuth);
      await saveProfile();
      await _refreshProfiles();

      successToast(
        context,
        "${t(context, "welcome")}, ${providerAuth.displayName}",
      );
    } catch (e) {
      debugPrint(e.toString());
      errorToast(context, t(context, "unsuccessful_auth"));
    }
  }

  Future<AuthCredential> getAuthCredential(String provider) async {
    if (provider == GOOGLE_PROVIDER) {
      final GoogleSignInAccount googleUser = await _googleSignIn.signIn();
      final GoogleSignInAuthentication googleAuth =
          await googleUser.authentication;

      return GoogleAuthProvider.getCredential(
        accessToken: googleAuth.accessToken,
        idToken: googleAuth.idToken,
      );
    } else if (provider == FACEBOOK_PROVIDER) {
      final result = await _facebookSignIn.logInWithReadPermissions(['email']);

      if (result.status == FacebookLoginStatus.loggedIn) {
        return FacebookAuthProvider.getCredential(
          accessToken: result.accessToken.token,
        );
      }
    }
    return null;
  }
}
